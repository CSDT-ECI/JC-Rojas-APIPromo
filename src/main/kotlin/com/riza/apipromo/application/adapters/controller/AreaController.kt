package com.riza.apipromo.application.adapters.controller

import com.riza.apipromo.domain.area.Area
import com.riza.apipromo.domain.area.AreaService
import com.riza.apipromo.domain.geometry.Point
import com.riza.apipromo.domain.geometry.PointInclusionMethod
import com.riza.apipromo.domain.geometry.Polygon
import com.riza.apipromo.v1.AreasApi
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class AreaController(private val areaService: AreaService) : AreasApi {
    override fun checkAllPointsInArea(
        areaId: Long,
        method: String,
        checkManyPointRequest: com.riza.apipromo.v1.domain.CheckManyPointRequest,
    ): ResponseEntity<List<Boolean>> {
        val pointsInside =
            areaService.checkAllPointsInArea(
                areaId,
                checkManyPointRequest.points.map { Point(it.x, it.y) },
                PointInclusionMethod.valueOf(method),
            )
        return if (pointsInside != null) {
            ResponseEntity.ok(pointsInside)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    override fun checkPointInArea(
        areaId: Long,
        method: String,
        checkPointRequest: com.riza.apipromo.v1.domain.CheckPointRequest,
    ): ResponseEntity<Boolean> {
        val isInside =
            areaService.checkPointInArea(
                areaId,
                Point(checkPointRequest.point.x, checkPointRequest.point.y),
                PointInclusionMethod.valueOf(method),
            )
        return if (isInside != null) {
            ResponseEntity.ok(isInside)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    override fun createArea(areaRequest: com.riza.apipromo.v1.domain.AreaRequest): ResponseEntity<com.riza.apipromo.v1.domain.Area> {
        val area =
            Area(
                polygon = Polygon(areaRequest.name, ArrayList(areaRequest.points.map { Point(it.x, it.y) })),
                promos = mutableSetOf(),
            )

        val savedArea = areaService.save(area)
        return ResponseEntity.ok(savedArea.convertToView())
    }

    override fun getAllAreas(): ResponseEntity<List<com.riza.apipromo.v1.domain.Area>> {
        return ResponseEntity.ok(areaService.findAll().map { it.convertToView() })
    }

    override fun deleteAreaById(id: Long): ResponseEntity<Unit> {
        return ResponseEntity.ok(areaService.deleteById(id))
    }
}
