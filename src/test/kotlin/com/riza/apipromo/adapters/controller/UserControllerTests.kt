package com.riza.apipromo.adapters.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.riza.apipromo.application.adapters.controller.UserController
import com.riza.apipromo.domain.WeekDay
import com.riza.apipromo.domain.geometry.Point
import com.riza.apipromo.domain.user.User
import com.riza.apipromo.domain.user.UserService
import com.riza.apipromo.v1.domain.CreateUserRequest
import com.riza.apipromo.v1.domain.UpdateLocationRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doNothing
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.util.*

@AutoConfigureMockMvc
class UserControllerTests {
    lateinit var mockMvc: MockMvc

    val objectMapper: ObjectMapper = ObjectMapper()

    @Mock
    lateinit var userService: UserService

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        val userController = UserController(userService)
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build()
    }

    @Test
    fun shouldReturn200WhenCreateUserSuccessfully() {
        val userRequest = CreateUserRequest("testUser")

        val expectedUser = User(name = "testUser", promos = mutableSetOf())

        `when`(userService.save(expectedUser)).thenReturn(expectedUser.copy(id = 1))

        mockMvc.post("/users") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(userRequest)
        }.andDo { print() }
            .andExpectAll {
                status { isOk() }
                jsonPath("$.id") { value(1) }
                jsonPath("$.name") { value("testUser") }
            }
    }

    @Test
    fun shouldReturn200WhenDeleteAllUsersSuccessfully() {
        doNothing().`when`(userService).deleteAll()
        mockMvc.delete("/users") {
            contentType = MediaType.APPLICATION_JSON
        }.andDo { print() }
            .andExpectAll {
                status { isOk() }
            }
    }

    @Test
    fun shouldReturn200WhenEditLocationByDayAndUserIdSuccessfully() {
        val userId = 1L
        val updateLocationRequest = UpdateLocationRequest(listOf(com.riza.apipromo.v1.domain.Point(1.0, 2.0)))

        val expectedUser =
            User(
                name = "testUser",
                promos = mutableSetOf(),
                locations =
                    EnumMap(
                        mapOf(
                            WeekDay.MONDAY to listOf(Point(1.0, 2.0)),
                            WeekDay.TUESDAY to listOf(),
                            WeekDay.WEDNESDAY to listOf(),
                            WeekDay.THURSDAY to listOf(),
                            WeekDay.FRIDAY to listOf(),
                            WeekDay.SATURDAY to listOf(),
                            WeekDay.SUNDAY to listOf(),
                        ),
                    ),
            )

        `when`(userService.editLocation(userId, WeekDay.MONDAY, listOf(Point(1.0, 2.0)))).thenReturn(expectedUser)

        mockMvc.put("/users/$userId/locations/MONDAY") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updateLocationRequest)
        }.andDo { print() }
            .andExpectAll {
                status { isOk() }
                jsonPath("$.name") { value("testUser") }
                jsonPath("$.locations.MONDAY.[0].x") { value(1.0) }
                jsonPath("$.locations.MONDAY.[0].y") { value(2.0) }
            }
    }

    @Test
    fun shouldReturn404WhenEditLocationByDayAndUserIdWithUnknownUser() {
        val userId = 1L
        val updateLocationRequest = UpdateLocationRequest(listOf(com.riza.apipromo.v1.domain.Point(1.0, 2.0)))

        `when`(userService.editLocation(userId, WeekDay.MONDAY, listOf(Point(1.0, 2.0)))).thenReturn(null)

        mockMvc.put("/users/$userId/locations/MONDAY") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updateLocationRequest)
        }.andDo { print() }
            .andExpectAll {
                status { isNotFound() }
            }
    }

    @Test
    fun shouldReturn200WhenGetAllUsersSuccessfully() {
        val expectedUsers =
            listOf(
                User(
                    name = "testUser1",
                    promos = mutableSetOf(),
                    locations =
                        EnumMap(
                            mapOf(
                                WeekDay.MONDAY to listOf(Point(1.0, 2.0)),
                                WeekDay.TUESDAY to listOf(),
                                WeekDay.WEDNESDAY to listOf(),
                                WeekDay.THURSDAY to listOf(),
                                WeekDay.FRIDAY to listOf(),
                                WeekDay.SATURDAY to listOf(),
                                WeekDay.SUNDAY to listOf(),
                            ),
                        ),
                ),
                User(
                    name = "testUser2",
                    promos = mutableSetOf(),
                ),
            )

        `when`(userService.findAll()).thenReturn(expectedUsers)

        mockMvc.get("/users") {
            contentType = MediaType.APPLICATION_JSON
        }.andDo { print() }
            .andExpectAll {
                status { isOk() }
                jsonPath("$[0].name") { value("testUser1") }
                jsonPath("$[1].name") { value("testUser2") }
            }
    }

    @Test
    fun shouldReturn200WhenGetUserByIdSuccessfully() {
        val userId = 1L

        val expectedUser =
            User(
                id = userId,
                name = "testUser",
                promos = mutableSetOf(),
            )

        `when`(userService.findById(userId)).thenReturn(expectedUser)

        mockMvc.get("/users/$userId") {
            contentType = MediaType.APPLICATION_JSON
        }.andDo { print() }
            .andExpectAll {
                status { isOk() }
                jsonPath("$.id") { value(userId) }
                jsonPath("$.name") { value("testUser") }
            }
    }

    @Test
    fun shouldReturn404WhenGetUserByIdWithUnknownUser() {
        val userId = 1L

        `when`(userService.findById(userId)).thenReturn(null)

        mockMvc.get("/users/$userId") {
            contentType = MediaType.APPLICATION_JSON
        }.andDo { print() }
            .andExpectAll {
                status { isNotFound() }
            }
    }

    @Test
    fun shouldReturn200WhenPatchUserSuccessfully() {
        val userId = 1L
        val patchUserRequest = com.riza.apipromo.v1.domain.PatchUserRequest("newFcmId")

        val expectedUser =
            User(
                id = userId,
                name = "testUser",
                promos = mutableSetOf(),
                fcmId = "newFcmId",
            )

        `when`(userService.patchUser(userId, "newFcmId")).thenReturn(expectedUser)

        mockMvc.patch("/users/$userId") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(patchUserRequest)
        }.andDo { print() }
            .andExpectAll {
                status { isOk() }
                jsonPath("$.id") { value(userId) }
                jsonPath("$.name") { value("testUser") }
            }
    }

    @Test
    fun shouldReturn404WhenPatchUserWithUnknownUser() {
        val userId = 1L
        val patchUserRequest = com.riza.apipromo.v1.domain.PatchUserRequest("newFcmId")

        `when`(userService.patchUser(userId, "newFcmId")).thenReturn(null)

        mockMvc.patch("/users/$userId") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(patchUserRequest)
        }.andDo { print() }
            .andExpectAll {
                status { isNotFound() }
            }
    }
}
