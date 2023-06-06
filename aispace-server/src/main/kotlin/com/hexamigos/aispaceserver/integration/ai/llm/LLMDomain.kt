package com.hexamigos.aispaceserver.integration.ai.llm

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatChoice
import com.aallam.openai.api.completion.Choice
import com.hexamigos.aispaceserver.action.email.EmailAction
import com.hexamigos.aispaceserver.integration.ai.llm.openai.OpenAiLLM
import java.util.UUID


abstract class LLMRequest {
    val id: UUID = UUID.randomUUID();
}

abstract class LLMResponse(open val id: String) {
}

data class OpenAIRequest(val requestMessage: String, val prompts: String="", val temperature: Double=0.0) : LLMRequest()
data class OpenAIResponse(override val id: String, val responseMessage: List<Choice>, val request: String, val prompts: String="") : LLMResponse(id = id)
data class OpenAIChatResponse @OptIn(BetaOpenAI::class) constructor(override val id: String, val responseMessage: List<ChatChoice>, val request: String, val prompts: String) : LLMResponse(id = id)