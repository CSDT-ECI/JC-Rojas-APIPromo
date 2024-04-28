package com.riza.apipromo.domain.geometry

interface PointInclusionAlgorithm {
    fun isPointInsidePolygon(
        point: Point,
        polygon: Polygon,
    ): Boolean
}
