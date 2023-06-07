package com.hexamigos.aispaceserver.integration.mail

import com.hexamigos.aispaceserver.action.email.Email
import com.hexamigos.aispaceserver.integration.mail.client.EmailClient
import org.springframework.stereotype.Component

interface EmailService {
    fun send(email: Email)
}

@Component
class EmailServiceImpl(val emailClient: EmailClient) : EmailService {
    override fun send(email: Email) {
        emailClient.send(email)
    }

}