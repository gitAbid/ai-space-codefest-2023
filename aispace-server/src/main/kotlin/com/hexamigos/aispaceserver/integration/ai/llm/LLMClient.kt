package com.hexamigos.aispaceserver.integration.ai.llm

interface LLMClient {
    suspend fun getCompletion(request: LLMRequest): LLMResponse
    suspend fun getChatCompletion(request: LLMRequest): LLMResponse

    fun getHistory(): Map<LLMRequest, LLMResponse>
}