package com.riza.apipromo.feature.promo.models

enum class PromoType{
        PERCENT,
        PRICE
}

data class AddPromoRequest(
        val code: String = "",
        val startDate: Long = 0L,
        val endDate: Long = 0L,
        val type: PromoType = PromoType.PRICE,
        val value: Int = 0,
        val service: String = "",
        val description: String = "",
        val areaIds: List<Long> = emptyList(),
        val threshold: Int = 1
)