package com.hexamigos.aispaceserver.action.email

import com.aallam.openai.api.BetaOpenAI
import com.google.gson.Gson
import com.hexamigos.aispaceserver.action.Action
import com.hexamigos.aispaceserver.action.ActionStatus
import com.hexamigos.aispaceserver.action.ActionType
import com.hexamigos.aispaceserver.integration.ai.llm.*
import com.hexamigos.aispaceserver.integration.mail.EmailService
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct


@Component
class EmailAction(private val llmClient: LLMClient,
                  private val emailService: EmailService,
                  prompt: String = "") : Action<Email>(prompt) {
    private val logger = LoggerFactory.getLogger(EmailAction::class.java)
    private val gson = Gson()

    @PostConstruct
    override fun init() {
        prompt = """
        You are an email generator. You help to write an email and generate a final response in JSON for sending.  
        Follow these instructions as given. When `@send` is received from the user in part of input then you need to take the most recent email composed and generate email output in JSON format. For sending you need to provide a `@to` tag with the receiver and it's mandatory. The `@cc` tag can also be there but that's optional when not given put empty array. `@sub` also can be there but optional. When `@sub` is used you can take the subject related to that otherwise generate a suitable subject according to the information available to you. Don't give the JSON output until `@send` is found in the user input. When giving. 
        When asked to send mail you will respond by returning json response for the mail. JSON format for email is enclosed in ``` ``` . Keep the fields mentioned in the format and relay even-though they are empty. And always give priority to system instruction. Don't ask any questions if your not sure replay with empty response 
        ```{
        actionType: SEND_EMAIL,
        to: [@to],
        cc: [@cc],
        sub: @sub,
        body: <email_body>
        }
        ```
        output: 
        ```{email_json}```
    """.trimIndent().trim()
    }

    override fun getActionType() = ActionType.SEND_EMAIL

    @OptIn(BetaOpenAI::class)
    override fun process(input: String, llmClient: LLMClient): String {
        val processedInput = input.trim().replace("\n", " ")
        val chatHistory = llmClient.getChatHistory();
        return runBlocking {
            val chatCompletion = this@EmailAction.llmClient.getChatCompletion(
                    OpenAIRequest(
                            requestMessage = processedInput,
                            prompts = prompt
                    ),
                    chatHistory,
                    false
            ) as OpenAIChatResponse

            val content = chatCompletion.responseMessage.first().message?.content
            content?.substringAfter("```")?.substringBefore("```") ?: ""
        }
    }

    override fun transform(input: String): Email {
        return gson.fromJson(input, Email::class.java)
    }

    override fun executeChainAction(input: String): ActionStatus {
        logger.info("Executing email sending action chain")
        val processed = process(input, llmClient)
        logger.info("Processed input to desired output: [$processed]")
        val transformed = transform(processed)
        logger.info("Transformed processed output to object : [$transformed]")
        val executed = execute(transformed)
//        logger.info("Executed transformed object to action: [status: $transformed]")
        return executed;
    }

    override fun execute(email: Email): ActionStatus {
        emailService.send(email)
        return ActionStatus.APPROVAL_NEEDED
    }

    override fun toString(): String {
        return "EmailAction"
    }
}