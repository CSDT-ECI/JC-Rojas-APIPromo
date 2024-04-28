package com.riza.apipromo.domain

import com.riza.apipromo.domain.area.Area
import com.riza.apipromo.domain.area.AreaRepository
import com.riza.apipromo.domain.area.AreaService
import com.riza.apipromo.domain.geometry.Point
import com.riza.apipromo.domain.geometry.PointInclusionAlgorithm
import com.riza.apipromo.domain.geometry.PointInclusionMethod
import com.riza.apipromo.domain.geometry.Polygon
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@ExtendWith(
    MockitoExtension::class,
)
class AreaServiceTests {
    @Mock
    lateinit var areaRepository: AreaRepository

    @Mock
    lateinit var windingNumberAlgorithm: PointInclusionAlgorithm

    @Mock
    lateinit var crossingNumberAlgorithm: PointInclusionAlgorithm

    lateinit var areaService: AreaService

    @BeforeEach
    fun setupService() {
        areaService =
            AreaService(
                areaRepository,
                mapOf(PointInclusionMethod.CN to crossingNumberAlgorithm, PointInclusionMethod.WN to windingNumberAlgorithm),
            )
    }

    @Test
    fun saveAreaShouldCallRepositoryAndReturnSavedArea() {
        val points =
            arrayListOf(
                Point(1.0, 1.0),
                Point(4.0, 1.0),
                Point(4.0, 4.0),
                Point(1.0, 4.0),
            )

        val polygon = Polygon("test", points)
        val testArea = Area(polygon = polygon, promos = mutableSetOf())
        val expectedArea = testArea.copy(id = 1)

        `when`(areaRepository.save(testArea)).thenReturn(expectedArea)

        Assertions.assertEquals(areaService.save(testArea), expectedArea)
    }

    @Test
    fun checkAllPointInAreaShouldCallAreaRepositoryAndCalculatePointsUsingGivenCNMethod() {
        val areaPoints =
            arrayListOf(
                Point(1.0, 1.0),
                Point(4.0, 1.0),
                Point(4.0, 4.0),
                Point(1.0, 4.0),
            )
        val pointsToCheck =
            listOf(
                Point(1.1, 1.1),
                Point(3.9, 2.0),
                Point(3.5, 2.0),
            )

        val polygon = Polygon("test", areaPoints)
        val storedArea = Area(id = 1, polygon = polygon, promos = mutableSetOf())

        `when`(areaRepository.findById(1)).thenReturn(storedArea)

        val expectedValues = listOf(true, true, true)

        `when`(crossingNumberAlgorithm.isPointInsidePolygon(any(), eq(polygon))).thenReturn(true)

        Assertions.assertEquals(areaService.checkAllPointsInArea(storedArea.id!!, pointsToCheck, PointInclusionMethod.CN), expectedValues)

        verify(crossingNumberAlgorithm, times(3)).isPointInsidePolygon(any(), eq(polygon))
        verify(windingNumberAlgorithm, times(0)).isPointInsidePolygon(any(), eq(polygon))
    }

    @Test
    fun checkAllPointInAreaShouldCallAreaRepositoryAndCalculatePointsUsingGivenWNMethod() {
        val areaPoints =
            arrayListOf(
                Point(1.0, 1.0),
                Point(4.0, 1.0),
                Point(4.0, 4.0),
                Point(1.0, 4.0),
            )
        val pointsToCheck =
            listOf(
                Point(1.1, 1.1),
                Point(3.9, 2.0),
                Point(3.5, 2.0),
            )

        val polygon = Polygon("test", areaPoints)
        val storedArea = Area(id = 1, polygon = polygon, promos = mutableSetOf())

        `when`(areaRepository.findById(1)).thenReturn(storedArea)

        val expectedValues = listOf(true, true, true)

        `when`(windingNumberAlgorithm.isPointInsidePolygon(any(), eq(polygon))).thenReturn(true)

        Assertions.assertEquals(areaService.checkAllPointsInArea(storedArea.id!!, pointsToCheck, PointInclusionMethod.WN), expectedValues)

        verify(windingNumberAlgorithm, times(3)).isPointInsidePolygon(any(), eq(polygon))
        verify(crossingNumberAlgorithm, times(0)).isPointInsidePolygon(any(), eq(polygon))
    }

    @Test
    fun checkAllPointInAreaShouldReturnNullIfAreaIsNotInRepository() {
        val pointsToCheck =
            listOf(
                Point(1.1, 1.1),
                Point(3.9, 2.0),
                Point(3.5, 2.0),
            )
        val nonexistentAreaId = 0L

        `when`(areaRepository.findById(nonexistentAreaId)).thenReturn(null)

        Assertions.assertNull(areaService.checkAllPointsInArea(nonexistentAreaId, pointsToCheck, PointInclusionMethod.WN))
    }

    @Test
    fun checkPointInAreaShouldCallAreaRepositoryAndCalculatePointUsingGivenCNMethod() {
        val areaPoints =
            arrayListOf(
                Point(1.0, 1.0),
                Point(4.0, 1.0),
                Point(4.0, 4.0),
                Point(1.0, 4.0),
            )
        val pointToCheck = Point(1.1, 1.1)

        val polygon = Polygon("test", areaPoints)
        val storedArea = Area(id = 1, polygon = polygon, promos = mutableSetOf())

        `when`(areaRepository.findById(1)).thenReturn(storedArea)

        val expectedValues = true

        `when`(crossingNumberAlgorithm.isPointInsidePolygon(pointToCheck, polygon)).thenReturn(true)

        Assertions.assertEquals(areaService.checkPointInArea(storedArea.id!!, pointToCheck, PointInclusionMethod.CN), expectedValues)

        verify(windingNumberAlgorithm, times(0)).isPointInsidePolygon(pointToCheck, polygon)
        verify(crossingNumberAlgorithm, times(1)).isPointInsidePolygon(pointToCheck, polygon)
    }

    @Test
    fun checkPointInAreaShouldCallAreaRepositoryAndCalculatePointUsingGivenWNMethod() {
        val areaPoints =
            arrayListOf(
                Point(1.0, 1.0),
                Point(4.0, 1.0),
                Point(4.0, 4.0),
                Point(1.0, 4.0),
            )
        val pointToCheck = Point(1.1, 1.1)

        val polygon = Polygon("test", areaPoints)
        val storedArea = Area(id = 1, polygon = polygon, promos = mutableSetOf())

        `when`(areaRepository.findById(1)).thenReturn(storedArea)

        val expectedValues = true

        `when`(windingNumberAlgorithm.isPointInsidePolygon(pointToCheck, polygon)).thenReturn(true)

        Assertions.assertEquals(areaService.checkPointInArea(storedArea.id!!, pointToCheck, PointInclusionMethod.WN), expectedValues)

        verify(windingNumberAlgorithm, times(1)).isPointInsidePolygon(pointToCheck, polygon)
        verify(crossingNumberAlgorithm, times(0)).isPointInsidePolygon(pointToCheck, polygon)
    }

    @Test
    fun checkPointInAreaShouldReturnNullIfAreaIsNotInRepository() {
        val pointToCheck = Point(1.1, 1.1)
        val nonexistentAreaId = 0L
        `when`(areaRepository.findById(nonexistentAreaId)).thenReturn(null)

        Assertions.assertNull(areaService.checkPointInArea(nonexistentAreaId, pointToCheck, PointInclusionMethod.WN))
    }

    @Test
    fun findAllShouldCallRepositoryAndReturnAListWithAllAreas() {
        val points =
            arrayListOf(
                Point(1.0, 1.0),
                Point(4.0, 1.0),
                Point(4.0, 4.0),
                Point(1.0, 4.0),
            )

        val polygon1 = Polygon("test1", points)
        val polygon2 = Polygon("test2", points)
        val expectedArea1 = Area(polygon = polygon1, promos = mutableSetOf())
        val expectedArea2 = Area(polygon = polygon2, promos = mutableSetOf())

        val expectedAreas = listOf(expectedArea1, expectedArea2)

        `when`(areaRepository.findAll()).thenReturn(expectedAreas)

        Assertions.assertEquals(areaService.findAll(), expectedAreas)
    }

    @Test
    fun deleteAreaByIdShouldCallRepositoryWithGivenId() {
        areaService.deleteById(1)
        verify(areaRepository).deleteById(1)
    }
}
