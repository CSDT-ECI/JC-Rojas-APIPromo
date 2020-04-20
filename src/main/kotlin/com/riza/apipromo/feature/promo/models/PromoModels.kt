package com.riza.apipromo.feature.promo.models

enum class PromoType{
        PERCENT,
        PRICE
}

data class AddPromoRequest(
        val code: String = "",
        val startDate: String = "",
        val endDate: String = "",
        val type: PromoType = PromoType.PRICE,
        val value: Int = 0,
        val service: String = "",
        val areaIds: List<Long> = emptyList()
)