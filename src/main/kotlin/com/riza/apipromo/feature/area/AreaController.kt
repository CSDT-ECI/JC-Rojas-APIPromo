package com.riza.apipromo.feature.area

import com.fasterxml.jackson.databind.ObjectMapper
import com.riza.apipromo.base.BaseResponse
import com.riza.apipromo.core.PointInclusion
import com.riza.apipromo.error.BadRequestException
import com.riza.apipromo.feature.area.models.*
import com.riza.apipromo.utils.Utils
import org.hibernate.annotations.common.util.impl.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(path = ["area"])
class AreaController @Autowired constructor(
        private val pointInclusion: PointInclusion,
        private val objectMapper: ObjectMapper,
        private val areaRepository: AreaRepository
) {

    private val logger = LoggerFactory.logger(AreaController::class.java)

    @GetMapping("test")
    @ResponseBody
    fun test(): BaseResponse<String> {
        return BaseResponse<String>().apply {
            data = "Hello World!"
        }

    }

    @PostMapping("add")
    @ResponseBody
    fun add(@RequestBody body: AreaRequest): BaseResponse<AreaDTO> {

        val points = objectMapper.writeValueAsString(body.points)
        val area = AreaDTO(
                body.name,
                points,
                emptySet()
        )

        val saved = areaRepository.save(area)

        return BaseResponse(
                message = "Berhasil menambah Area",
                data = saved
        )

    }

    @GetMapping("all")
    @ResponseBody
    fun all(): BaseResponse<Iterable<AreaDTO>> {

        return BaseResponse(
                message = "semua area",
                data = areaRepository.findAll()
        )

    }

    @PostMapping("delete")
    @ResponseBody
    fun delete(@RequestBody body: IdOnlyRequest): BaseResponse<AreaDTO> {

        areaRepository.deleteById(body.id)

        return BaseResponse(
                "Berhasil menghapus"
        )

    }

    @PostMapping("check/{method}")
    @ResponseBody
    fun check(
            @RequestBody body: CheckPointRequest,
            @PathVariable("method") method: String
    ): BaseResponse<Boolean> {

        val result = BaseResponse<Boolean>()

        val area = areaRepository.findById(body.areaId)
        logger.info("req $body -> $area")
        var isInside = false
        area.ifPresentOrElse({
            val polygon = Utils.area2Polygon(it, objectMapper)
            isInside = if (method == "wn") {
                pointInclusion.analyzePointByCN(polygon, body.point)
            } else {
                pointInclusion.analyzePointByWN(polygon, body.point)
            }
            result.data = isInside
            result.message = if (isInside) "Point di dalam" else "Point di luar"
        }, {
            throw BadRequestException("Area id ${body.areaId} Tidak ditemukan")
        })

        return result

    }


    @PostMapping("checkall/{method}")
    @ResponseBody
    fun checkAll(
            @RequestBody body: CheckManyPointRequest,
            @PathVariable("method") method: String
    ): BaseResponse<List<Boolean>> {
        val response = BaseResponse<List<Boolean>>()
        val result = arrayListOf<Boolean>()
        val area = areaRepository.findById(body.areaId)
        logger.info("req $body -> $area")
        area.ifPresentOrElse({

            val polygon = Utils.area2Polygon(it, objectMapper)

            body.points.forEach {
                val inside = if (method == "wn") {
                    pointInclusion.analyzePointByCN(polygon, it)
                } else {
                    pointInclusion.analyzePointByWN(polygon, it)
                }
                result.add(inside)
            }
            response.message = "Berhasil menganalisa points"
            response.data = result

        }, {
            throw BadRequestException("Area id ${body.areaId} Tidak ditemukan")
        })

        return response
    }


}