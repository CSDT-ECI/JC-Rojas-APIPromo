package com.riza.apipromo.domain.geometry

data class Polygon(
    val name: String,
    val points: ArrayList<Point>,
) {
    fun closePolygon() {
        points.getOrNull(0)?.let { points.add(it) }
    }
}
