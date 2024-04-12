package com.riza.apipromo.core

class PointInclusion {
    /*
    CROSSING NUMBER
     */

    fun analyzePointByCN(
        poly: Polygon,
        p: Point,
    ): Boolean {
        val boundingBox = BoundingBox(poly)

        if (boundingBox.isInside(p)) {
            val cn = countCN(poly, p)
            println("cn is $cn")
            return (cn % 2 != 0)
        } else {
            return false
        }
    }

    private fun countCN(
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

    /*
    WINDING NUMBER
     */

    fun analyzePointByWN(
        poly: Polygon,
        p: Point,
    ): Boolean {
        val boundingBox = BoundingBox(poly)

        if (boundingBox.isInside(p)) {
            val wn = countWN(poly, p)
            println("wn is $wn")
            return (wn != 0)
        } else {
            return false
        }
    }

    private fun countWN(
        poly: Polygon,
        point: Point,
    ): Int {
        var wn = 0

        val points = poly.points

        for (i in 0 until points.size - 1) {
            val edgeStart = points[i]
            val edgeEnd = points[i + 1]

            wn += calculateCrossingValueForEdge(edgeStart, edgeEnd, point)
        }

        return wn
    }

    private fun calculateCrossingValueForEdge(
        edgeStart: Point,
        edgeEnd: Point,
        point: Point,
    ): Int {
        if (edgeStart.y <= point.y) {
            if (edgeEnd.y > point.y) {
                if (checkPointPositionAgainstLine(edgeStart, edgeEnd, point) > 0) return 1
            }
        } else {
            if (edgeEnd.y <= point.y) {
                if (checkPointPositionAgainstLine(edgeStart, edgeEnd, point) < 0) return -1
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
