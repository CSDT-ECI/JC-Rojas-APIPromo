package com.riza.apipromo.domain

import com.riza.apipromo.core.Point
import com.riza.apipromo.core.Polygon

interface PointInclusionAlgorithm {
    fun isPointInsidePolygon(
        point: Point,
        polygon: Polygon,
    ): Boolean
}
