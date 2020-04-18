package com.riza.apipromo.feature.promo

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

interface PromoRepository : CrudRepository<PromoDTO, Long>{

}