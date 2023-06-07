package com.hexamigos.aispaceserver.resource.task

import com.hexamigos.aispaceserver.action.task.Task
import com.hexamigos.aispaceserver.action.task.TaskDetail
import com.hexamigos.aispaceserver.resource.ResourceManager
import com.hexamigos.aispaceserver.resource.ResourceManagerType
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class TaskManager : ResourceManager<String, TaskDetail> {
    val tasks = HashMap<String, TaskDetail>()
    override fun add(resource: TaskDetail): String {
        tasks.putIfAbsent(resource.id, resource)
        return resource.id
    }

    override fun getBy(key: String): TaskDetail? {
        return tasks[key]
    }

    override fun getAll(): MutableCollection<TaskDetail> {
        return tasks.values
    }

    override fun update(resource: TaskDetail): TaskDetail? {
        tasks[resource.id] = resource
        return tasks[resource.id]
    }

    override fun deleteBy(key: String): Boolean {
        return tasks.remove(key) != null
    }

    override fun deleteAll(): Int {
        val count = tasks.size
        tasks.clear()
        return count;
    }

    override fun getResourceType() = ResourceManagerType.TASK

}