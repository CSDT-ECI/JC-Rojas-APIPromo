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
        val n = points.size - 1

        for (i in 0 until n) {
            val edge1 = points[i]
            val edge2 = points[i + 1]

            if (
                isPointBetweenYEdgesCoordinates(point, edge1, edge2) && isPointOnLeftOfEdges(point, edge1, edge2)
            ) {
                val xIntersection = calculateXIntersection(point, edge1, edge2)
                if (point.x < xIntersection) {
                    ++crossings
                }
            }
        }

        return crossings
    }

    private fun isPointOnLeftOfEdges(
        point: Point,
        edge1: Point,
        edge2: Point,
    ) = point.x <= edge1.x && point.x <= edge2.x

    private fun isPointBetweenYEdgesCoordinates(
        point: Point,
        edge1: Point,
        edge2: Point,
    ) = (edge1.y <= point.y && edge2.y > point.y) || (edge1.y > point.y && edge2.y <= point.y)

    private fun calculateXIntersection(
        point: Point,
        edge1: Point,
        edge2: Point,
    ): Double {
        val projection = (point.y - edge1.y) / (edge2.y - edge1.y)
        return edge1.x + projection * (edge2.x - edge1.x)
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
        p: Point,
    ): Int {
        var wn = 0

        val v = poly.points
        val n = v.size - 1

        for (i in 0 until n) {
            if (v[i].x == v[i + 1].x) continue // rule 3

            if (v[i].y <= p.y) {
                if (v[i + 1].y > p.y) {
                    if (isLeft(v[i], v[i + 1], p)) ++wn
                }
            } else {
                if (v[i + 1].y <= p.y) {
                    if (!isLeft(v[i], v[i+1], p)) --wn
                }
            }
        }

        return wn
    }

    private fun isLeft(
        p0: Point,
        p1: Point,
        p2: Point,
    ): Boolean {
        val n = (p1.x - p0.x) * (p2.y - p0.y) - (p2.x - p0.x) * (p1.y - p0.y)
        return (n > 0)
    }
}
