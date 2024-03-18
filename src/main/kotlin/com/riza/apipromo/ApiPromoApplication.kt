package com.riza.apipromo

import com.fasterxml.jackson.databind.ObjectMapper
import com.riza.apipromo.core.PointInclusion
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class ApiPromoApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<ApiPromoApplication>(*args)
        }
    }

    @Bean
    fun getPointInclusion() = PointInclusion()

    @Bean
    fun getObjectMapper() = ObjectMapper()
}
