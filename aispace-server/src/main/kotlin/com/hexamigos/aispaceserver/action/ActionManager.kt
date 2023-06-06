package com.hexamigos.aispaceserver.action

import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class ActionManager(val context: ApplicationContext) {
    private val logger = LoggerFactory.getLogger(ActionManager::class.java)
    private val actionDetectors = HashSet<ActionDetector>()
    private val actions = HashMap<ActionType, Action<*>>()


    @PostConstruct
    private fun build() {
        val actionDetectorMap = context.getBeansOfType(ActionDetector::class.java)
        actionDetectors.addAll(actionDetectorMap.values)
        logger.info("Action detectors initialized. [{}] action detectors found {}", actionDetectors.size, actionDetectors)


        val actionsMap = context.getBeansOfType(Action::class.java)
        actionsMap.values.forEach { action: Action<*> -> actions[action.getActionType()] = action }
        logger.info("Actions initialized. [{}] actions found [{}]", actions.size, actions)
    }


}