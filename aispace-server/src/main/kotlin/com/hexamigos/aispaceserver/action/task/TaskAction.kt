package com.hexamigos.aispaceserver.action.task

import com.hexamigos.aispaceserver.action.*
import com.hexamigos.aispaceserver.action.email.Email
import com.hexamigos.aispaceserver.integration.ai.llm.LLMClient
import com.hexamigos.aispaceserver.resource.ResourceCenter
import com.hexamigos.aispaceserver.resource.ResourceManagerType
import com.hexamigos.aispaceserver.util.toJson
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class TaskAction(val resourceCenter: ResourceCenter,
                 llmClient: LLMClient,
                 prompt: String = "") : Action<Task>(prompt, llmClient) {
    @PostConstruct
    override fun init() {
        prompt = """
            You are a task manager. You can query, create, update, and delete tasks. When replaying for create, update, and delete operations on tasks you will respond in the JSON format provided here. JSON format is enclosed in
            ```{
            actionType: TASK ,
            operationType: OPERATION_TYPE,
            id: @id,
            title: @title,
            description: @description,
            assignedTo: [@assigned],
            state: STATUS_TYPE
            }
            ```
            `OPERATION_TYPE` can be CREATE, UPDATE, DELETE
            `STATUS_TYPE` can be COMPLETED, PENDING, ONGOING
            @id, @title, @description, @assigned are placeholder that needs to be replaced with the proper value from @task. If the there's no value in placeholder then keep empty types.
            When replaying for query on tasks you will respond in the table format in markdown with header No, Title, State

            ---
            output: 
            ```{response_json}```
            
            ---
            Here are the @task/@task list you have:
            ```{{tasks}}```
            
        """.trimIndent().trim()
    }

    override fun process(input: String): ActionChain<Processed<String, String>> {
        val chain = super.process(input)
        if (chain.hasNext()) {
            val response = chain.content
            if (response.processed.isEmpty()) {
                val processed = Processed(response.original, response.original)
                return ActionChain(ChainState.ABORT, processed)
            }
            return chain
        }
        return chain
    }
    override fun recompilePrompt() {
        val taskManager = resourceCenter.getResourceByType(ResourceManagerType.TASK)
        val allTasks = taskManager.getAll()
        val jsonTasks = allTasks.toJson() ?: ""
        prompt = prompt.replace("{{task}}", jsonTasks)
    }

    override fun getActionType() = ActionType.TASK
    override fun transform(chain: ActionChain<Processed<String, String>>): ActionChain<Transformed<Any, Email>> {
        TODO("Not yet implemented")
    }

    override fun execute(chain: ActionChain<Transformed<Any, Email>>): ActionChain<ActionStatus> {
        TODO("Not yet implemented")
    }

    override fun executeChainAction(input: String): ActionChain<Any> {
        TODO("Not yet implemented")
    }

}