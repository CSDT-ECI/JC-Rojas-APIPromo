package com.riza.apipromo.application.adapters.repository.jpa.user

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.riza.apipromo.application.adapters.repository.jpa.promo.PromoEntity
import com.riza.apipromo.domain.user.User
import jakarta.persistence.*

@Entity(name = "user")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: Long? = null,
    var name: String,
    var fcmId: String = "",
    var locations: String,
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "users")
    @JsonIgnore
    var promos: MutableSet<PromoEntity>,
) {
    fun toDomain(objectMapper: ObjectMapper): User {
        return User(id, name, fcmId, objectMapper.readValue(locations), promos.map { it.toDomain(objectMapper) }.toMutableSet())
    }

    companion object {
        fun convert(
            user: User,
            objectMapper: ObjectMapper,
        ): UserEntity {
            return UserEntity(
                user.id,
                user.name,
                user.fcmId,
                objectMapper.writeValueAsString(user.locations),
                user.promos.map { PromoEntity.convert(it, objectMapper) }.toMutableSet(),
            )
        }
    }
}
