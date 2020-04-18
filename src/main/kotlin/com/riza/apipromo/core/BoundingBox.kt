package com.riza.apipromo.core

class BoundingBox(private val p: Polygon) {

    private var xMin : Double
    private var xMax : Double
    private var yMax : Double
    private var yMin : Double

    init {
        xMin = p.points[0].x
        xMax = p.points[0].x
        yMin = p.points[0].y
        yMax = p.points[0].y

        p.points.forEach {
            if (it.x > xMax) xMax = it.x
            if (it.x < xMin) xMin = it.x
            if (it.y > yMax) yMax = it.y
            if (it.y < yMin) yMin = it.y
        }

    }


    fun isInside(point: Point): Boolean {
        return (point.x >= xMin && point.x <= xMax
                && point.y >= yMin && point.y <= yMax)
    }



}