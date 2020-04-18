package com.riza.apipromo.feature.area

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.riza.apipromo.core.Point
import com.riza.apipromo.core.PointInclusion
import com.riza.apipromo.core.Polygon
import com.riza.apipromo.base.BaseResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*


@Controller
@RequestMapping(path = ["area"])
class AreaController @Autowired constructor(
        private val pointInclusion: PointInclusion,
        private val objectMapper: ObjectMapper,
        private val areaRepository: AreaRepository
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
    fun add(@RequestBody body: AreaRequest): BaseResponse<AreaDTO>{

        val points = objectMapper.writeValueAsString(body.points)
        val area = AreaDTO(
                0,
                body.name,
                points
        )

        val saved = areaRepository.save(area)

        return BaseResponse(
                message = "Berhasil menambah Area",
                data = saved
        )

    }

    @GetMapping("all")
    @ResponseBody
    fun all(): BaseResponse<Iterable<AreaDTO>>{

        return BaseResponse(
                message = "semua area",
                data = areaRepository.findAll()
        )

    }

    @PostMapping("delete")
    @ResponseBody
    fun delete(@RequestBody body: IdOnlyRequest): BaseResponse<AreaDTO>{

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
    ): BaseResponse<Boolean>{

        val area = areaRepository.findById(body.areaId)
        var isInside = false
        area.ifPresent {
            val polygon = area2Polygon(it)
            isInside = if(method == "wn"){
                pointInclusion.analyzePointByCN(polygon, body.point)
            }else{
                pointInclusion.analyzePointByWN(polygon, body.point)
            }
        }

        return BaseResponse(
                message = "Berhasil",
                success = true,
                data = isInside

        )

    }

    private fun area2Polygon(areaDTO: AreaDTO): Polygon{
        val points = objectMapper.readValue<List<Point>>(areaDTO.points)
        return Polygon(
                areaDTO.name,
                ArrayList(points)
        )
    }







}