package com.riza.apipromo.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.riza.apipromo.core.Point
import com.riza.apipromo.core.Polygon
import com.riza.apipromo.feature.area.models.AreaDTO

object Utils {

    fun area2Polygon(areaDTO: AreaDTO, objectMapper: ObjectMapper): Polygon {
        val points = objectMapper.readValue<List<Point>>(areaDTO.points)
        return Polygon(
                areaDTO.name,
                ArrayList(points)
        )
    }

    fun findMedianPoint(points: List<Point>): Point?{
        if(points.isEmpty()) return null

        val midX = points.sortedBy { it.x }[points.lastIndex / 2].x
        val midY = points.sortedBy { it.y }[points.lastIndex / 2].y
        return Point(midX, midY)
    }

}