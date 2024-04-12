package com.riza.apipromo.feature.promo.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.riza.apipromo.feature.area.models.AreaDTO
import com.riza.apipromo.feature.user.models.UserDTO
import jakarta.persistence.*
import java.util.Date

@Entity(name = "promo")
data class PromoDTO(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    var code: String,
    @Temporal(TemporalType.DATE)
    var startDate: Date,
    @Temporal(TemporalType.DATE)
    var endDate: Date?,
    @Enumerated(EnumType.STRING)
    var type: PromoType?,
    var value: Int?,
    var service: String?,
    var description: String?,
    var threshold: Int?,
    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
    )
    @JoinTable(
        name = "promo_area",
        joinColumns = [JoinColumn(name = "promo_id")],
        inverseJoinColumns = [JoinColumn(name = "area_id")],
    )
    @JsonIgnoreProperties("promos")
    var areas: MutableSet<AreaDTO>?,
    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
    )
    @JoinTable(
        name = "promo_user",
        joinColumns = [JoinColumn(name = "promo_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")],
    )
    @JsonIgnoreProperties("locations", "fcmId", "promos")
    var users: MutableSet<UserDTO>?,
)
