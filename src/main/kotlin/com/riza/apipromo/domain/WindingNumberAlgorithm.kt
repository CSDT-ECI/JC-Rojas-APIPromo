package com.riza.apipromo.domain

import com.riza.apipromo.core.BoundingBox
import com.riza.apipromo.core.Point
import com.riza.apipromo.core.Polygon
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class WindingNumberAlgorithm : PointInclusionAlgorithm {
    private val log: Logger = LoggerFactory.getLogger(WindingNumberAlgorithm::class.java)

    override fun isPointInsidePolygon(
        point: Point,
        polygon: Polygon,
    ): Boolean {
        val boundingBox = BoundingBox(polygon)

        return if (boundingBox.isInside(point)) {
            val windingNumber = calculateWindingNumber(polygon, point)
            log.info("wn is $windingNumber")
            (windingNumber != 0)
        } else {
            false
        }
    }

    private fun calculateWindingNumber(
        poly: Polygon,
        point: Point,
    ): Int {
        var windingNumber = 0

        val points = poly.points

        for (i in 0 until points.size - 1) {
            val edgeStart = points[i]
            val edgeEnd = points[i + 1]

            windingNumber += calculateCrossingValueForEdge(edgeStart, edgeEnd, point)
        }

        return windingNumber
    }

    private fun calculateCrossingValueForEdge(
        edgeStart: Point,
        edgeEnd: Point,
        point: Point,
    ): Int {
        if (edgeStart.y <= point.y) {
            if (edgeEnd.y > point.y && checkPointPositionAgainstLine(edgeStart, edgeEnd, point) > 0) {
                return 1
            }
        } else {
            if (edgeEnd.y <= point.y && checkPointPositionAgainstLine(edgeStart, edgeEnd, point) < 0) {
                return -1
            }
        }
        return 0
    }

    private fun checkPointPositionAgainstLine(
        edgeStart: Point,
        edgeEnd: Point,
        point: Point,
    ): Double {
        return (edgeEnd.x - edgeStart.x) * (point.y - edgeStart.y) - (point.x - edgeStart.x) * (edgeEnd.y - edgeStart.y)
    }
}
