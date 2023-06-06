package com.hexamigos.aispaceserver.action.email

import com.aallam.openai.api.BetaOpenAI
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.hexamigos.aispaceserver.action.Action
import com.hexamigos.aispaceserver.action.ActionStatus
import com.hexamigos.aispaceserver.action.ActionType
import com.hexamigos.aispaceserver.integration.ai.llm.*
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

data class Email(
        @SerializedName("actionType") var actionType: String? = null,
        @SerializedName("to") var to: ArrayList<String> = arrayListOf(),
        @SerializedName("cc") var cc: ArrayList<String> = arrayListOf(),
        @SerializedName("sub") var sub: String? = null,
        @SerializedName("body") var body: String? = null

)

@Component
class EmailAction(private val llmClient: LLMClient,
                  private var prompt: String = "") : Action<Email> {
    private val logger = LoggerFactory.getLogger(EmailAction::class.java)
    private val gson = Gson()

    @PostConstruct
    override fun init() {
        prompt = """
        You are an email generator. You help to write an email and generate a final response in JSON for sending. 
        Follow these instructions as given. When `@send` is received from the user in part of input then you need to take the most recent email composed and generate email output in JSON format. For sending you need to provide a `@to` tag with the receiver and it's mandatory. The `@cc` tag can also be there but that's optional when not given put empty string. `@sub` also can be there but optional. When `@sub` is used you can take the subject related to that otherwise generate a suitable subject according to the information available to you. Don't give the JSON output until `@send` is found in the user input. When giving. 
        JSON format for email is enclosed in ``` ```
        ```{
        actionType: SEND_EMAIL
        to: ["@to"],
        cc: ["@cc"],
        sub: "@sub",
        body:" <email_body>"
        }
        ```
        output: 
        ```{email_json}```
    """.trimIndent().trim()
    }


    override fun getPrompt() = prompt

    override fun getActionType() = ActionType.SEND_EMAIL

    @OptIn(BetaOpenAI::class)
    override fun process(input: String, llmClient: LLMClient): String {
        val processedInput = input.trim().replace("\n", " ")
        return runBlocking {
            val chatCompletion = this@EmailAction.llmClient.getChatCompletion(
                    OpenAIRequest(
                            requestMessage = processedInput,
                            prompts = prompt
                    )
            ) as OpenAIChatResponse

            val content = chatCompletion.responseMessage.first().message?.content
            content?.substringAfter("```")?.substringBefore("```") ?: ""
        }
    }

    override fun transform(input: String): Email {
        return gson.fromJson(input, Email::class.java)
    }

    override fun execute(input: Email): ActionStatus {
        val email = """Executing Sending Email: 
            |from: abid@gmail.com
            |to: ${input.to}
            |cc: ${input.cc}
            |
            |subject: ${input.sub}
            |
            |body: ${input.body}
        """.trimMargin()
        logger.info(email)
        return ActionStatus.APPROVAL_NEEDED
    }

    override fun toString(): String {
        return "EmailAction"
    }
}