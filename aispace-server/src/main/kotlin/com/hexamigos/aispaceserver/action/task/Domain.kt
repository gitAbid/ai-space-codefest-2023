package com.hexamigos.aispaceserver.action.task

import com.hexamigos.aispaceserver.action.ActionType
import com.hexamigos.aispaceserver.action.OperationType

enum class TaskState {
    PENDING, ONGOING, COMPLETED
}

enum class

data

class Task(val id: String, val name: String, val description: String, val state: TaskState = TaskState.PENDING,
           val assigned: ArrayList<String> = arrayListOf<String>(),
           var actionType: ActionType = ActionType.NO_ACTION,
           var operationType: OperationType? = null)