package com.riza.apipromo.domain.area

import com.riza.apipromo.domain.geometry.Point
import com.riza.apipromo.domain.geometry.PointInclusionAlgorithm
import com.riza.apipromo.domain.geometry.Polygon
import com.riza.apipromo.domain.promo.Promo

data class Area(
    var id: Long? = null,
    var polygon: Polygon,
    var promos: MutableSet<Promo>,
) {
    fun checkPointInsideArea(
        point: Point,
        pointInclusionAlgorithm: PointInclusionAlgorithm,
    ): Boolean {
        return pointInclusionAlgorithm.isPointInsidePolygon(point, polygon)
    }

    fun checkMultiplePointsInsideArea(
        points: List<Point>,
        pointInclusionAlgorithm: PointInclusionAlgorithm,
    ): List<Boolean> {
        return points.map {
            checkPointInsideArea(it, pointInclusionAlgorithm)
        }
    }

    fun checkMinimumPointsInsideArea(
        points: List<Point>,
        pointInclusionAlgorithm: PointInclusionAlgorithm,
        minimumPoints: Int,
    ): Boolean {
        var pointsInside = 0
        points.forEach {
            if (checkPointInsideArea(it, pointInclusionAlgorithm)) pointsInside++
            if (pointsInside >= minimumPoints) return true
        }
        return false
    }
}
