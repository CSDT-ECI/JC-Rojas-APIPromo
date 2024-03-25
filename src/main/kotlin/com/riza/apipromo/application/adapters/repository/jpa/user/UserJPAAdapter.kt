package com.riza.apipromo.application.adapters.repository.jpa.user

import com.fasterxml.jackson.databind.ObjectMapper
import com.riza.apipromo.domain.user.User
import com.riza.apipromo.domain.user.UserRepository
import kotlin.jvm.optionals.getOrNull

class UserJPAAdapter(
    private val userRepository: UserJPARepository,
    private val objectMapper: ObjectMapper,
) : UserRepository {
    override fun save(user: User): User {
        return userRepository.save(UserEntity.convert(user, objectMapper)).toDomain(objectMapper)
    }

    override fun findAll(): List<User> {
        return userRepository.findAll().map { it.toDomain(objectMapper) }
    }

    override fun findById(userId: Long): User? {
        return userRepository.findById(userId).getOrNull()?.toDomain(objectMapper)
    }

    override fun deleteAll() {
        userRepository.deleteAll()
    }
}
