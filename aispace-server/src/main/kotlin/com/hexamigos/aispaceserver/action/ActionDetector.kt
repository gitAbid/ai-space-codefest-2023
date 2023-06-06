package com.hexamigos.aispaceserver.action

interface ActionDetector {
    fun detect(tokens: HashSet<String>): ActionType
}