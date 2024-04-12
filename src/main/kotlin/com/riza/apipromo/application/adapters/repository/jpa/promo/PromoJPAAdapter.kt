package com.riza.apipromo.application.adapters.repository.jpa.promo

import com.fasterxml.jackson.databind.ObjectMapper
import com.riza.apipromo.domain.promo.Promo
import com.riza.apipromo.domain.promo.PromoRepository

class PromoJPAAdapter(
    private val promoJPARepository: PromoJPARepository,
    private val objectMapper: ObjectMapper,
) : PromoRepository {
    override fun findAll(): List<Promo> {
        return promoJPARepository.findAll().map { it.toDomain(objectMapper) }
    }

    override fun save(promo: Promo): Promo {
        return promoJPARepository.save(PromoEntity.convert(promo, objectMapper)).toDomain(objectMapper)
    }
}
