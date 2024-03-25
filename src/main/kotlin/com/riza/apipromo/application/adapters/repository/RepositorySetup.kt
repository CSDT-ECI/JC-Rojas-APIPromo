package com.riza.apipromo.application.adapters.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.riza.apipromo.application.adapters.repository.jpa.area.AreaJPAAdapter
import com.riza.apipromo.application.adapters.repository.jpa.area.AreaJPARepository
import com.riza.apipromo.application.adapters.repository.jpa.promo.PromoJPAAdapter
import com.riza.apipromo.application.adapters.repository.jpa.promo.PromoJPARepository
import com.riza.apipromo.application.adapters.repository.jpa.user.UserJPAAdapter
import com.riza.apipromo.application.adapters.repository.jpa.user.UserJPARepository
import com.riza.apipromo.domain.area.AreaRepository
import com.riza.apipromo.domain.promo.PromoRepository
import com.riza.apipromo.domain.user.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RepositorySetup {
    @Bean
    fun areaJPAAdapter(
        areaJPARepository: AreaJPARepository,
        objectMapper: ObjectMapper,
    ): AreaRepository {
        return AreaJPAAdapter(areaJPARepository, objectMapper)
    }

    @Bean
    fun userJPAAdapter(
        userJPARepository: UserJPARepository,
        objectMapper: ObjectMapper,
    ): UserRepository {
        return UserJPAAdapter(userJPARepository, objectMapper)
    }

    @Bean
    fun promoJPAAdapter(
        promoJPARepository: PromoJPARepository,
        objectMapper: ObjectMapper,
    ): PromoRepository {
        return PromoJPAAdapter(promoJPARepository, objectMapper)
    }
}
