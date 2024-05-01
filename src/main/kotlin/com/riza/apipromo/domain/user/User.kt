package com.riza.apipromo.domain.user

import com.riza.apipromo.domain.WeekDay
import com.riza.apipromo.domain.geometry.Point
import com.riza.apipromo.domain.promo.Promo
import java.util.EnumMap

data class User(
    var id: Long? = null,
    var name: String,
    var fcmId: String = "",
    val locations: EnumMap<WeekDay, List<Point>> = EnumMap(WeekDay.entries.associateWith { listOf() }),
    var promos: MutableSet<Promo>,
) {
    fun updateLocation(
        day: WeekDay,
        locations: List<Point>,
    ) {
        this.locations[day].let {
            this.locations[day] = locations
        }
    }

    fun calculateLocationsAsPoints(): List<Point> {
        return locations.values.flatten()
    }

    fun patch(fcm: String) {
        fcmId = fcm
    }
}
