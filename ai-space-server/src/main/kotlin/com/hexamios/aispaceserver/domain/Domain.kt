package com.hexamios.aispaceserver.domain



data class ChatRequest(
    val model: String,
    val messages: List<Message>,
    val n: Int = 0,
    val temperature: Double = 0.0
)

data class Message (
    val role: String? = null,
    val content: String? = null // constructor, getters and setters
)

data class Choice(
    val index: Int = 0,
    val message: Message? = null // constructors, getters and setters
)

data class ChatResponse(
    val choices: List<Choice>
)
