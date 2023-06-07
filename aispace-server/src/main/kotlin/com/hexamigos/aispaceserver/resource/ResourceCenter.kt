package com.hexamigos.aispaceserver.resource

import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct

@Component
class ResourceCenter(val context: ApplicationContext,
                     val resources: EnumMap<ResourceManagerType, ResourceManager<*, *>> = EnumMap(ResourceManagerType::class.java)) {
    private val logger = LoggerFactory.getLogger(ResourceCenter::class.java)

    @PostConstruct
    fun build() {
        val resourceManagers = context.getBeansOfType(ResourceManager::class.java).values
        resourceManagers.forEach { manager ->
            resources[manager.getResourceType()] = manager
        }
        logger.info("Resource managers initialized. [{}] resources found [{}]", resources.size, resources)
    }

    fun getResourceByType(resourceType: ResourceManagerType) = resources[resourceType]
            ?: throw RuntimeException("No resource found for resourceType[$resourceType]")

}