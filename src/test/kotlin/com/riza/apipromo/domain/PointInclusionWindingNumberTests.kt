package com.riza.apipromo.domain

import com.riza.apipromo.core.Point
import com.riza.apipromo.core.PointInclusion
import com.riza.apipromo.core.Polygon
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PointInclusionWindingNumberTests {
    @Test
    fun pointInsideSquareShouldBeTrue() {
        val points =
            arrayListOf(
                Point(1.0, 1.0),
                Point(4.0, 1.0),
                Point(4.0, 4.0),
                Point(1.0, 4.0),
            )
        val pointInclusion = PointInclusion()
        val polygon = Polygon("test", points)

        val pointInside = Point(2.5, 2.5)

        Assertions.assertTrue(pointInclusion.analyzePointByWN(polygon, pointInside))
    }

    @Test
    fun pointOutsideSquareShouldBeFalse() {
        val points =
            arrayListOf(
                Point(1.0, 1.0),
                Point(4.0, 1.0),
                Point(4.0, 4.0),
                Point(1.0, 4.0),
            )
        val pointInclusion = PointInclusion()
        val polygon = Polygon("test", points)

        val pointInside = Point(0.0, 0.0)

        Assertions.assertFalse(pointInclusion.analyzePointByWN(polygon, pointInside))
    }

    @Test
    fun pointInUpperLimitOfSquareShouldBeFalse() {
        val points =
            arrayListOf(
                Point(1.0, 1.0),
                Point(4.0, 1.0),
                Point(4.0, 4.0),
                Point(1.0, 4.0),
            )
        val pointInclusion = PointInclusion()
        val polygon = Polygon("test", points)

        val pointInside = Point(1.5, 4.0)

        Assertions.assertFalse(pointInclusion.analyzePointByWN(polygon, pointInside))
    }

    @Test
    fun pointInLowerLimitOfSquareShouldBeTrue() {
        val points =
            arrayListOf(
                Point(1.0, 1.0),
                Point(4.0, 1.0),
                Point(4.0, 4.0),
                Point(1.0, 4.0),
            )
        val pointInclusion = PointInclusion()
        val polygon = Polygon("test", points)

        val pointInside = Point(1.5, 1.0)

        Assertions.assertTrue(pointInclusion.analyzePointByWN(polygon, pointInside))
    }

    @Test
    fun pointInLeftLimitOfSquareShouldBeTrue() {
        val points =
            arrayListOf(
                Point(1.0, 1.0),
                Point(4.0, 1.0),
                Point(4.0, 4.0),
                Point(1.0, 4.0),
            )
        val pointInclusion = PointInclusion()
        val polygon = Polygon("test", points)

        val pointInside = Point(1.0, 1.5)

        Assertions.assertTrue(pointInclusion.analyzePointByWN(polygon, pointInside))
    }

    @Test
    fun pointInRightLimitOfSquareShouldBeFalse() {
        val points =
            arrayListOf(
                Point(1.0, 1.0),
                Point(4.0, 1.0),
                Point(4.0, 4.0),
                Point(1.0, 4.0),
            )
        val pointInclusion = PointInclusion()
        val polygon = Polygon("test", points)

        val pointInside = Point(4.0, 1.5)

        Assertions.assertFalse(pointInclusion.analyzePointByWN(polygon, pointInside))
    }

    @Test
    fun pointInsideASelfIntersectionShouldBeTrue() {
        val points =
            arrayListOf(
                Point(1.0, 1.0),
                Point(7.0, 1.0),
                Point(7.0, 6.0),
                Point(2.0, 6.0),
                Point(2.0, 4.0),
                Point(5.0, 4.0),
                Point(5.0, 2.0),
                Point(3.0, 2.0),
                Point(3.0, 7.0),
                Point(1.0, 7.0),
            )
        val pointInclusion = PointInclusion()
        val polygon = Polygon("test", points)

        val pointInside = Point(2.5, 5.0)

        Assertions.assertTrue(pointInclusion.analyzePointByWN(polygon, pointInside))
    }

    @Test
    fun pointInsidePolygonWithSelfIntersectionsShouldBeTrue() {
        val points =
            arrayListOf(
                Point(1.0, 1.0),
                Point(7.0, 1.0),
                Point(7.0, 6.0),
                Point(2.0, 6.0),
                Point(2.0, 4.0),
                Point(5.0, 4.0),
                Point(5.0, 2.0),
                Point(3.0, 2.0),
                Point(3.0, 8.0),
                Point(1.0, 8.0),
            )
        val pointInclusion = PointInclusion()
        val polygon = Polygon("test", points)

        val pointInside = Point(1.5, 5.0)

        Assertions.assertTrue(pointInclusion.analyzePointByWN(polygon, pointInside))
    }

    @Test
    fun pointInsideInteriorRegionsOfComplexPolygonShouldBeFalse() {
        val points =
            arrayListOf(
                Point(1.0, 1.0),
                Point(7.0, 1.0),
                Point(7.0, 6.0),
                Point(2.0, 6.0),
                Point(2.0, 4.0),
                Point(5.0, 4.0),
                Point(5.0, 2.0),
                Point(3.0, 2.0),
                Point(3.0, 7.0),
                Point(1.0, 7.0),
            )
        val pointInclusion = PointInclusion()
        val polygon = Polygon("test", points)

        val pointInside = Point(4.5, 3.0)

        Assertions.assertFalse(pointInclusion.analyzePointByWN(polygon, pointInside))
    }
}
