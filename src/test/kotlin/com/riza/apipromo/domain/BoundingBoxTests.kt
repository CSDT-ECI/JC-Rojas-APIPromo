package com.riza.apipromo.domain

import com.riza.apipromo.domain.geometry.BoundingBox
import com.riza.apipromo.domain.geometry.Point
import com.riza.apipromo.domain.geometry.Polygon
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class BoundingBoxTests {
    @ParameterizedTest
    @CsvSource(
        "1.0, 1.0",
        "4.0, 1.0",
        "4.0,4.0",
        "1.0,4.0",
        "2.5,2.0",
    )
    fun pointsInLimitsOfBoundingBoxOfTriangleShouldBeTrue(
        pointXCoord: Double,
        pointYCoord: Double,
    ) {
        val points =
            arrayListOf(
                Point(1.0, 1.0),
                Point(4.0, 1.0),
                Point(2.5, 4.0),
            )

        val polygon = Polygon("test", points)
        val boundingBox = BoundingBox(polygon)

        val point = Point(pointXCoord, pointYCoord)

        Assertions.assertTrue(boundingBox.isInside(point))
    }

    @ParameterizedTest
    @CsvSource(
        "0.9, 1.0",
        "1.0, 0.9",
        "4.1, 1.0",
        "4.0,4.1",
    )
    fun pointsOutOFLimitsOfBoundingBoxOfTriangleShouldBeFalse(
        pointXCoord: Double,
        pointYCoord: Double,
    ) {
        val points =
            arrayListOf(
                Point(1.0, 1.0),
                Point(4.0, 1.0),
                Point(2.5, 4.0),
            )

        val polygon = Polygon("test", points)
        val boundingBox = BoundingBox(polygon)

        val point = Point(pointXCoord, pointYCoord)

        Assertions.assertFalse(boundingBox.isInside(point))
    }
}
