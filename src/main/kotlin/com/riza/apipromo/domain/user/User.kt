package com.riza.apipromo.domain.user

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.riza.apipromo.core.Point
import com.riza.apipromo.domain.promo.Promo
import com.riza.apipromo.error.BadRequestException
import com.riza.apipromo.feature.user.models.UserLocation

data class User(
    var id: Long? = null,
    var name: String,
    var fcmId: String = "",
    // TODO:REWORK USER LOCATIONS
    var locations: UserLocation = UserLocation(),
    var promos: MutableSet<Promo>,
) {
    fun updateLocation(
        day: String,
        location: String,
    ) {
        when (day) {
            "monday" -> {
                locations.monday = location
            }
            "tuesday" -> {
                locations.tuesday = location
            }
            "wednesday" -> {
                locations.wednesday = location
            }
            "thursday" -> {
                locations.thursday = location
            }
            "friday" -> {
                locations.friday = location
            }
            "saturday" -> {
                locations.saturday = location
            }
            "sunday" -> {
                locations.sunday = location
            }

            else -> {
                throw BadRequestException("Hari tidak valid")
            }
        }
    }

    fun calculateLocationsAsPoints(objectMapper: ObjectMapper): List<Point> {
        val locationDay = arrayListOf<String>()
        val locationHistory = arrayListOf<Point>()
        // todo excluding
        locationDay.add(locations.monday)
        locationDay.add(locations.tuesday)
        locationDay.add(locations.wednesday)
        locationDay.add(locations.thursday)
        locationDay.add(locations.friday)
        locationDay.add(locations.saturday)
        locationDay.add(locations.sunday)

        locationDay.forEach {
            val point = objectMapper.readValue<List<Point>>(it)
            locationHistory.addAll(point)
        }
        return locationHistory
    }
}
