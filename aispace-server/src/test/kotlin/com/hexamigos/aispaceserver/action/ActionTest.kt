package com.hexamigos.aispaceserver.action

import com.hexamigos.aispaceserver.action.email.EmailAction
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ActionTest {

    @Autowired
    lateinit var emailAction: EmailAction

    @Test
    fun init() {
    }

    @Test
    fun recompilePrompt() {
    }

    @Test
    fun getActionType() {
    }

    @Test
    fun process() {
    }

    @Test
    fun getSectionedResponse() {
        val input ="asdasdasdasdasd"
        println(emailAction.getSectionedResponse(input))
    }

    @Test
    fun transform() {
    }

    @Test
    fun execute() {
    }

    @Test
    fun executeChainAction() {
    }
}