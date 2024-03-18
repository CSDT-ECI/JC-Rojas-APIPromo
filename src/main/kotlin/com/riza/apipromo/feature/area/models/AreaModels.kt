package com.riza.apipromo.feature.area.models

import com.riza.apipromo.core.Point

data class IdOnlyRequest(
    val id: Long = 0L,
)

data class CheckPointRequest(
    val point: Point = Point(),
    val areaId: Long = 0L,
)

data class CheckManyPointRequest(
    val points: List<Point> = emptyList(),
    val areaId: Long = 0L,
)

data class AreaRequest(
    val name: String = "",
    val points: List<Point> = emptyList(),
)
