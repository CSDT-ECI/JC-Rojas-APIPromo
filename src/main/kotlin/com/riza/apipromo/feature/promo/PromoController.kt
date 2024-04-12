package com.riza.apipromo.feature.promo

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.riza.apipromo.base.BaseResponse
import com.riza.apipromo.core.Point
import com.riza.apipromo.core.PointInclusion
import com.riza.apipromo.error.BadRequestException
import com.riza.apipromo.feature.area.AreaRepository
import com.riza.apipromo.feature.area.models.AreaDTO
import com.riza.apipromo.feature.promo.models.AddPromoRequest
import com.riza.apipromo.feature.promo.models.PromoDTO
import com.riza.apipromo.feature.user.UserRepository
import com.riza.apipromo.feature.user.models.UserDTO
import com.riza.apipromo.utils.Utils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*

@Controller
@RequestMapping(path = ["promo"])
class PromoController
    @Autowired
    constructor(
        private val areaRepository: AreaRepository,
        private val promoRepository: PromoRepository,
        private val userRepository: UserRepository,
        private val pointInclusion: PointInclusion,
        private val objectMapper: ObjectMapper,
    ) {
        companion object {
            const val ALGORITHM = "CN"
        }

        @PostMapping("add")
        @ResponseBody
        fun addPromo(
            @RequestBody promoRequest: AddPromoRequest,
        ): BaseResponse<PromoDTO> {
            if (promoRequest.areaIds.isEmpty()) {
                throw BadRequestException("Area ID Kosong")
            }

            val areas: Iterable<AreaDTO> = areaRepository.findAllById(promoRequest.areaIds)

            val user = getUserLocIn(areas, promoRequest.threshold)

            val promo =
                PromoDTO(
                    code = promoRequest.code,
                    startDate = Date(promoRequest.startDate),
                    endDate = Date(promoRequest.endDate),
                    type = promoRequest.type,
                    value = promoRequest.value,
                    service = promoRequest.service,
                    description = promoRequest.description,
                    areas = areas.toHashSet(),
                    users = user.toHashSet(),
                    threshold = promoRequest.threshold,
                )

            val result = promoRepository.save(promo)

            return BaseResponse(
                data = result,
            )
        }

        private fun getUserLocIn(
            areas: Iterable<AreaDTO>,
            threshold: Int = 1,
        ): List<UserDTO> {
            val result = arrayListOf<UserDTO>()
            val users = userRepository.findAll()

            users.forEach { user: UserDTO ->

                var isInside = false

                val locationDay = arrayListOf<String>()
                val locationHistory = arrayListOf<Point>()
                // todo excluding
                locationDay.add(user.locations.monday)
                locationDay.add(user.locations.tuesday)
                locationDay.add(user.locations.wednesday)
                locationDay.add(user.locations.thursday)
                locationDay.add(user.locations.friday)
                locationDay.add(user.locations.saturday)
                locationDay.add(user.locations.sunday)

                locationDay.forEach {
                    val point = objectMapper.readValue<List<Point>>(it)
                    locationHistory.addAll(point)
                }

                for (area: AreaDTO in areas) {
                    val polygon = Utils.area2Polygon(area, objectMapper)
                    var insideCount = 0

                    for (it in locationHistory) {
                        when (ALGORITHM) {
                            "CN" -> if (pointInclusion.analyzePointByCN(polygon, it)) insideCount++
                            else -> if (pointInclusion.analyzePointByWN(polygon, it)) insideCount++
                        }

                        if (insideCount >= threshold) {
                            isInside = true
                            break
                        }
                    }

                    if (isInside) {
                        result.add(user)
                        break
                    }
                }
            }

            return result
        }

        @GetMapping("all")
        @ResponseBody
        fun getAllPromo(): BaseResponse<Iterable<PromoDTO>> {
            var result: Iterable<PromoDTO>? = null
            validate {
                result = promoRepository.findAll()
            }
            return BaseResponse(data = result)
        }

        private fun validate(action: () -> Unit) {
            try {
                action.invoke()
            } catch (e: Exception) {
                throw BadRequestException(e.message.toString())
            }
        }
    }
