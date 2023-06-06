package com.hexamigos.aispaceserver.action

enum class ActionStatus(val message: String) {
    FINISHED("Action finished"), APPROVAL_NEEDED("Waiting for approval to finish the action"), FAILED("Action failed"), SUCCESS("Action success")
}