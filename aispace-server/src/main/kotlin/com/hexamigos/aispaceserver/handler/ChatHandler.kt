package com.hexamigos.aispaceserver.handler

import com.hexamigos.aispaceserver.domain.ChatData
import com.hexamigos.aispaceserver.domain.ChatRequest
import com.hexamigos.aispaceserver.domain.Message
import com.hexamigos.aispaceserver.service.ChatService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class ChatHandler(private val chatService: ChatService) {
    suspend fun chat(request: ServerRequest): ServerResponse {
        val chatRequest = request.awaitBodyOrNull<Message>()
            ?: throw java.lang.RuntimeException("Bad Request")

        println("chatRequest ${chatRequest}")

        val chatCompletion = chatService.getChatCompletion(chatRequest.content)

        return ServerResponse.ok().bodyValueAndAwait(
            Message(
                role = "Assistant",
                content = chatCompletion.content
            )
        )
    }
}
