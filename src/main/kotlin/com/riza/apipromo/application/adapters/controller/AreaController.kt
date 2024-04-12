package com.riza.apipromo.application.adapters.controller

import com.riza.apipromo.base.BaseResponse
import com.riza.apipromo.core.PointInclusionMethod
import com.riza.apipromo.core.Polygon
import com.riza.apipromo.domain.area.Area
import com.riza.apipromo.domain.area.AreaService
import com.riza.apipromo.error.BadRequestException
import com.riza.apipromo.feature.area.models.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["area"])
class AreaController(
    private val areaService: AreaService,
) {
    @GetMapping("test")
    @ResponseBody
    fun test(): BaseResponse<String> {
        return BaseResponse<String>().apply {
            data = "Hello World!"
        }
    }

    @PostMapping("add")
    @ResponseBody
    fun add(
        @RequestBody body: AreaRequest,
    ): BaseResponse<Area> {
        val area =
            Area(
                polygon = Polygon(body.name, ArrayList(body.points)),
                promos = mutableSetOf(),
            )

        val saved = areaService.save(area)

        return BaseResponse(
            message = "Berhasil menambah Area",
            data = saved,
        )
    }

    @GetMapping("all")
    @ResponseBody
    fun all(): BaseResponse<Iterable<Area>> {
        return BaseResponse(
            message = "semua area",
            data = areaService.findAll(),
        )
    }

    @PostMapping("delete")
    @ResponseBody
    fun delete(
        @RequestBody body: IdOnlyRequest,
    ): BaseResponse<Area> {
        areaService.deleteById(body.id)

        return BaseResponse(
            "Berhasil menghapus",
        )
    }

    @PostMapping("check/{method}")
    @ResponseBody
    fun check(
        @RequestBody body: CheckPointRequest,
        @PathVariable("method") method: PointInclusionMethod,
    ): BaseResponse<Boolean> {
        val result = BaseResponse<Boolean>()
        val isInside = areaService.checkPointInArea(body.areaId, body.point, method)

        if (isInside != null) {
            result.data = isInside
            result.message = if (isInside) "Point di dalam" else "Point di luar"
        } else {
            throw BadRequestException("Area id ${body.areaId} Tidak ditemukan")
        }

        return result
    }

    @PostMapping("checkall/{method}")
    @ResponseBody
    fun checkAll(
        @RequestBody body: CheckManyPointRequest,
        @PathVariable("method") method: PointInclusionMethod,
    ): BaseResponse<List<Boolean>> {
        val response = BaseResponse<List<Boolean>>()

        val result = areaService.checkAllPointsInArea(body.areaId, body.points, method)
        if (result != null) {
            response.message = "Berhasil menganalisa points"
            response.data = result
        } else {
            throw BadRequestException("Area id ${body.areaId} Tidak ditemukan")
        }
        return response
    }
}
