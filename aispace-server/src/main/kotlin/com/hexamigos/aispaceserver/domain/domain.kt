package com.hexamigos.aispaceserver.domain

data class ChatRequest(
    val model: String = "gpt-3.5-turbo" ,
    val messages: List<Message>,
    val temperature: Double = 0.1
)

data class Message(
    val role: String,
    val content: String
)

data class ChatCompletion(
    val id: String,
    val `object`: String,
    val created: Long,
    val model: String,
    val usage: Usage,
    val choices: List<Choice>
)

data class Usage(
    val prompt_tokens: Int,
    val completion_tokens: Int,
    val total_tokens: Int
)

data class Choice(
    val message: Message,
    val finish_reason: String,
    val index: Int
)

data class ChatData(
    val chats: List<Chat>
)

data class Chat(
    val role: String,
    val content: String
)
