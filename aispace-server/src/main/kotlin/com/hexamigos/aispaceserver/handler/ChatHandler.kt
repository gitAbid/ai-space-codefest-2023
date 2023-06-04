package com.hexamigos.aispaceserver.handler

import com.hexamigos.aispaceserver.domain.ChatData
import com.hexamigos.aispaceserver.domain.ChatRequest
import com.hexamigos.aispaceserver.domain.Message
import com.hexamigos.aispaceserver.service.ChatService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class ChatHandler(@Autowired private val chatService: ChatService) {
    suspend fun chat(request: ServerRequest): ServerResponse {
        val chatRequest = request.awaitBodyOrNull<Message>()
            ?: throw java.lang.RuntimeException("Bad Request")

        println("chatRequest ${chatRequest}")

        val chatCompletion = chatService.getChatCompletion(
            ChatRequest(
                messages = listOf(
                    Message(
                        role = chatRequest.role,
                        content = chatRequest.content
                    )
                )
            )
        )
        return ServerResponse.ok().bodyValueAndAwait(chatCompletion.choices[0].message)
//        return ServerResponse.ok().bodyValueAndAwait(
//            Message(
//                role = "assistant",
//                content = "I'm sorry, I don't understand what you're trying to say. Can you please provide more context or information?"
//            )
//        )
    }
}
