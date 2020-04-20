package com.riza.apipromo.feature.area

import com.riza.apipromo.feature.area.models.AreaDTO
import org.springframework.data.repository.CrudRepository

interface AreaRepository : CrudRepository<AreaDTO, Long> {

    override fun findAll(): MutableIterable<AreaDTO>



}