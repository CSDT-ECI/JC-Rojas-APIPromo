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
        p: Point,
    ): Int {
        var cn = 0

        val v = poly.points
        val n = v.size - 1

        for (i in 0 until n) {
            if (v[i].x == v[i + 1].x) continue // rule 3

            if (
                (v[i].y <= p.y && v[i + 1].y > p.y) || // rule1
                (v[i].y > p.y && v[i + 1].y <= p.y) // rule 2
            ) {
                val ray = (p.y - v[i].y) / (v[i + 1].y - v[i].y)

                if (p.x < v[i].x + ray * (v[i + 1].x - v[i].x)) {
                    ++cn
                }
            }
        }

        return cn
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
