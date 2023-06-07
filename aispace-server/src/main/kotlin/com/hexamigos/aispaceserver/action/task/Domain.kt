package com.hexamigos.aispaceserver.action.task

import com.hexamigos.aispaceserver.action.ActionType
import com.hexamigos.aispaceserver.action.OperationType
import java.util.UUID

enum class TaskState {
    PENDING, ONGOING, COMPLETED
}

class Task(val id: String? = null,
           var name: String? = null,
           var description: String? = null,
           var state: TaskState? = TaskState.PENDING,
           var assignedTo: ArrayList<String> = arrayListOf(),
           var actionType: ActionType? = ActionType.NO_ACTION,
           var operationType: OperationType? = null)

class TaskDetail(val id: String = UUID.randomUUID().toString(),
                 var name: String? = null,
                 var description: String? = null,
                 var state: TaskState? = TaskState.PENDING,
                 var assignedTo: ArrayList<String> = arrayListOf())