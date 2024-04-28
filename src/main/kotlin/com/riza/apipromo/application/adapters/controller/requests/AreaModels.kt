package com.riza.apipromo.application.adapters.controller.requests

import com.riza.apipromo.domain.geometry.Point

data class CheckPointRequest(
    val point: Point = Point(),
)

data class CheckManyPointRequest(
    val points: List<Point> = emptyList(),
)

data class AreaRequest(
    val name: String = "",
    val points: List<Point> = emptyList(),
)
