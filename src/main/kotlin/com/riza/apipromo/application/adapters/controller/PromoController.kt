package com.riza.apipromo.application.adapters.controller

import com.riza.apipromo.domain.geometry.PointInclusionMethod
import com.riza.apipromo.domain.promo.Promo
import com.riza.apipromo.domain.promo.PromoService
import com.riza.apipromo.domain.promo.PromoType
import com.riza.apipromo.v1.PromosApi
import com.riza.apipromo.v1.domain.AddPromoRequest
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class PromoController(
    private val promoService: PromoService,
) : PromosApi {
    val defaultAlgorithm = PointInclusionMethod.CN

    override fun createPromo(addPromoRequest: AddPromoRequest): ResponseEntity<com.riza.apipromo.v1.domain.Promo> {
        val promo =
            Promo(
                code = addPromoRequest.code,
                startDate = addPromoRequest.startDate.toLocalDateTime(),
                endDate = addPromoRequest.endDate?.toLocalDateTime(),
                type = PromoType.valueOf(addPromoRequest.type.value),
                value = addPromoRequest.value,
                service = addPromoRequest.service,
                description = addPromoRequest.description,
                areas = mutableSetOf(),
                users = mutableSetOf(),
                threshold = addPromoRequest.threshold,
            )
        val createdPromo = promoService.addPromo(promo, addPromoRequest.areaIds, defaultAlgorithm)
        return if (createdPromo != null) {
            ResponseEntity.ok(createdPromo.convertToView())
        } else {
            ResponseEntity.badRequest().build()
        }
    }

    override fun getAllPromos(): ResponseEntity<List<com.riza.apipromo.v1.domain.Promo>> {
        return ResponseEntity.ok(promoService.findAll().map { it.convertToView() })
    }
}
