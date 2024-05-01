package com.riza.apipromo.application.adapters.controller

import com.riza.apipromo.domain.WeekDay
import com.riza.apipromo.domain.geometry.Point
import com.riza.apipromo.domain.user.User
import com.riza.apipromo.domain.user.UserService
import com.riza.apipromo.v1.UsersApi
import com.riza.apipromo.v1.domain.CreateUserRequest
import com.riza.apipromo.v1.domain.PatchUserRequest
import com.riza.apipromo.v1.domain.UpdateLocationRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
    private val userService: UserService,
) : UsersApi {
    override fun createUser(createUserRequest: CreateUserRequest): ResponseEntity<com.riza.apipromo.v1.domain.User> {
        val user =
            userService.save(
                User(name = createUserRequest.name, promos = mutableSetOf()),
            )
        return ResponseEntity.ok(user.convertToView())
    }

    override fun deleteAllUsers(): ResponseEntity<Unit> {
        return ResponseEntity.ok(userService.deleteAll())
    }

    override fun editLocationByDayAndUserId(
        userId: Long,
        day: com.riza.apipromo.v1.domain.WeekDay,
        updateLocationRequest: UpdateLocationRequest,
    ): ResponseEntity<com.riza.apipromo.v1.domain.User> {
        val user = userService.editLocation(userId, WeekDay.valueOf(day.name), updateLocationRequest.location.map { Point(it.x, it.y) })
        return if (user != null) {
            ResponseEntity.ok(user.convertToView())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    override fun getAllUsers(): ResponseEntity<List<com.riza.apipromo.v1.domain.User>> {
        return ResponseEntity.ok(userService.findAll().map { it.convertToView() })
    }

    override fun getUserById(userId: Long): ResponseEntity<com.riza.apipromo.v1.domain.User> {
        val user = userService.findById(userId)
        return if (user != null) {
            ResponseEntity.ok(user.convertToView())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    override fun patchUser(
        userId: Long,
        patchUserRequest: PatchUserRequest,
    ): ResponseEntity<com.riza.apipromo.v1.domain.User> {
        val user = userService.patchUser(userId, patchUserRequest.fcm)
        return if (user != null) {
            ResponseEntity.ok(user.convertToView())
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
