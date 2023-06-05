package com.hexamigos.aispaceserver.integration.ai.llm.openai

import com.aallam.openai.api.embedding.EmbeddingRequest
import com.aallam.openai.api.embedding.EmbeddingResponse
import com.aallam.openai.api.model.ModelId
import com.hexamigos.aispaceserver.integration.ai.llm.LLMClient
import com.hexamigos.aispaceserver.integration.ai.llm.OpenAIRequest
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import javax.annotation.PostConstruct

@SpringBootTest
class OpenAiLLMTest {


    @Value("\${openai.api.key}")
    lateinit var openAiKey: String

    lateinit var openAiLLM: LLMClient

    @PostConstruct
    fun before() {
        this.openAiLLM = OpenAiLLM(openAiKey)
    }


    @Test
    fun getCompletion() {
        runBlocking {
            val completion = openAiLLM.getCompletion(OpenAIRequest(
                    requestMessage = "I am a lonely bird and ",
                    prompts = "Complete the sentence"
            ))
            println(completion)
        }
    }

    @Test
    fun getChatCompletion() {
        runBlocking {
            val completion = openAiLLM.getChatCompletion(OpenAIRequest(
                    requestMessage = "Write an email for leave of absence?",
                    prompts = """
                        Your are an AI assistant. 
                    """.trimIndent()
            ))
            println(completion)
        }
    }

    @Test
    fun getHistory() {
    }

    @Test
    fun getEmbeddings(content: List<String>) {
        runBlocking {
            val response = openAiLLM.getEmbeddings(arrayListOf("Hi My Name is Abid", "How are you"))
//            println(response)
        }

    }
}