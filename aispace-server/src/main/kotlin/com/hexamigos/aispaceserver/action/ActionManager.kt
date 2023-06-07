package com.hexamigos.aispaceserver.action

import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct
import kotlin.collections.HashSet

@Component
class ActionManager(private val context: ApplicationContext,
                    private val actionDetectors: HashSet<ActionDetector> = HashSet(),
                    private val actions: EnumMap<ActionType, Action<*>> = EnumMap(ActionType::class.java)
) {
    private val logger = LoggerFactory.getLogger(ActionManager::class.java)

    @PostConstruct
    private fun build() {
        val actionDetectorMap = context.getBeansOfType(ActionDetector::class.java)
        actionDetectors.addAll(actionDetectorMap.values)
        logger.info("Action detectors initialized. [{}] action detectors found {}", actionDetectors.size, actionDetectors)

        val actionsMap = context.getBeansOfType(Action::class.java)
        actionsMap.values.forEach { action: Action<*> -> actions[action.getActionType()] = action }
        logger.info("Actions initialized. [{}] actions found [{}]", actions.size, actions)
    }

    fun detectAction(input: String): List<ActionType> {
        logger.info("Detecting actions started for input: [$input]")
        val tokens = getTokens(input, "@")
        val actionTypes = actionDetectors
                .map { ad -> ad.detect(tokens) }
                .filter { at -> at != ActionType.NOT_DETECTED }
                .toList()
        logger.info("Detected actions for input: [$input]. Actions[${actionTypes.size}]: [$actionTypes]")
        return actionTypes
    }

    fun performAction(input: String, actionType: ActionType): ActionChain<Any> {
        logger.info("Performing actions [type: $actionType, input: $input]")
        return actions[actionType]?.run {
            val actionChain = executeChainAction(input)
            logger.info("Finished performing actions [type: $actionType, input: $input, status: $actionChain]")
            actionChain
        } ?: throw RuntimeException("Invalid action to perform [$actionType]")
    }

    fun getTokens(input: String, delimiter: String): HashSet<String> {
        val pattern = Regex("""${delimiter}\w+""")
        val matches = pattern.findAll(input)
        return matches.map { it.value }.toHashSet();
    }


}