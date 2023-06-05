package com.hexamigos.aispaceserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.cors.reactive.CorsWebFilter
import java.util.*


@SpringBootApplication
class AispaceServerApplication

fun main(args: Array<String>) {
    runApplication<AispaceServerApplication>(*args)
}


