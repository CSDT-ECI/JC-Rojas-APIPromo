package com.riza.apipromo.domain.area

import com.riza.apipromo.domain.geometry.Point
import com.riza.apipromo.domain.geometry.PointInclusionAlgorithm
import com.riza.apipromo.domain.geometry.PointInclusionMethod
import jdk.jshell.spi.ExecutionControl.NotImplementedException

open class AreaService(
    private val areaRepository: AreaRepository,
    private val pointInclusionStrategies: Map<PointInclusionMethod, PointInclusionAlgorithm>,
) {
    fun checkPointInArea(
        areaId: Long,
        point: Point,
        method: PointInclusionMethod,
    ): Boolean? {
        val area = areaRepository.findById(areaId)
        val pointInclusionStrategy =
            pointInclusionStrategies[method]
                ?: throw NotImplementedException("Selected method is not implemented in the system")
        return area?.checkPointInsideArea(point, pointInclusionStrategy)
    }

    fun checkAllPointsInArea(
        areaId: Long,
        points: List<Point>,
        method: PointInclusionMethod,
    ): List<Boolean>? {
        val area = areaRepository.findById(areaId)
        val pointInclusionStrategy =
            pointInclusionStrategies[method]
                ?: throw NotImplementedException("Selected method is not implemented in the system")
        return area?.checkMultiplePointsInsideArea(points, pointInclusionStrategy)
    }

    fun save(area: Area): Area {
        area.initialize()
        return areaRepository.save(area)
    }

    fun findAll(): List<Area> {
        return areaRepository.findAll()
    }

    fun deleteById(areaId: Long) {
        areaRepository.deleteById(areaId)
    }
}
