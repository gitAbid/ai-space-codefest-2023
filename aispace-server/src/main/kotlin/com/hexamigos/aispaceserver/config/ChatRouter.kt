package com.hexamigos.aispaceserver.config

import com.hexamigos.aispaceserver.handler.ChatHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.reactive.function.server.coRouter

@Configuration
@CrossOrigin()
class ChatRouter(private val chatHandler: ChatHandler) {

    @Bean
    @CrossOrigin()
    fun chatRoutes() = coRouter {
        "/chat".nest {
            POST("", chatHandler::chat)
        }
    }
}
