package com.hexamigos.aispaceserver.action

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component


@Component
class ActionCenter(val actionManager: ActionManager) {
    private val logger = LoggerFactory.getLogger(ActionCenter::class.java)
    fun detectAndExecute(input: String): List<String> {
        logger.info("Detecting and Executing actions")
        return actionManager.detectAction(input)
                .map { action -> actionManager.performAction(input, action) }
                .map { actionStatus -> actionStatus.content }
                .map { content -> if (content is ActionResponse<*, *>) content.processed.toString() else content.toString() }
                .toList();
    }
}