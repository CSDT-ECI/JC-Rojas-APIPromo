package com.riza.apipromo.domain.user

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

    fun editUserFcmId(
        idUser: Long,
        fcm: String,
    ): User? {
        val user = userRepository.findById(idUser)
        user?.let {
            it.fcmId = fcm
            userRepository.save(user)
        }
        return user
    }

    fun deleteAll() {
        userRepository.deleteAll()
    }

    fun editLocation(
        idUser: Long,
        day: String,
        location: String,
    ): User? {
        val user = userRepository.findById(idUser)
        user?.let {
            user.updateLocation(day, location)
            userRepository.save(user)
        }
        return user
    }
}
