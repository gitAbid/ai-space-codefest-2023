package com.hexamigos.aispaceserver.action.email

import com.hexamigos.aispaceserver.action.ActionType
import com.hexamigos.aispaceserver.integration.ai.llm.LLMClient
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class EmailActionTest {

    @Autowired
    lateinit var emailAction: EmailAction

    @Autowired
    lateinit var llmClient: LLMClient

    @Test
    fun getPrompt() {
        assertTrue(emailAction.prompt.isNotEmpty())
    }

    @Test
    fun getActionType() {
        assertTrue(emailAction.getActionType() == ActionType.SEND_EMAIL)
    }

    @Test
    fun process() {
        val input = """
            Here's a draft email
            
            Subject: Leave of Absence Request

            Dear Sampath,

            I am writing to request a leave of absence from work due to illness. I have been feeling unwell and my doctor has advised me to take some time off to rest and recover.

            I, Abid, would like to request leave for one day, starting from May 5th, 2021. I will keep you updated on my condition and plan to return to work as soon as possible.

            Thank you for your understanding and support during this time.

            Sincerely,
            Abid
            
            ---
            user_input: @send it to sampath@gmail.com, thilina@gmail.com. @cc adib@gmail.com. @sub "Apply leave on 5th May"
        """.trimIndent().trim()
        val process = emailAction.process(input)
        println(process)
    }

    @Test
    fun transform() {
        val emailJson = """
            {
              "actionType": "SEND_EMAIL",
              "to": ["sampath@gmail.com", "thilina@gmail.com"],
              "cc": ["adib@gmail.com"],
              "sub": "Apply leave on 5th May",
              "body": "Dear Sampath,\n\nI am writing to request a leave of absence from work due to illness. I have been feeling unwell and my doctor has advised me to take some time off to rest and recover.\n\nI, Abid, would like to request leave for one day, starting from May 5th, 2021. I will keep you updated on my condition and plan to return to work as soon as possible.\n\nThank you for your understanding and support during this time.\n\nSincerely,\nAbid"
            }
        """.trimIndent().trim()
//        val transform = emailAction.transform(emailJson)
//        println(transform)
    }

    @Test
    fun execute() {
        val emailJson = """
            {
              "actionType": "SEND_EMAIL",
              "to": ["sampath@gmail.com", "thilina@gmail.com"],
              "cc": ["adib@gmail.com"],
              "sub": "Apply leave on 5th May",
              "body": "Dear Sampath,\n\nI am writing to request a leave of absence from work due to illness. I have been feeling unwell and my doctor has advised me to take some time off to rest and recover.\n\nI, Abid, would like to request leave for one day, starting from May 5th, 2021. I will keep you updated on my condition and plan to return to work as soon as possible.\n\nThank you for your understanding and support during this time.\n\nSincerely,\nAbid"
            }
        """.trimIndent().trim()
//        val transform = emailAction.transform(emailJson)
//        val status = emailAction.execute(transform)
//        println(status)
    }


    @Test
    fun executeProcessTransformExecuteChain() {
        val input = """
            Here's a draft email
            
            Subject: Leave of Absence Request

            Dear Sampath,

            I am writing to request a leave of absence from work due to illness. I have been feeling unwell and my doctor has advised me to take some time off to rest and recover.

            I, Abid, would like to request leave for one day, starting from May 5th, 2021. I will keep you updated on my condition and plan to return to work as soon as possible.

            Thank you for your understanding and support during this time.

            Sincerely,
            Abid
            
            ---
            user_input: @send it to sampath@gmail.com, thilina@gmail.com. @cc adib@gmail.com. @sub "Apply leave on 5th May"
        """.trimIndent().trim()
//        val processed = emailAction.process(input, llmClient)
//        println("Processed: $processed")
//        val transformed = emailAction.transform(processed)
//        println("Transformed: $transformed")
//        val executed = emailAction.execute(transformed)
//        println("Executed: $executed")

    }
}