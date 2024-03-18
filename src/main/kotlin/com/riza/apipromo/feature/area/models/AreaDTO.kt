package com.riza.apipromo.feature.area.models


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.riza.apipromo.feature.promo.models.PromoDTO
import jakarta.persistence.*

@Entity(name = "area")
data class AreaDTO(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    var name: String,
    @Column(columnDefinition = "text")
    var points:String,
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "areas")
    @JsonIgnoreProperties("areas")
    var promos: MutableSet<PromoDTO>
)
