package com.hexamigos.aispaceserver.integration.ai.llm.openai

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.completion.CompletionRequest
import com.aallam.openai.api.completion.TextCompletion
import com.aallam.openai.api.http.Timeout
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.hexamigos.aispaceserver.integration.ai.llm.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import kotlin.time.Duration.Companion.seconds

@Component
class OpenAiLLM(@Value("\${openai.api.key}") val apiKey: String) : LLMClient {
    private val history = LinkedHashMap<LLMRequest, LLMResponse>()

    @OptIn(BetaOpenAI::class)
    private val chatHistory = arrayListOf<ChatMessage>()
    private val openAI = OpenAI(
            token = apiKey,
            timeout = Timeout(socket = 60.seconds),
            // additional configurations...
    )

    override suspend fun getCompletion(request: LLMRequest): LLMResponse {
        val (requestMessage, prompts) = request as OpenAIRequest


        val modelPrompt = """
            {$prompts}
            
            ```${requestMessage}```   
        """.trimIndent()


        val completionRequest = CompletionRequest(
                model = ModelId("text-ada-001"),
                prompt = modelPrompt,
                echo = true,
        )
        val completion: TextCompletion = openAI.completion(completionRequest)

        return OpenAIResponse(completion.id, completion.choices, requestMessage, prompts);
    }

    @OptIn(BetaOpenAI::class)
    override suspend fun getChatCompletion(request: LLMRequest): LLMResponse {
        val (requestMessage, prompts) = request as OpenAIRequest
        chatHistory.add(ChatMessage(
                role = ChatRole.User,
                content = requestMessage
        ))
        val chatCompletionRequest = ChatCompletionRequest(
                model = ModelId("gpt-3.5-turbo"),
                messages = arrayListOf(
                        ChatMessage(
                                role = ChatRole.System,
                                content = prompts
                        ),

                        ).apply {
                    addAll(chatHistory)
                }
        )
        val completion: ChatCompletion = openAI.chatCompletion(chatCompletionRequest)

        completion.choices[0].message?.let { chatHistory.add(it) }

        return OpenAIChatResponse(completion.id, completion.choices, requestMessage, prompts);
    }

    override fun getHistory(): Map<LLMRequest, LLMResponse> = history;


}