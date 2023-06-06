package com.hexamigos.aispaceserver.service

import com.aallam.openai.api.BetaOpenAI
import com.hexamigos.aispaceserver.action.ActionCenter
import com.hexamigos.aispaceserver.domain.Chat
import com.hexamigos.aispaceserver.integration.ai.llm.LLMResponse
import com.hexamigos.aispaceserver.integration.ai.llm.OpenAIChatResponse
import com.hexamigos.aispaceserver.integration.ai.llm.OpenAIRequest
import com.hexamigos.aispaceserver.integration.ai.llm.openai.OpenAiLLM
import org.springframework.stereotype.Service

@Service
class ChatService(val openAiLLM: OpenAiLLM,
                  val actionCenter: ActionCenter) {

    @OptIn(BetaOpenAI::class)
    suspend fun getChatCompletion(message: String): Chat {
        println("Request Body $message")
        val detectAndExecute = actionCenter.detectAndExecute(message)

        if (detectAndExecute.size > 0) {
            return Chat(role = "Assistant", content = "Action executed with messages: $detectAndExecute")

        }

        val task = arrayListOf<String>("1. Task: Complete Release. Progress: Done",
                "2. Task: Discuss with weleed vai. Progress: Done",
                "3. Task: Progress meeting on code fest. Progress: Done ",
                "5. Task: Work on code fest demo. Progress: Ongoing")

        val (_, responseMessage, _, _) = openAiLLM.getChatCompletion(OpenAIRequest(
                requestMessage = message,
                prompts = """
                        Your are an AI assistant. Given following section answer question only that information.
                        If your unsure and answer in not explicitly written then say 'Sorry, I don't know how to help with that'
                        Accessible Projects:
                        1. Hutch
                        2. Cube
                        3. Appigo
                        ${if (message.contains("@task")) "Task list: \n${task}" else ""}
                    """.trimIndent().trim()
        )) as OpenAIChatResponse
        val content = responseMessage[0].message?.content;
        return Chat(role = "Assistant", content = content ?: "Sorry? I don't know what to say")
    }
}

