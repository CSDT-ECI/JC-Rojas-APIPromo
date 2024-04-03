package com.riza.apipromo.application.adapters.controller

import com.riza.apipromo.base.BaseResponse
import com.riza.apipromo.core.PointInclusionMethod
import com.riza.apipromo.domain.promo.Promo
import com.riza.apipromo.domain.promo.PromoService
import com.riza.apipromo.error.BadRequestException
import com.riza.apipromo.feature.promo.models.AddPromoRequest
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*

@Controller
@RequestMapping(path = ["promo"])
class PromoController(
    private val promoService: PromoService,
) {
    val defaultAlgorithm = PointInclusionMethod.CN

    @PostMapping("add")
    @ResponseBody
    fun addPromo(
        @RequestBody promoRequest: AddPromoRequest,
    ): BaseResponse<Promo> {
        if (promoRequest.areaIds.isEmpty()) {
            throw BadRequestException("Area ID Kosong")
        }
        val promo =
            Promo(
                code = promoRequest.code,
                startDate = Date(promoRequest.startDate),
                endDate = Date(promoRequest.endDate),
                type = promoRequest.type,
                value = promoRequest.value,
                service = promoRequest.service,
                description = promoRequest.description,
                areas = mutableSetOf(),
                users = mutableSetOf(),
                threshold = promoRequest.threshold,
            )

        return BaseResponse(
            data = promoService.addPromo(promo, promoRequest.areaIds, defaultAlgorithm),
        )
    }

    @GetMapping("all")
    @ResponseBody
    fun getAllPromo(): BaseResponse<Iterable<Promo>> {
        var result: Iterable<Promo>? = null
        validate {
            result = promoService.findAll()
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