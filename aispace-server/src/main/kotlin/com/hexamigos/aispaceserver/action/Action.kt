package com.hexamigos.aispaceserver.action

import com.hexamigos.aispaceserver.integration.ai.llm.LLMClient

interface Action<T> {
    fun init();
    fun getPrompt(): String
    fun getActionType(): ActionType
    fun process(input: String, llmClient: LLMClient): String
    fun transform(input: String): T
    fun execute(input: T): ActionStatus
    fun executeChainAction(input: String): ActionStatus
}