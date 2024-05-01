package com.riza.apipromo.application.adapters.repository.jpa.promo

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.riza.apipromo.application.adapters.repository.jpa.area.AreaEntity
import com.riza.apipromo.application.adapters.repository.jpa.user.UserEntity
import com.riza.apipromo.domain.promo.Promo
import com.riza.apipromo.domain.promo.PromoType
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "promo")
data class PromoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    var code: String,
    @Basic
    var startDate: LocalDateTime,
    @Basic
    var endDate: LocalDateTime?,
    @Enumerated(EnumType.STRING)
    var type: PromoType?,
    var value: Int?,
    var service: String?,
    var description: String?,
    var threshold: Int,
    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.MERGE],
    )
    @JoinTable(
        name = "promo_area",
        joinColumns = [JoinColumn(name = "promo_id")],
        inverseJoinColumns = [JoinColumn(name = "area_id")],
    )
    @JsonIgnoreProperties("promos")
    var areas: MutableSet<AreaEntity>,
    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.MERGE],
    )
    @JoinTable(
        name = "promo_user",
        joinColumns = [JoinColumn(name = "promo_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")],
    )
    @JsonIgnoreProperties("locations", "fcmId", "promos")
    var users: MutableSet<UserEntity>,
) {
    fun toDomain(objectMapper: ObjectMapper): Promo {
        val domainAreas = areas.map { it.toDomain(objectMapper) }.toMutableSet()
        val domainUsers = users.map { it.toDomain(objectMapper) }.toMutableSet()
        return Promo(id, code, startDate, endDate, type, value, service, description, threshold, domainAreas, domainUsers)
    }

    companion object {
        fun convert(
            promo: Promo,
            objectMapper: ObjectMapper,
        ): PromoEntity {
            return PromoEntity(
                promo.id,
                promo.code,
                promo.startDate,
                promo.endDate,
                promo.type,
                promo.value,
                promo.service,
                promo.description,
                promo.threshold,
                promo.areas.map { AreaEntity.convert(it, objectMapper) }.toMutableSet(),
                promo.users.map { UserEntity.convert(it, objectMapper) }.toMutableSet(),
            )
        }
    }
}
