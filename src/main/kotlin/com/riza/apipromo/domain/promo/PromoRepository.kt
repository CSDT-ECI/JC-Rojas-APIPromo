package com.riza.apipromo.domain.promo

interface PromoRepository {
    fun findAll(): List<Promo>

    fun save(promo: Promo): Promo
}
