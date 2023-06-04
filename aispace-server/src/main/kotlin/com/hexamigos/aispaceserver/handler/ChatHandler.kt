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
        val chatRequest = request.awaitBodyOrNull<ChatData>()
            ?: throw java.lang.RuntimeException("Bad Request")

        val chatCompletion = chatService.getChatCompletion(
            ChatRequest(
                messages = listOf(
                    Message(
                        role = chatRequest.chats[0].role,
                        content = chatRequest.chats[0].content
                    )
                )
            )
        )
        return ServerResponse.ok().bodyValueAndAwait(chatCompletion.choices[0].message)
    }
}
