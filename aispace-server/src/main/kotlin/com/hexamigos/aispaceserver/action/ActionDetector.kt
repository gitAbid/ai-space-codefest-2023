package com.hexamigos.aispaceserver.action

interface ActionDetector {
    fun detect(input: String): ActionType
}