package com.hexamigos.aispaceserver.action

import com.aallam.openai.api.BetaOpenAI
import com.hexamigos.aispaceserver.action.email.Email
import com.hexamigos.aispaceserver.integration.ai.llm.LLMClient
import com.hexamigos.aispaceserver.integration.ai.llm.OpenAIChatResponse
import com.hexamigos.aispaceserver.integration.ai.llm.OpenAIRequest
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component

@Component
abstract class Action<T>(var prompt: String = "",
                         val llmClient: LLMClient) {
    abstract fun init();
    fun recompilePrompt() {
    }

    abstract fun getActionType(): ActionType

    @OptIn(BetaOpenAI::class)
    fun process(input: String): ActionChain<Processed<String, String>> {
        val processedInput = input.trim().replace("\n", " ")
        val chatHistory = llmClient.getChatHistory()
        var chain: ActionChain<Processed<String, String>>
        runBlocking {
            try {
                val chatCompletion = llmClient.getChatCompletion(
                        OpenAIRequest(
                                requestMessage = processedInput,
                                prompts = prompt
                        ),
                        chatHistory,
                        false
                ) as OpenAIChatResponse

                val content = chatCompletion.responseMessage.first().message?.content ?: "No Content"
                val response = getSectionedResponse(content)
                val processed = Processed(content, response)
                chain = ActionChain(ChainState.NEXT, processed)
            } catch (e: Exception) {
                val processed = Processed("Ops! Something went wrong", e.message ?: "Unknown issue")
                chain = ActionChain(ChainState.ERROR, processed)
            }
        }

        return chain;
    }

    fun getSectionedResponse(content: String) = if (content.contains("```"))
        content.substringAfter("```").substringBefore("```") else ""


    abstract fun transform(chain: ActionChain<Processed<String, String>>): ActionChain<Transformed<Any, Email>>
    abstract fun execute(chain: ActionChain<Transformed<Any, Email>>): ActionChain<ActionStatus>
    abstract fun executeChainAction(input: String): ActionChain<Any>
}