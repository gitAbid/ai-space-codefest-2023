package com.hexamigos.aispaceserver.action

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ActionCenterTest {

    @Autowired
    lateinit var actionCenter: ActionCenter

    @Test
    fun detectAndExecute() {
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
        val detectAndExecute = actionCenter.detectAndExecute(input)
        println("Executed with Status: $detectAndExecute")
    }

    @Test
    fun getActionManager() {
    }
}