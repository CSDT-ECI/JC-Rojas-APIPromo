package com.riza.apipromo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class ApiPromoApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<ApiPromoApplication>(*args)
        }
    }
}
