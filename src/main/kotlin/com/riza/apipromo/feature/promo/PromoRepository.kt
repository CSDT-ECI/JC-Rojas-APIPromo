package com.riza.apipromo.feature.promo

import com.riza.apipromo.feature.promo.models.PromoDTO
import org.springframework.data.repository.CrudRepository

interface PromoRepository : CrudRepository<PromoDTO, Long>{

}