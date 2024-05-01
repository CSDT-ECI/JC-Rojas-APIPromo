package com.riza.apipromo.domain.promo

import com.riza.apipromo.domain.area.AreaRepository
import com.riza.apipromo.domain.geometry.PointInclusionAlgorithm
import com.riza.apipromo.domain.geometry.PointInclusionMethod
import com.riza.apipromo.domain.user.UserRepository
import jdk.jshell.spi.ExecutionControl

class PromoService(
    private val promoRepository: PromoRepository,
    private val areaRepository: AreaRepository,
    private val userRepository: UserRepository,
    private val pointInclusionStrategies: Map<PointInclusionMethod, PointInclusionAlgorithm>,
) {
    fun findAll(): List<Promo> {
        return promoRepository.findAll()
    }

    fun addPromo(
        promo: Promo,
        areaIds: List<Long>,
        method: PointInclusionMethod,
    ): Promo? {
        val areas = areaRepository.findAllById(areaIds)
        if (areas.isEmpty()) return null
        val users = userRepository.findAll()
        promo.areas = areas.toMutableSet()
        val pointInclusionStrategy =
            pointInclusionStrategies[method]
                ?: throw ExecutionControl.NotImplementedException("Selected method is not implemented in the system")
        promo.calculateBenefitedUsers(users, areas, pointInclusionStrategy)

        return promoRepository.save(promo)
    }
}
