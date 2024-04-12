package com.riza.apipromo.application.adapters.repository.jpa.area

import com.fasterxml.jackson.databind.ObjectMapper
import com.riza.apipromo.domain.area.Area
import com.riza.apipromo.domain.area.AreaRepository
import kotlin.jvm.optionals.getOrNull

class AreaJPAAdapter(
    private val areaJpaRepository: AreaJPARepository,
    private val objectMapper: ObjectMapper,
) : AreaRepository {
    override fun save(area: Area): Area {
        return areaJpaRepository.save(AreaEntity.convert(area, objectMapper)).toDomain(objectMapper)
    }

    override fun findById(areaId: Long): Area? {
        return areaJpaRepository.findById(areaId).getOrNull()?.toDomain(objectMapper)
    }

    override fun findAll(): List<Area> {
        return areaJpaRepository.findAll().map { it.toDomain(objectMapper) }
    }

    override fun findAllById(areaIds: List<Long>): List<Area> {
        return areaJpaRepository.findAllById(areaIds).map { it.toDomain(objectMapper) }
    }

    override fun deleteById(areaId: Long) {
        areaJpaRepository.deleteById(areaId)
    }
}
