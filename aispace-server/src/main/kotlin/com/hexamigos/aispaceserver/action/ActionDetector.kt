package com.hexamigos.aispaceserver.action

abstract class ActionDetector(private val actionType: ActionType,
                              val tags: HashMap<String, Boolean> = HashMap()
) {
    fun detect(tokens: HashSet<String>): ActionType {
        val isAction = tokens.any { key -> tags.getOrDefault(key, false) }
        return if (isAction) actionType else ActionType.NOT_DETECTED
    }
}