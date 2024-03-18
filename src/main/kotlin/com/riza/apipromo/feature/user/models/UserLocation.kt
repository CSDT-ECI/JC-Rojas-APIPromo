package com.riza.apipromo.feature.user.models

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class UserLocation(
    @Column(columnDefinition = "text")
    var monday: String = "[]",
    @Column(columnDefinition = "text")
    var tuesday: String = "[]",
    @Column(columnDefinition = "text")
    var wednesday: String = "[]",
    @Column(columnDefinition = "text")
    var thursday: String = "[]",
    @Column(columnDefinition = "text")
    var friday: String = "[]",
    @Column(columnDefinition = "text")
    var saturday: String = "[]",
    @Column(columnDefinition = "text")
    var sunday: String = "[]"
)
