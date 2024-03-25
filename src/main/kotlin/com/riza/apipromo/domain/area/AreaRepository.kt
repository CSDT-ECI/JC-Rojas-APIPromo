package com.riza.apipromo.domain.area

interface AreaRepository {
    fun save(area: Area): Area

    fun findById(areaId: Long): Area?

    fun findAll(): List<Area>

    fun findAllById(areaIds: List<Long>): List<Area>

    fun deleteById(areaId: Long)
}
