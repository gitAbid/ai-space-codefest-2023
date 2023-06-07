package com.hexamigos.aispaceserver.resource

import com.hexamigos.aispaceserver.action.task.Task

interface ResourceManager<K, T> {
    fun getResourceType(): ResourceManagerType
    fun add(resource: T): K
    fun getBy(key: K): T?
    fun getAll(): MutableCollection<T>
    fun update(resource: T): T?
    fun deleteBy(key: K): Boolean
    fun deleteAll(): Int
}