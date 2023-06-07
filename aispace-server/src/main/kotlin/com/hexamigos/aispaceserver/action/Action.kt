package com.hexamigos.aispaceserver.action

import com.hexamigos.aispaceserver.integration.ai.llm.LLMClient

abstract class Action<T>(var prompt: String) {
    abstract fun init();
    fun getPrompt() = prompt;
    abstract fun getActionType(): ActionType
    abstract fun process(input: String, llmClient: LLMClient): String
    abstract fun transform(input: String): T
    abstract fun execute(input: T): ActionStatus
    abstract fun executeChainAction(input: String): ActionStatus
}