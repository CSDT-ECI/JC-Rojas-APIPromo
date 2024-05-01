package com.riza.apipromo.domain.geometry

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class CrossingNumberAlgorithm : PointInclusionAlgorithm {
    private val log: Logger = LoggerFactory.getLogger(CrossingNumberAlgorithm::class.java)

    override fun isPointInsidePolygon(
        point: Point,
        polygon: Polygon,
    ): Boolean {
        val boundingBox = BoundingBox(polygon)

        return if (boundingBox.isInside(point)) {
            val crossingNumber = calculateCrossingNumber(polygon, point)
            log.info("cn is $crossingNumber")
            (crossingNumber % 2 != 0)
        } else {
            false
        }
    }

    private fun calculateCrossingNumber(
        poly: Polygon,
        point: Point,
    ): Int {
        var crossings = 0

        val points = poly.points

        for (i in 0 until points.size - 1) {
            val edgeStart = points[i]
            val edgeEnd = points[i + 1]

            if (
                isPointBetweenYEdgesCoordinates(point, edgeStart, edgeEnd) && isPointOnLeftOfEdges(point, edgeStart, edgeEnd)
            ) {
                val xIntersection = calculateXIntersection(point, edgeStart, edgeEnd)
                if (point.x < xIntersection) {
                    ++crossings
                }
            }
        }

        return crossings
    }

    private fun isPointOnLeftOfEdges(
        point: Point,
        edgeStart: Point,
        edgeEnd: Point,
    ) = point.x <= edgeStart.x && point.x <= edgeEnd.x

    private fun isPointBetweenYEdgesCoordinates(
        point: Point,
        edgeStart: Point,
        edgeEnd: Point,
    ) = (edgeStart.y <= point.y && edgeEnd.y > point.y) || (edgeStart.y > point.y && edgeEnd.y <= point.y)

    private fun calculateXIntersection(
        point: Point,
        edgeStart: Point,
        edgeEnd: Point,
    ): Double {
        val projection = (point.y - edgeStart.y) / (edgeEnd.y - edgeStart.y)
        return edgeStart.x + projection * (edgeEnd.x - edgeStart.x)
    }
}
