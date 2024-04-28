package com.riza.apipromo.application.adapters.controller

import com.riza.apipromo.application.adapters.controller.error.BadRequestException
import com.riza.apipromo.application.adapters.controller.requests.AreaRequest
import com.riza.apipromo.application.adapters.controller.requests.CheckManyPointRequest
import com.riza.apipromo.application.adapters.controller.requests.CheckPointRequest
import com.riza.apipromo.application.adapters.controller.responses.BaseResponse
import com.riza.apipromo.domain.area.Area
import com.riza.apipromo.domain.area.AreaService
import com.riza.apipromo.domain.geometry.PointInclusionMethod
import com.riza.apipromo.domain.geometry.Polygon
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["areas"])
class AreaController(
    private val areaService: AreaService,
) {
    @PostMapping
    @ResponseBody
    fun createArea(
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

    @GetMapping
    @ResponseBody
    fun getAllAreas(): BaseResponse<Iterable<Area>> {
        return BaseResponse(
            message = "semua area",
            data = areaService.findAll(),
        )
    }

    @DeleteMapping("{id}")
    @ResponseBody
    fun deleteAreaById(
        @PathVariable("id") id: Long,
    ): BaseResponse<Area> {
        areaService.deleteById(id)

        return BaseResponse(
            "Berhasil menghapus",
        )
    }

    @PostMapping("{areaId}/check/{method}")
    @ResponseBody
    fun checkPointInArea(
        @PathVariable("areaId") areaId: Long,
        @PathVariable("method") method: PointInclusionMethod,
        @RequestBody body: CheckPointRequest,
    ): BaseResponse<Boolean> {
        val result = BaseResponse<Boolean>()
        val isInside = areaService.checkPointInArea(areaId, body.point, method)

        if (isInside != null) {
            result.data = isInside
            result.message = if (isInside) "Point di dalam" else "Point di luar"
        } else {
            throw BadRequestException("Area id $areaId Tidak ditemukan")
        }

        return result
    }

    @PostMapping("{areaId}/checkall/{method}")
    @ResponseBody
    fun checkAllPointsInArea(
        @PathVariable("areaId") areaId: Long,
        @PathVariable("method") method: PointInclusionMethod,
        @RequestBody body: CheckManyPointRequest,
    ): BaseResponse<List<Boolean>> {
        val response = BaseResponse<List<Boolean>>()

        val result = areaService.checkAllPointsInArea(areaId, body.points, method)
        if (result != null) {
            response.message = "Berhasil menganalisa points"
            response.data = result
        } else {
            throw BadRequestException("Area id $areaId Tidak ditemukan")
        }
        return response
    }
}
