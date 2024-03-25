package com.riza.apipromo.application.adapters.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.riza.apipromo.base.BaseResponse
import com.riza.apipromo.core.Point
import com.riza.apipromo.domain.user.User
import com.riza.apipromo.domain.user.UserService
import com.riza.apipromo.error.BadRequestException
import com.riza.apipromo.feature.user.models.*
import org.springframework.web.bind.annotation.*
import riza.com.cto.core.PolygonUtils

@RestController
@RequestMapping(path = ["user", "users"])
class UserController(
    private val userService: UserService,
    private val objectMapper: ObjectMapper,
) {
    @PostMapping("add")
    @ResponseBody
    fun addUser(
        @RequestBody request: AddUserRequest,
    ): BaseResponse<User> {
        val user =
            userService.save(
                User(name = request.name, promos = mutableSetOf()),
            )

        return BaseResponse(
            data = user,
        )
    }

    @GetMapping("all")
    @ResponseBody
    fun allUser(): BaseResponse<Iterable<User>> {
        val users = userService.findAll()
        return BaseResponse(
            data = users,
        )
    }

    @PostMapping("edit-fcm")
    @ResponseBody
    fun editFCM(
        @RequestBody request: EditFCMRequest,
    ): BaseResponse<User> {
        val user = userService.editUserFcmId(request.idUser, request.fcm)
        val response = BaseResponse<User>()
        if (user != null) {
            response.data = user
        } else {
            throw BadRequestException("User tidak ditemukan")
        }
        return response
    }

    @GetMapping("{id}")
    @ResponseBody
    fun getUser(
        @PathVariable("id") id: Long,
    ): BaseResponse<User> {
        val res = BaseResponse<User>()
        val user = userService.findById(id)
        if (user != null) {
            res.data = user
        } else {
            throw BadRequestException("User tidak ditemukan")
        }
        return res
    }

    @PostMapping("update-location")
    @ResponseBody
    fun editLocation(
        @RequestBody request: UpdateLocationRequest,
    ): BaseResponse<User> {
        val response = BaseResponse<User>()
        val location = objectMapper.writeValueAsString(request.location)
        val user = userService.editLocation(request.idUser, request.day, location)

        if (user != null) {
            response.data = user
        } else {
            throw BadRequestException("Hari tidak valid")
        }
        return response
    }

    @PostMapping("bulk-user-create")
    @ResponseBody
    fun bulkTestUser(
        @RequestBody request: UserBulkRequest,
    ): BaseResponse<String> {
        request.users.forEach {
            val mUser = User(name = it.name, promos = mutableSetOf())

            val center =
                PolygonUtils.generateRandomPointFrom(
                    request.centroid,
                    PolygonUtils.meterToDegree(1000.0),
                )

            mUser.locations.apply {
                monday = getRandomLocation4Day(center, request.radius)
                tuesday = getRandomLocation4Day(center, request.radius)
                wednesday = getRandomLocation4Day(center, request.radius)
                thursday = getRandomLocation4Day(center, request.radius)
                friday = getRandomLocation4Day(center, request.radius)
                saturday = getRandomLocation4Day(center, request.radius)
                sunday = getRandomLocation4Day(center, request.radius)
            }

            userService.save(mUser)
        }

        return BaseResponse(data = "${request.users.size} Created!")
    }

    private fun getRandomLocation4Day(
        centroid: Point,
        radius: Double,
    ) = objectMapper.writeValueAsString(
        PolygonUtils.generateWalkPoints(
            centroid,
            radius,
            24,
        ),
    )

    @PostMapping("bulk-user-delete")
    @ResponseBody
    fun bulkDelete(): BaseResponse<Boolean> {
        userService.deleteAll()
        return BaseResponse(data = true)
    }
}
