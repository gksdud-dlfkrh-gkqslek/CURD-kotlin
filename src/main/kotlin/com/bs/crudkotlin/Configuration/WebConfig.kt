package com.bs.crudkotlin.Configuration

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.io.File

@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/api/**")
            .allowedOrigins(
                "http://localhost:8080",
                "https://web-production-9b27c.up.railway.app"
            ).allowCredentials(true)
            .allowedMethods("GET", "POST", "PUT", "DELETE")
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        val uploadDir = File("uploads").absolutePath
        registry.addResourceHandler("/uploads/**")
            .addResourceLocations("file:$uploadDir/")
    }
}