package com.hexamios.aispaceserver.controller

import com.hexamios.aispaceserver.domain.ChatRequest
import com.hexamios.aispaceserver.domain.ChatResponse
import com.hexamios.aispaceserver.domain.Message
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
class ChatController( val restTemplate: RestTemplate) {

    @Value("\${openai.model}")
    private lateinit var model: String

    @Value("\${openai.api.url}")
    private lateinit var apiUrl: String


    @GetMapping("/chat")
    fun chat(@RequestParam prompt: String): String? {

        val systemMsg = Message( role = "system",
        content= "You an employ onboarding expert. You help new comers of the company to get started in the company"
        )

        val userMsg = Message(role = "user", content = prompt)
        // create a request
        val request = ChatRequest(model = model, messages = listOf(systemMsg, userMsg))

        // call the API
        val response = restTemplate.postForObject(apiUrl, request, ChatResponse::class.java)

        if (response == null || response.choices == null || response.choices.isEmpty()) {
            return "No response"
        }

        // return the first response
        return response.choices[0].message?.content
    }
}

