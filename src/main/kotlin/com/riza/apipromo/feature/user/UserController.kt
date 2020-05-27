package com.riza.apipromo.feature.user

import com.fasterxml.jackson.databind.ObjectMapper
import com.riza.apipromo.base.BaseResponse
import com.riza.apipromo.core.Point
import com.riza.apipromo.core.PointInclusion
import com.riza.apipromo.error.BadRequestException
import com.riza.apipromo.feature.promo.PromoRepository
import com.riza.apipromo.feature.user.models.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import riza.com.cto.core.PolygonUtils

@RestController
@RequestMapping(path = ["user", "users"])
class UserController @Autowired constructor(
        val objectMapper: ObjectMapper,
        val pointInclusion: PointInclusion,
        val promoRepository: PromoRepository,
        val userRepository: UserRepository
) {

    @PostMapping("add")
    @ResponseBody
    fun addUser(@RequestBody request: AddUserRequest): BaseResponse<UserDTO> {

        val user = userRepository.save(
                UserDTO(request.name)
        )

        return BaseResponse(
                data = user
        )

    }

    @GetMapping("all")
    @ResponseBody
    fun allUser(): BaseResponse<Iterable<UserDTO>> {

        val users = userRepository.findAll()

        return BaseResponse(
                data = users
        )

    }

    @PostMapping("edit-fcm")
    @ResponseBody
    fun editFCM(@RequestBody request: EditFCMRequest): BaseResponse<UserDTO> {

        val user = userRepository.findById(request.idUser)

        val response = BaseResponse<UserDTO>()
        user.ifPresentOrElse({
            it.fcmId = request.fcm
            userRepository.save(it)
            response.data = it
        }, {
            throw BadRequestException("User tidak ditemukan")
        })
        return response

    }

    @GetMapping("{id}")
    @ResponseBody
    fun getUser(
            @PathVariable("id") id: Long
    ): BaseResponse<UserDTO>{

        val res = BaseResponse<UserDTO>()
        userRepository.findById(id).ifPresentOrElse(
                {
                    res.data = it
                },
                {
                    throw BadRequestException("id tidak ditemukan")
                }
        )
        return res
    }

    @PostMapping("update-location")
    @ResponseBody
    fun editLocation(@RequestBody request: UpdateLocationRequest): BaseResponse<UserDTO> {

        val response = BaseResponse<UserDTO>()
        val user = userRepository.findById(request.idUser)

        user.ifPresentOrElse({

            val location = objectMapper.writeValueAsString(request.location)

            when (request.day) {

                "monday" -> {
                    it.locations.monday = location
                }
                "tuesday" -> {
                    it.locations.tuesday = location
                }
                "wednesday" -> {
                    it.locations.wednesday = location
                }
                "thursday" -> {
                    it.locations.thursday = location
                }
                "friday" -> {
                    it.locations.friday = location
                }
                "saturday" -> {
                    it.locations.saturday = location
                }
                "sunday" -> {
                    it.locations.sunday = location
                }

                else -> {
                    throw BadRequestException("Hari tidak valid")
                }

            }

            userRepository.save(it)
            response.data = it

        }, {
            throw BadRequestException("User tidak ditemukan")
        })


        return response

    }

    @PostMapping("bulk-user-create")
    @ResponseBody
    fun bulkTestUser(
            @RequestBody request: UserBulkRequest
    ): BaseResponse<String> {

        request.users.forEach {

            val mUser = UserDTO(it.name)

            mUser.locations.apply {

                monday = getRandomLocation4Day(request.centroid, request.radius)
                tuesday = getRandomLocation4Day(request.centroid, request.radius)
                wednesday = getRandomLocation4Day(request.centroid, request.radius)
                thursday = getRandomLocation4Day(request.centroid, request.radius)
                friday = getRandomLocation4Day(request.centroid, request.radius)
                saturday = getRandomLocation4Day(request.centroid, request.radius)
                sunday = getRandomLocation4Day(request.centroid, request.radius)
            }

            userRepository.save(mUser)

        }

        return BaseResponse(data = "${request.users.size} Created!")

    }

    private fun getRandomLocation4Day(centroid: Point, radius: Double) = objectMapper.writeValueAsString(
            PolygonUtils.generateWalkPoints(
                    centroid,
                    radius,
                    24
            ))


    @PostMapping("bulk-user-delete")
    @ResponseBody
    fun bulkDelete(): BaseResponse<Boolean>{
        userRepository.deleteAll()
        return BaseResponse(data = true)
    }

}