package com.riza.apipromo.domain.user

interface UserRepository {
    fun save(user: User): User

    fun findAll(): List<User>

    fun findById(userId: Long): User?

    fun deleteAll()
}
