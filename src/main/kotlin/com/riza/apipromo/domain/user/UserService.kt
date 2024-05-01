package com.riza.apipromo.domain.user

import com.riza.apipromo.domain.WeekDay
import com.riza.apipromo.domain.geometry.Point

class UserService(
    private val userRepository: UserRepository,
) {
    fun save(user: User): User {
        return userRepository.save(user)
    }

    fun findAll(): List<User> {
        return userRepository.findAll()
    }

    fun findById(userId: Long): User? {
        return userRepository.findById(userId)
    }

    fun patchUser(
        idUser: Long,
        fcm: String,
    ): User? {
        val user = userRepository.findById(idUser)
        user?.let {
            user.patch(fcm)
            userRepository.save(user)
        }
        return user
    }

    fun deleteAll() {
        userRepository.deleteAll()
    }

    fun editLocation(
        idUser: Long,
        day: WeekDay,
        location: List<Point>,
    ): User? {
        val user = userRepository.findById(idUser)
        user?.let {
            user.updateLocation(day, location)
            userRepository.save(user)
        }
        return user
    }
}
