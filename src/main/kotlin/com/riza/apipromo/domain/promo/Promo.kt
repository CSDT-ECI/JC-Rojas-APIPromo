package com.riza.apipromo.domain.promo

import com.riza.apipromo.domain.area.Area
import com.riza.apipromo.domain.geometry.PointInclusionAlgorithm
import com.riza.apipromo.domain.user.User
import java.time.LocalDateTime

data class Promo(
    var id: Long? = null,
    var code: String,
    var startDate: LocalDateTime,
    var endDate: LocalDateTime?,
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
    ) {
        allUsers.forEach { user: User ->
            val locations = user.calculateLocationsAsPoints()
            for (area in areas) {
                if (area.checkMinimumPointsInsideArea(locations, pointInclusionAlgorithm, threshold)) {
                    users.add(user)
                    break
                }
            }
        }
    }
}

enum class PromoType {
    PERCENT,
    PRICE,
}
