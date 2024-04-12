package com.riza.apipromo.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.riza.apipromo.core.Point
import com.riza.apipromo.core.Polygon
import com.riza.apipromo.feature.area.models.AreaDTO

object Utils {
    fun area2Polygon(
        areaDTO: AreaDTO,
        objectMapper: ObjectMapper,
    ): Polygon {
        val points = objectMapper.readValue<List<Point>>(areaDTO.points)
        return Polygon(
            areaDTO.name,
            ArrayList(points),
        )
    }
}
