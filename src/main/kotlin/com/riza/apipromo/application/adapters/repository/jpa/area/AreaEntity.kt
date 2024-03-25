package com.riza.apipromo.application.adapters.repository.jpa.area

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.riza.apipromo.application.adapters.repository.jpa.promo.PromoEntity
import com.riza.apipromo.core.Point
import com.riza.apipromo.core.Polygon
import com.riza.apipromo.domain.area.Area
import jakarta.persistence.*

@Entity(name = "area")
data class AreaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    var name: String,
    @Column(columnDefinition = "text")
    var points: String,
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "areas")
    @JsonIgnoreProperties("areas")
    var promos: MutableSet<PromoEntity>,
) {
    fun toDomain(objectMapper: ObjectMapper): Area {
        return Area(
            id,
            Polygon(name, objectMapper.readValue<ArrayList<Point>>(points)),
            promos.map { it.toDomain(objectMapper) }.toMutableSet(),
        )
    }

    companion object {
        fun convert(
            area: Area,
            objectMapper: ObjectMapper,
        ): AreaEntity {
            return AreaEntity(
                area.id,
                area.polygon.name,
                objectMapper.writeValueAsString(area.polygon.points),
                area.promos.map { PromoEntity.convert(it, objectMapper) }.toHashSet(),
            )
        }
    }
}
