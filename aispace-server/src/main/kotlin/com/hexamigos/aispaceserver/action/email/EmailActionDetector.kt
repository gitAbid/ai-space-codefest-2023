package com.hexamigos.aispaceserver.action.email

import com.hexamigos.aispaceserver.action.ActionDetector
import com.hexamigos.aispaceserver.action.ActionType
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class EmailActionDetector : ActionDetector {
    val tags = HashMap<String, Boolean>()
    override fun detect(tokens: HashSet<String>): ActionType {
        val isAction = tokens.any { key -> tags.getOrDefault(key, false) }
        return if (isAction) ActionType.SEND_EMAIL else ActionType.NOT_DETECTED
    }

    @PostConstruct
    private fun build() {
        tags.put("@send", true)
    }

    override fun toString(): String {
        return "EmailActionDetector"
    }
}