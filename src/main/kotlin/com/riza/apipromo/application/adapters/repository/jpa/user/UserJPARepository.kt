package com.riza.apipromo.application.adapters.repository.jpa.user

import org.springframework.data.jpa.repository.JpaRepository

interface UserJPARepository : JpaRepository<UserEntity, Long>
