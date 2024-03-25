package com.riza.apipromo.domain.area

import com.riza.apipromo.core.Point
import com.riza.apipromo.core.PointInclusionMethod
import com.riza.apipromo.domain.PointInclusionAlgorithm

class AreaService(
    private val areaRepository: AreaRepository,
    private val pointInclusionStrategies: Map<PointInclusionMethod, PointInclusionAlgorithm>,
) {
    fun checkPointInArea(
        areaId: Long,
        point: Point,
        method: PointInclusionMethod,
    ): Boolean? {
        val area = areaRepository.findById(areaId)
        return area?.checkPointInsideArea(point, pointInclusionStrategies[method]!!)
    }

    fun checkAllPointsInArea(
        areaId: Long,
        points: List<Point>,
        method: PointInclusionMethod,
    ): List<Boolean>? {
        val area = areaRepository.findById(areaId)
        return area?.checkMultiplePointsInsideArea(points, pointInclusionStrategies[method]!!)
    }

    fun save(area: Area): Area {
        return areaRepository.save(area)
    }

    fun findAll(): Iterable<Area> {
        return areaRepository.findAll()
    }

    fun deleteById(areaId: Long) {
        areaRepository.deleteById(areaId)
    }
}
