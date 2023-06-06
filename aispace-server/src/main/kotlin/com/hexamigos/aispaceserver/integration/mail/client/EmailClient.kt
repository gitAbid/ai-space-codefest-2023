package com.hexamigos.aispaceserver.integration.mail.client

import com.hexamigos.aispaceserver.action.Email

interface EmailClient {
    fun build()
    fun send(email: Email)
}