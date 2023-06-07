package com.hexamigos.aispaceserver.integration.mail.client

import com.hexamigos.aispaceserver.action.email.Email
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class EmailLoggerClient : EmailClient {
    private val logger = LoggerFactory.getLogger(EmailLoggerClient::class.java)
    override fun build() {

    }

    override fun send(email: Email) {
        val mail = """Executing Sending Email: 
            |from: abid@gmail.com
            |to: ${email.to}
            |cc: ${email.cc}
            |
            |subject: ${email.sub}
            |
            |body: ${email.body}
        """.trimMargin()
        logger.info(mail)
    }
}