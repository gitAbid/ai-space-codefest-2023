package com.hexamigos.aispaceserver.action.email

import com.hexamigos.aispaceserver.action.ActionDetector
import com.hexamigos.aispaceserver.action.ActionType
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class EmailActionDetector : ActionDetector {
    val tags = HashSet<String>()
    override fun detect(input: String): ActionType {
        TODO("Not yet implemented")
    }

    @PostConstruct
    private fun build() {
        tags.add("@send")
    }

    override fun toString(): String {
        return "EmailActionDetector"
    }
}