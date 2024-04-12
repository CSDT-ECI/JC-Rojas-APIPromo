package com.riza.apipromo.feature.user.models

import com.riza.apipromo.core.Point

data class AddUserRequest(
    val name: String = "",
)

data class EditFCMRequest(
    val idUser: Long = 0L,
    val fcm: String = "",
)

data class UpdateLocationRequest(
    val idUser: Long = 0L,
    val day: String = "",
    val location: List<Point> = emptyList(),
)

data class UserBulkRequest(
    val users: List<AddUserRequest> = emptyList(),
    val centroid: Point = Point(),
    val radius: Double = 0.0,
)
