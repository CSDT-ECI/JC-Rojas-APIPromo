package com.riza.apipromo.domain.promo

import com.fasterxml.jackson.databind.ObjectMapper
import com.riza.apipromo.domain.PointInclusionAlgorithm
import com.riza.apipromo.domain.area.Area
import com.riza.apipromo.domain.user.User
import com.riza.apipromo.feature.promo.models.PromoType
import java.util.*

data class Promo(
    var id: Long? = null,
    var code: String,
    var startDate: Date,
    var endDate: Date?,
    var type: PromoType?,
    var value: Int?,
    var service: String?,
    var description: String?,
    var threshold: Int,
    var areas: MutableSet<Area>,
    var users: MutableSet<User>,
) {
    fun calculateBenefitedUsers(
        allUsers: List<User>,
        areas: List<Area>,
        pointInclusionAlgorithm: PointInclusionAlgorithm,
        objectMapper: ObjectMapper,
    ) {
        allUsers.forEach { user: User ->
            val locations = user.calculateLocationsAsPoints(objectMapper)
            for (area in areas) {
                if (area.checkMinimumPointsInsideArea(locations, pointInclusionAlgorithm, threshold)) {
                    users.add(user)
                    break
                }
            }
        }
    }
}
