package com.riza.apipromo.domain

import com.riza.apipromo.application.adapters.controller.requests.PromoType
import com.riza.apipromo.domain.area.Area
import com.riza.apipromo.domain.area.AreaRepository
import com.riza.apipromo.domain.geometry.*
import com.riza.apipromo.domain.promo.Promo
import com.riza.apipromo.domain.promo.PromoRepository
import com.riza.apipromo.domain.promo.PromoService
import com.riza.apipromo.domain.user.User
import com.riza.apipromo.domain.user.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.time.Clock
import java.time.Instant
import java.util.*

@ExtendWith(MockitoExtension::class)
class PromoServiceTests {
    private val clock: Clock = Clock.fixed(Instant.parse("2024-01-01T00:00:00Z"), Clock.systemUTC().zone)

    @Mock
    lateinit var userRepository: UserRepository

    @Mock
    lateinit var promoRepository: PromoRepository

    @Mock
    lateinit var areaRepository: AreaRepository

    private lateinit var promoService: PromoService

    @BeforeEach
    fun setupService() {
        promoService =
            PromoService(
                promoRepository,
                areaRepository,
                userRepository,
                mapOf(
                    PointInclusionMethod.CN to CrossingNumberAlgorithm(),
                    PointInclusionMethod.WN to WindingNumberAlgorithm(),
                ),
            )
    }

    @Test
    fun findAllShouldCallRepositoryAndReturnAListWithAllPromos() {
        // given
        val expectedPromo1 = promoMockValue()
        val expectedPromo2 = promoMockValue().copy(id = 2, code = "Test Promo 2")
        val expectedPromos = listOf(expectedPromo1, expectedPromo2)
        // when
        Mockito.`when`(promoRepository.findAll()).thenReturn(expectedPromos)
        val promos = promoService.findAll()
        // then
        Assertions.assertEquals(promos, expectedPromos)
    }

    @Test
    fun addPromoShouldCallRepositoryAndReturnSavedPromoWithAllUsersInTheRequestedAreas() {
        // given
        val promoRequest = promoMockValue().copy(areas = mutableSetOf(), users = mutableSetOf())
        val requestedAreaIds = listOf(1L, 2L)
        val expectedPromo = promoMockValue()
        // when
        Mockito.`when`(areaRepository.findAllById(requestedAreaIds)).thenReturn(areaMockValues())
        Mockito.`when`(userRepository.findAll()).thenReturn(mockedUsers())
        Mockito.`when`(promoRepository.save(expectedPromo)).thenReturn(expectedPromo)
        val promo = promoService.addPromo(promoRequest, requestedAreaIds, PointInclusionMethod.CN)
        // then
        Assertions.assertEquals(promo, expectedPromo)
    }

    @Test
    fun addPromoShouldCallRepositoryAndReturnSavedPromoWithoutUsersIfNoUsersAreInTheRequestedAreas() {
        // given
        val promoRequest = promoMockValue().copy(areas = mutableSetOf(), users = mutableSetOf(), threshold = 10)
        val requestedAreaIds = listOf(1L, 2L)
        val expectedPromo = promoMockValue().copy(users = mutableSetOf(), threshold = 10)
        // when
        Mockito.`when`(areaRepository.findAllById(requestedAreaIds)).thenReturn(areaMockValues())
        Mockito.`when`(userRepository.findAll()).thenReturn(mockedUsers())
        Mockito.`when`(promoRepository.save(expectedPromo)).thenReturn(expectedPromo)
        val promo = promoService.addPromo(promoRequest, requestedAreaIds, PointInclusionMethod.CN)
        // then
        Assertions.assertEquals(promo, expectedPromo)
    }

    private fun areaMockValues(): List<Area> {
        return listOf(
            Area(
                id = 1,
                promos = mutableSetOf(),
                polygon =
                    Polygon(
                        name = "Test Area 1",
                        points =
                            arrayListOf(
                                Point(1.0, 1.0),
                                Point(1.0, 2.0),
                                Point(2.0, 2.0),
                                Point(2.0, 1.0),
                            ),
                    ),
            ),
            Area(
                id = 2,
                promos = mutableSetOf(),
                polygon =
                    Polygon(
                        name = "Test Area 2",
                        points =
                            arrayListOf(
                                Point(2.0, 2.0),
                                Point(2.0, 3.0),
                                Point(3.0, 3.0),
                                Point(3.0, 2.0),
                            ),
                    ),
            ),
        )
    }

    private fun promoMockValue(): Promo {
        return Promo(
            id = 1,
            code = "Test Promo 1",
            areas = areaMockValues().toMutableSet(),
            users = mockedUsers().toMutableSet(),
            service = "Test Service 1",
            threshold = 1,
            value = 1,
            type = PromoType.PRICE,
            description = "Test Description 1",
            startDate = Date.from(Instant.now(clock)),
            endDate = Date.from(Instant.now(clock).plusSeconds(60)),
        )
    }

    private fun mockedUsers(): List<User> {
        return listOf(
            User(
                id = 1,
                name = "Test User 1",
                promos = mutableSetOf(),
                fcmId = "Test FCM ID 1",
                locations =
                    EnumMap(
                        mapOf(
                            WeekDay.MONDAY to listOf(Point(1.0, 1.0)),
                            WeekDay.TUESDAY to listOf(Point(2.0, 2.0)),
                            WeekDay.WEDNESDAY to listOf(Point(3.0, 3.0)),
                            WeekDay.THURSDAY to listOf(Point(4.0, 4.0)),
                            WeekDay.FRIDAY to listOf(Point(5.0, 5.0)),
                            WeekDay.SATURDAY to listOf(Point(6.0, 6.0)),
                            WeekDay.SUNDAY to listOf(Point(7.0, 7.0)),
                        ),
                    ),
            ),
            User(
                id = 2,
                name = "Test User 2",
                promos = mutableSetOf(),
                fcmId = "Test FCM ID 2",
                locations =
                    EnumMap(
                        mapOf(
                            WeekDay.MONDAY to listOf(Point(1.0, 1.0)),
                            WeekDay.TUESDAY to listOf(Point(2.0, 2.0)),
                            WeekDay.WEDNESDAY to listOf(Point(3.0, 3.0)),
                            WeekDay.THURSDAY to listOf(Point(4.0, 4.0)),
                            WeekDay.FRIDAY to listOf(Point(5.0, 5.0)),
                            WeekDay.SATURDAY to listOf(Point(6.0, 6.0)),
                            WeekDay.SUNDAY to listOf(Point(7.0, 7.0)),
                        ),
                    ),
            ),
        )
    }
}
