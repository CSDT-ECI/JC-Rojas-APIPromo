package com.riza.apipromo.adapters.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.riza.apipromo.application.adapters.controller.PromoController
import com.riza.apipromo.domain.area.Area
import com.riza.apipromo.domain.geometry.Point
import com.riza.apipromo.domain.geometry.PointInclusionMethod
import com.riza.apipromo.domain.geometry.Polygon
import com.riza.apipromo.domain.promo.Promo
import com.riza.apipromo.domain.promo.PromoService
import com.riza.apipromo.domain.promo.PromoType
import com.riza.apipromo.domain.user.User
import com.riza.apipromo.v1.domain.AddPromoRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset

@SpringBootTest
@AutoConfigureMockMvc
class PromoControllerTests {
    @Autowired
    lateinit var mockMvc: MockMvc

    val objectMapper: ObjectMapper = ObjectMapper().registerModule(JavaTimeModule())

    @Mock
    lateinit var promoService: PromoService

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        val promoController = PromoController(promoService)
        this.mockMvc = MockMvcBuilders.standaloneSetup(promoController).build()
    }

    @Test
    fun shouldReturn200WhenCreatePromoSuccessfully() {
        val addPromoRequest =
            AddPromoRequest(
                code = "PROMO2023",
                startDate = OffsetDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC),
                type = AddPromoRequest.Type.PERCENT,
                service = "Service1",
                description = "This is a test promo",
                areaIds = listOf(1),
                threshold = 10,
                endDate = OffsetDateTime.of(2023, 1, 2, 0, 0, 0, 0, ZoneOffset.UTC),
                value = 20,
            )
        val expectedUser = User(id = 1, name = "testUser", promos = mutableSetOf())
        val expectedArea =
            Area(
                id = 1,
                polygon = Polygon("test1", arrayListOf(Point(1.0, 1.0), Point(2.0, 2.0), Point(3.0, 3.0))),
                promos = mutableSetOf(),
            )

        val expectedPromo =
            Promo(
                code = "PROMO2023",
                startDate = LocalDateTime.of(2023, 1, 1, 0, 0, 0, 0),
                type = PromoType.PERCENT,
                service = "Service1",
                description = "This is a test promo",
                areas = mutableSetOf(),
                users = mutableSetOf(),
                threshold = 10,
                endDate = LocalDateTime.of(2023, 1, 2, 0, 0, 0, 0),
                value = 20,
            )

        `when`(promoService.addPromo(expectedPromo, listOf(1), PointInclusionMethod.CN))
            .thenReturn(expectedPromo.copy(id = 1, areas = mutableSetOf(expectedArea), users = mutableSetOf(expectedUser)))

        mockMvc.post("/promos") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(addPromoRequest)
        }.andDo { print() }
            .andExpectAll {
                status { isOk() }
                jsonPath("$.id") { value(1) }
                jsonPath("$.code") { value("PROMO2023") }
            }
    }

    @Test
    fun shouldReturn200WhenFindAllPromosSuccessfully() {
        val expectedPromo1 =
            Promo(
                id = 1,
                code = "PROMO2023",
                startDate = LocalDateTime.of(2023, 1, 1, 0, 0, 0, 0),
                type = PromoType.PERCENT,
                service = "Service1",
                description = "This is a test promo",
                areas = mutableSetOf(),
                users = mutableSetOf(),
                threshold = 10,
                endDate = LocalDateTime.of(2023, 1, 2, 0, 0, 0, 0),
                value = 20,
            )
        val expectedPromo2 =
            Promo(
                id = 2,
                code = "PROMO2024",
                startDate = LocalDateTime.of(2024, 1, 1, 0, 0, 0, 0),
                type = PromoType.PRICE,
                service = "Service2",
                description = "This is another test promo",
                areas = mutableSetOf(),
                users = mutableSetOf(),
                threshold = 20,
                endDate = LocalDateTime.of(2024, 1, 2, 0, 0, 0, 0),
                value = 30,
            )

        val expectedPromos = listOf(expectedPromo1, expectedPromo2)

        `when`(promoService.findAll()).thenReturn(expectedPromos)

        mockMvc.get("/promos") {
            contentType = MediaType.APPLICATION_JSON
        }.andDo { print() }
            .andExpectAll {
                status { isOk() }
                jsonPath("$[0].id") { value(1) }
                jsonPath("$[0].code") { value("PROMO2023") }
                jsonPath("$[1].id") { value(2) }
                jsonPath("$[1].code") { value("PROMO2024") }
            }
    }

    @Test
    fun shouldReturn400WhenAddPromoToUnknownArea() {
        val addPromoRequest =
            AddPromoRequest(
                code = "PROMO2023",
                startDate = OffsetDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC),
                type = AddPromoRequest.Type.PERCENT,
                service = "Service1",
                description = "This is a test promo",
                areaIds = listOf(1),
                threshold = 10,
                endDate = OffsetDateTime.of(2023, 1, 2, 0, 0, 0, 0, ZoneOffset.UTC),
                value = 20,
            )

        val expectedPromo =
            Promo(
                code = "PROMO2023",
                startDate = LocalDateTime.of(2023, 1, 1, 0, 0, 0, 0),
                type = PromoType.PERCENT,
                service = "Service1",
                description = "This is a test promo",
                areas = mutableSetOf(),
                users = mutableSetOf(),
                threshold = 10,
                endDate = LocalDateTime.of(2023, 1, 2, 0, 0, 0, 0),
                value = 20,
            )

        `when`(promoService.addPromo(expectedPromo, listOf(1), PointInclusionMethod.CN)).thenReturn(null)

        mockMvc.post("/promos") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(addPromoRequest)
        }.andDo { print() }
            .andExpectAll {
                status { isBadRequest() }
            }
    }
}
