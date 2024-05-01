package com.riza.apipromo.adapters.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.riza.apipromo.application.adapters.controller.AreaController
import com.riza.apipromo.domain.area.Area
import com.riza.apipromo.domain.area.AreaService
import com.riza.apipromo.domain.geometry.Point
import com.riza.apipromo.domain.geometry.PointInclusionMethod
import com.riza.apipromo.domain.geometry.Polygon
import com.riza.apipromo.v1.domain.AreaRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doNothing
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@AutoConfigureMockMvc
class AreaControllerTests {
    lateinit var mockMvc: MockMvc

    val objectMapper: ObjectMapper = ObjectMapper()

    @Mock
    lateinit var areaService: AreaService

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        val areaController = AreaController(areaService)
        this.mockMvc = MockMvcBuilders.standaloneSetup(areaController).build()
    }

    @Test
    fun shouldReturn200WhenGetAllAreas() {
        val storedAreas =
            listOf(
                Area(1, Polygon("test1", arrayListOf(Point(1.0, 2.0))), mutableSetOf()),
                Area(2, Polygon("test2", arrayListOf(Point(1.0, 2.0))), mutableSetOf()),
            )
        `when`(areaService.findAll()).thenReturn(storedAreas)

        mockMvc.get("/areas")
            .andDo { print() }
            .andExpectAll {
                status { isOk() }
                jsonPath("$.[0].id") { value(1) }
                jsonPath("$.[1].id") { value(2) }
            }
    }

    @Test
    fun shouldReturn200WhenCreateAreaSuccessfully() {
        val areaRequest = AreaRequest("test", listOf(com.riza.apipromo.v1.domain.Point(1.0, 2.0)))

        val expectedArea = Area(polygon = Polygon("test", arrayListOf(Point(1.0, 2.0))), promos = mutableSetOf())

        `when`(areaService.save(expectedArea)).thenReturn(expectedArea.copy(id = 1))

        mockMvc.post("/areas") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(areaRequest)
        }.andDo { print() }
            .andExpectAll {
                status { isOk() }
                jsonPath("$.id") { value(1) }
                jsonPath("$.polygon.name") { value("test") }
            }
    }

    @Test
    fun shouldReturn404WhenAreaNotFoundInCheckPointInArea() {
        val areaId = 1L
        val method = "CN"
        val checkPointRequest = com.riza.apipromo.v1.domain.CheckPointRequest(com.riza.apipromo.v1.domain.Point(1.0, 2.0))

        `when`(areaService.checkPointInArea(areaId, Point(1.0, 2.0), PointInclusionMethod.CN))
            .thenReturn(null)

        mockMvc.post("/areas/$areaId/check/$method") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(checkPointRequest)
        }.andDo { print() }
            .andExpectAll {
                status { isNotFound() }
            }
    }

    @Test
    fun shouldReturn404WhenAreaNotFoundInCheckAllPointsInArea() {
        val areaId = 1L
        val method = "CN"
        val checkManyPointRequest =
            com.riza.apipromo.v1.domain.CheckManyPointRequest(
                listOf(
                    com.riza.apipromo.v1.domain.Point(1.0, 2.0),
                    com.riza.apipromo.v1.domain.Point(2.0, 3.0),
                ),
            )

        `when`(areaService.checkAllPointsInArea(areaId, listOf(Point(1.0, 2.0), Point(2.0, 3.0)), PointInclusionMethod.CN))
            .thenReturn(null)

        mockMvc.post("/areas/$areaId/checkall/$method") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(checkManyPointRequest)
        }.andDo { print() }
            .andExpectAll {
                status { isNotFound() }
            }
    }

    @Test
    fun shouldReturn200WhenAreaFoundInCheckPointInArea() {
        val areaId = 1L
        val method = "CN"
        val checkPointRequest = com.riza.apipromo.v1.domain.CheckPointRequest(com.riza.apipromo.v1.domain.Point(1.0, 2.0))

        `when`(areaService.checkPointInArea(areaId, Point(1.0, 2.0), PointInclusionMethod.CN))
            .thenReturn(true)

        mockMvc.post("/areas/$areaId/check/$method") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(checkPointRequest)
        }.andDo { print() }
            .andExpectAll {
                status { isOk() }
                jsonPath("$") { value("true") }
            }
    }

    @Test
    fun shouldReturn200WhenAreaFoundInCheckAllPointsInArea() {
        val areaId = 1L
        val method = "CN"
        val checkManyPointRequest =
            com.riza.apipromo.v1.domain.CheckManyPointRequest(
                listOf(
                    com.riza.apipromo.v1.domain.Point(1.0, 2.0),
                    com.riza.apipromo.v1.domain.Point(2.0, 3.0),
                ),
            )

        `when`(areaService.checkAllPointsInArea(areaId, listOf(Point(1.0, 2.0), Point(2.0, 3.0)), PointInclusionMethod.CN))
            .thenReturn(listOf(true, false))

        mockMvc.post("/areas/$areaId/checkall/$method") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(checkManyPointRequest)
        }.andDo { print() }
            .andExpectAll {
                status { isOk() }
                jsonPath("$.[0]") { value("true") }
                jsonPath("$.[1]") { value("false") }
            }
    }

    @Test
    fun shouldReturn200WhenDeleteAreaSuccessfully() {
        val areaId = 1L
        doNothing().`when`(areaService).deleteById(areaId)

        mockMvc.delete("/areas/$areaId")
            .andDo { print() }
            .andExpectAll {
                status { isOk() }
            }
    }
}
