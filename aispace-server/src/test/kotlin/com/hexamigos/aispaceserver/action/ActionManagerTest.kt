package com.hexamigos.aispaceserver.action

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ActionManagerTest {


    @Autowired
    lateinit var actionManager: ActionManager

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {

    }

    @Test
    fun detectAction() {
        val input = "@send it to sampath@gmail.com, thilina@gmail.com. @cc adib@gmail.com. @sub \"Apply leave on 5th May\""
        val detectAction = actionManager.detectAction(input)
        println(detectAction)
        assertEquals(1, detectAction.size)
    }

    @Test
    fun getTokens() {
        val input = "@send it to sampath@gmail.com, thilina@gmail.com. @cc adib@gmail.com. @sub \"Apply leave on 5th May\""
        val tokens = actionManager.getTokens(input,"@")
        println(tokens)
        assertEquals(4, tokens.size)

    }
}