package com.riza.apipromo.feature.promo

import com.riza.apipromo.feature.area.AreaDTO
import com.riza.apipromo.feature.area.AreaRepository
import com.riza.apipromo.base.BaseResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.text.SimpleDateFormat

@Controller
@RequestMapping(path = ["promo"])
class PromoController @Autowired constructor(
        private val areaRepository: AreaRepository,
        private val promoRepository: PromoRepository
) {

    @PostMapping("add")
    @ResponseBody
    fun addPromo(
        @RequestBody promoRequest: AddPromoRequest
    ): BaseResponse<PromoDTO>{

        if(promoRequest.areaIds.isEmpty()){
            //todo exception
        }

        val areas: Iterable<AreaDTO> = areaRepository.findAllById(promoRequest.areaIds)

        val promo = PromoDTO(
                0L,
                promoRequest.code,
                parseDate(promoRequest.startDate),
                parseDate(promoRequest.endDate),
                promoRequest.type,
                promoRequest.value,
                promoRequest.service,
                areas.toHashSet()
        )

        val result = promoRepository.save(promo)

        return BaseResponse(
                data = result
        )

    }


    @GetMapping("all")
    @ResponseBody
    fun getAllPromo(): BaseResponse<Iterable<PromoDTO>>{

        val result = promoRepository.findAll()
        return BaseResponse(data = result)

    }

    private fun parseDate(strDate: String) = SimpleDateFormat("dd-MM-yyyy").parse(strDate)



}