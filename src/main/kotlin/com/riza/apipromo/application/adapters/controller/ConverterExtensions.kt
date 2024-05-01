package com.riza.apipromo.application.adapters.controller

import com.riza.apipromo.domain.area.Area
import com.riza.apipromo.domain.geometry.Polygon
import com.riza.apipromo.domain.promo.Promo
import com.riza.apipromo.domain.user.User
import java.time.ZoneOffset

fun Area.convertToView(): com.riza.apipromo.v1.domain.Area {
    return com.riza.apipromo.v1.domain.Area(
        id = id,
        polygon = polygon.convertToView(),
        promos = promos.map { it.convertToView() },
    )
}

fun Promo.convertToView(): com.riza.apipromo.v1.domain.Promo {
    return com.riza.apipromo.v1.domain.Promo(
        id = id,
        service = service,
        threshold = threshold,
        value = value,
        type = type?.let { com.riza.apipromo.v1.domain.Promo.Type.valueOf(it.name) },
        users = users.map { it.convertToView() },
        areas = areas.map { it.convertToView() },
        code = code,
        description = description,
        startDate = startDate.atOffset(ZoneOffset.UTC),
        endDate = endDate?.atOffset(ZoneOffset.UTC),
    )
}

fun User.convertToView(): com.riza.apipromo.v1.domain.User {
    return com.riza.apipromo.v1.domain.User(
        id = id,
        name = name,
        fcmId = fcmId,
        locations =
            locations.map {
                it.key.name to
                    it.value.map { point ->
                        com.riza.apipromo.v1.domain.Point(
                            point.x,
                            point.y,
                        )
                    }
            }.toMap(),
        promos = promos.map { it.convertToView() },
    )
}

fun Polygon.convertToView(): com.riza.apipromo.v1.domain.Polygon {
    return com.riza.apipromo.v1.domain.Polygon(
        name = name,
        points = points.map { com.riza.apipromo.v1.domain.Point(it.x, it.y) },
    )
}
