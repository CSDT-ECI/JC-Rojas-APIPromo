package com.riza.apipromo.feature.user

import com.riza.apipromo.feature.user.models.UserDTO
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<UserDTO, Long> {

}