package com.hexamigos.aispaceserver.action.task

import com.hexamigos.aispaceserver.action.Action
import com.hexamigos.aispaceserver.action.ActionStatus
import com.hexamigos.aispaceserver.action.ActionType
import com.hexamigos.aispaceserver.integration.ai.llm.LLMClient
import javax.annotation.PostConstruct

class TaskAction(prompt: String) : Action<Task>(prompt) {
    @PostConstruct
    override fun init() {
        TODO("Not yet implemented")
    }

    override fun getPrompt(): String {
        TODO("Not yet implemented")
    }

    override fun getActionType(): ActionType {
        TODO("Not yet implemented")
    }

    override fun process(input: String, llmClient: LLMClient): String {
        TODO("Not yet implemented")
    }

    override fun transform(input: String): Task {
        TODO("Not yet implemented")
    }

    override fun executeChainAction(input: String): ActionStatus {
        TODO("Not yet implemented")
    }

    override fun execute(input: Task): ActionStatus {
        TODO("Not yet implemented")
    }
}