package com.riza.apipromo.application.adapters.controller

import com.riza.apipromo.application.adapters.controller.error.BadRequestException
import com.riza.apipromo.application.adapters.controller.requests.CreateUserRequest
import com.riza.apipromo.application.adapters.controller.requests.PatchUserRequest
import com.riza.apipromo.application.adapters.controller.requests.UpdateLocationRequest
import com.riza.apipromo.application.adapters.controller.responses.BaseResponse
import com.riza.apipromo.domain.WeekDay
import com.riza.apipromo.domain.user.User
import com.riza.apipromo.domain.user.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["users"])
class UserController(
    private val userService: UserService,
) {
    @PostMapping
    @ResponseBody
    fun createUser(
        @RequestBody request: CreateUserRequest,
    ): BaseResponse<User> {
        val user =
            userService.save(
                User(name = request.name, promos = mutableSetOf()),
            )

        return BaseResponse(
            data = user,
        )
    }

    @GetMapping
    @ResponseBody
    fun getAllUsers(): BaseResponse<Iterable<User>> {
        val users = userService.findAll()
        return BaseResponse(
            data = users,
        )
    }

    @PatchMapping("{userId}")
    @ResponseBody
    fun patchUser(
        @PathVariable("userId") userId: Long,
        @RequestBody request: PatchUserRequest,
    ): BaseResponse<User> {
        val user = userService.patchUser(userId, request.fcm)
        val response = BaseResponse<User>()
        if (user != null) {
            response.data = user
        } else {
            throw BadRequestException("User tidak ditemukan")
        }
        return response
    }

    @GetMapping("{userId}")
    @ResponseBody
    fun getUserById(
        @PathVariable("userId") userId: Long,
    ): BaseResponse<User> {
        val res = BaseResponse<User>()
        val user = userService.findById(userId)
        if (user != null) {
            res.data = user
        } else {
            throw BadRequestException("User tidak ditemukan")
        }
        return res
    }

    @PutMapping("{userId}/locations/{day}")
    @ResponseBody
    fun editLocationByDayAndUserId(
        @PathVariable("userId") userId: Long,
        @PathVariable("day") day: WeekDay,
        @RequestBody request: UpdateLocationRequest,
    ): BaseResponse<User> {
        val response = BaseResponse<User>()
        val user = userService.editLocation(userId, day, request.location)

        if (user != null) {
            response.data = user
        } else {
            throw BadRequestException("Hari tidak valid")
        }
        return response
    }

    @DeleteMapping
    @ResponseBody
    fun deleteAllUsers(): BaseResponse<Boolean> {
        userService.deleteAll()
        return BaseResponse(data = true)
    }
}
