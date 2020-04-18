package com.riza.apipromo.feature.promo

import com.riza.apipromo.feature.area.AreaDTO
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Size

@Entity(name = "promo")
data class PromoDTO(
        @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long,
        @Size(max = 20) val code: String,
        @Temporal(TemporalType.DATE) val startDate: Date,
        @Temporal(TemporalType.DATE) val endDate: Date,
        @Size(max = 10) val type: String,
        val value: Int,
        @Size(max = 20) val service: String,

        @ManyToMany(fetch = FetchType.LAZY,
                cascade =
                [CascadeType.PERSIST,
                    CascadeType.MERGE
                ])
        @JoinTable(name = "promo_area",
                joinColumns = [JoinColumn(name = "promo_id")],
                inverseJoinColumns = [JoinColumn(name = "area_id")])
        val areas: Set<AreaDTO> = emptySet()
)

data class AddPromoRequest(
        val code: String,
        val startDate: String,
        val endDate: String,
        val type: String,
        val value: Int,
        val service: String,
        val areaIds: List<Long> = emptyList()
)