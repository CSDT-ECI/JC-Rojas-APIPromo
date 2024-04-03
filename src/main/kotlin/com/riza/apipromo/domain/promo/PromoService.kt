package com.riza.apipromo.domain.promo

import com.fasterxml.jackson.databind.ObjectMapper
import com.riza.apipromo.core.PointInclusionMethod
import com.riza.apipromo.domain.PointInclusionAlgorithm
import com.riza.apipromo.domain.area.AreaRepository
import com.riza.apipromo.domain.user.UserRepository
import jdk.jshell.spi.ExecutionControl

class PromoService(
    private val promoRepository: PromoRepository,
    private val areaRepository: AreaRepository,
    private val userRepository: UserRepository,
    private val pointInclusionStrategies: Map<PointInclusionMethod, PointInclusionAlgorithm>,
    private val objectMapper: ObjectMapper,
) {
    fun findAll(): List<Promo> {
        return promoRepository.findAll()
    }

    fun addPromo(
        promo: Promo,
        areaIds: List<Long>,
        method: PointInclusionMethod,
    ): Promo {
        val areas = areaRepository.findAllById(areaIds)
        val users = userRepository.findAll()
        promo.areas = areas.toMutableSet()
        val pointInclusionStrategy =
            pointInclusionStrategies[method]
                ?: throw ExecutionControl.NotImplementedException("Selected method is not implemented in the system")
        promo.calculateBenefitedUsers(users, areas, pointInclusionStrategy, objectMapper)

        return promoRepository.save(promo)
    }
}
