package com.hexamigos.aispaceserver.integration.ai.llm

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.embedding.EmbeddingResponse

interface LLMClient {
    suspend fun getCompletion(request: LLMRequest): LLMResponse
    suspend fun getChatCompletion(request: LLMRequest): LLMResponse
    suspend fun getEmbeddings(content: List<String>): EmbeddingResponse
    fun getHistory(): Map<LLMRequest, LLMResponse>

    @OptIn(BetaOpenAI::class)
    suspend fun getChatCompletionForAction(request: LLMRequest, chatHistory: ArrayList<ChatMessage>, updateHistory: Boolean = false): LLMResponse
    @OptIn(BetaOpenAI::class)
    fun getChatHistory(): ArrayList<ChatMessage>
}