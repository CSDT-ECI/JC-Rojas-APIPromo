package com.riza.apipromo.application.adapters.controller.requests

import com.riza.apipromo.domain.geometry.Point

data class CreateUserRequest(
    val name: String = "",
)

data class PatchUserRequest(
    val fcm: String = "",
)

data class UpdateLocationRequest(
    val location: List<Point> = emptyList(),
)
