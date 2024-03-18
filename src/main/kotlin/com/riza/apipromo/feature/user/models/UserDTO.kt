package com.riza.apipromo.feature.user.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.riza.apipromo.feature.promo.models.PromoDTO
import jakarta.persistence.*

@Entity(name = "user")
data class UserDTO(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: Long? = null,
    var name: String,
    var fcmId: String = "",
    @Embedded
    var locations: UserLocation = UserLocation(),
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "users")
    @JsonIgnore
    var promos: MutableSet<PromoDTO>? = null
)
