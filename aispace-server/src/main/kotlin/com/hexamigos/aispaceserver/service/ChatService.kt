package com.hexamigos.aispaceserver.service

import com.hexamigos.aispaceserver.domain.ChatCompletion
import com.hexamigos.aispaceserver.domain.ChatRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service

import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange

@Service
class ChatService(
    @Value("\${openai.api.key}")
    private val apiKey: String
) {
    private val webClient = WebClient.builder()
        .baseUrl("https://api.openai.com/v1")
        .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer $apiKey")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build()

    suspend fun getChatCompletion(request: ChatRequest): ChatCompletion {
        val responseBody = webClient.post()
            .uri("/chat/completions")
            .bodyValue(request)
            .awaitExchange { clientResponse ->
            when{
                clientResponse.statusCode().is2xxSuccessful ->{
                    clientResponse.awaitBody<ChatCompletion>()
                }
                clientResponse.statusCode().is4xxClientError -> {

                    throw java.lang.RuntimeException( "Weird Error")
                }
                else->{
                    throw java.lang.RuntimeException("More weird error")
                }

            }

            }

        return responseBody
    }
}

