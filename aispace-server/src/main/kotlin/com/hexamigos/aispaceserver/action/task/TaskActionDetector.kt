package com.hexamigos.aispaceserver.action.task

import com.hexamigos.aispaceserver.action.ActionDetector
import com.hexamigos.aispaceserver.action.ActionType
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class TaskActionDetector : ActionDetector(ActionType.TASK) {
    @PostConstruct
    private fun build() {
        tags["@task"] = true
    }

    override fun toString(): String {
        return "TaskActionDetector"
    }
}