package com.riza.apipromo.feature.area

import com.fasterxml.jackson.annotation.JsonIgnore
import com.riza.apipromo.core.Point
import com.riza.apipromo.feature.promo.PromoDTO
import javax.persistence.*

@Entity(name = "area")
data class AreaDTO(
        @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long,
        val name: String,
        val points: String,

        @JsonIgnore
        @ManyToMany(fetch = FetchType.LAZY,
                cascade = [
                    CascadeType.PERSIST,
                    CascadeType.MERGE
                ],

                mappedBy = "areas")
        val promos: Set<PromoDTO> = emptySet()
)

data class IdOnlyRequest(
        val id: Long = 0L
)

data class CheckPointRequest(
        val point: Point = Point(),
        val areaId: Long = 0L
)

data class CheckManyPointRequest(
        val points:  List<Point> = emptyList(),
        val areaId: Long = 0L
)

data class AreaRequest(
        val name: String = "",
        val points: List<Point> = emptyList()
)