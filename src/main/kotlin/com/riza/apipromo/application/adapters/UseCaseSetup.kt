package com.riza.apipromo.application.adapters

import com.fasterxml.jackson.databind.ObjectMapper
import com.riza.apipromo.domain.area.AreaRepository
import com.riza.apipromo.domain.area.AreaService
import com.riza.apipromo.domain.geometry.CrossingNumberAlgorithm
import com.riza.apipromo.domain.geometry.PointInclusionAlgorithm
import com.riza.apipromo.domain.geometry.PointInclusionMethod
import com.riza.apipromo.domain.geometry.WindingNumberAlgorithm
import com.riza.apipromo.domain.promo.PromoRepository
import com.riza.apipromo.domain.promo.PromoService
import com.riza.apipromo.domain.user.UserRepository
import com.riza.apipromo.domain.user.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UseCaseSetup {
    @Bean
    fun crossingNumberAlgorithm(): Pair<PointInclusionMethod, PointInclusionAlgorithm> {
        return Pair(PointInclusionMethod.CN, CrossingNumberAlgorithm())
    }

    @Bean
    fun windingNumberAlgorithm(): Pair<PointInclusionMethod, PointInclusionAlgorithm> {
        return Pair(PointInclusionMethod.WN, WindingNumberAlgorithm())
    }

    @Bean
    fun areaService(
        areaRepository: AreaRepository,
        pointInclusionStrategies: List<Pair<PointInclusionMethod, PointInclusionAlgorithm>>,
    ): AreaService {
        return AreaService(areaRepository, pointInclusionStrategies.toMap())
    }

    @Bean
    fun userService(userRepository: UserRepository): UserService {
        return UserService(userRepository)
    }

    @Bean
    fun promoService(
        promoRepository: PromoRepository,
        userRepository: UserRepository,
        areaRepository: AreaRepository,
        pointInclusionStrategies: List<Pair<PointInclusionMethod, PointInclusionAlgorithm>>,
    ): PromoService {
        return PromoService(promoRepository, areaRepository, userRepository, pointInclusionStrategies.toMap())
    }

    @Bean
    fun getObjectMapper() = ObjectMapper()
}
