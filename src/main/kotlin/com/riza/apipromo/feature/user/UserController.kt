package com.riza.apipromo.feature.user

import com.fasterxml.jackson.databind.ObjectMapper
import com.riza.apipromo.base.BaseResponse
import com.riza.apipromo.core.PointInclusion
import com.riza.apipromo.error.BadRequestException
import com.riza.apipromo.feature.promo.PromoRepository
import com.riza.apipromo.feature.user.models.AddUserRequest
import com.riza.apipromo.feature.user.models.EditFCMRequest
import com.riza.apipromo.feature.user.models.UpdateLocationRequest
import com.riza.apipromo.feature.user.models.UserDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["user","users"])
class UserController @Autowired constructor(
        val objectMapper: ObjectMapper,
        val pointInclusion: PointInclusion,
        val promoRepository: PromoRepository,
        val userRepository: UserRepository
) {

    @PostMapping("add")
    @ResponseBody
    fun addUser(@RequestBody request: AddUserRequest): BaseResponse<UserDTO>{

        val user = userRepository.save(
                UserDTO(request.name)
        )

        return BaseResponse(
                data = user
        )

    }

    @GetMapping("all")
    @ResponseBody
    fun allUser(): BaseResponse<Iterable<UserDTO>>{

        val users = userRepository.findAll()

        return BaseResponse(
                data = users
        )

    }

    @PostMapping("edit-fcm")
    @ResponseBody
    fun editFCM(@RequestBody request: EditFCMRequest): BaseResponse<UserDTO>{

        val user = userRepository.findById(request.idUser)

        val response = BaseResponse<UserDTO>()
        user.ifPresentOrElse({
            it.fcmId = request.fcm
            userRepository.save(it)
            response.data = it
        },{
            throw BadRequestException("User tidak ditemukan")
        })
        return response

    }

    @PostMapping("update-location")
    @ResponseBody
    fun editLocation(@RequestBody request: UpdateLocationRequest): BaseResponse<UserDTO>{

        val response = BaseResponse<UserDTO>()
        val user = userRepository.findById(request.idUser)

        user.ifPresentOrElse({

            val location = objectMapper.writeValueAsString(request.location)

            when(request.day){

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

                else ->{
                    throw BadRequestException("Hari tidak valid")
                }

            }

            userRepository.save(it)
            response.data = it

        },{
            throw BadRequestException("User tidak ditemukan")
        })


        return response

    }





}