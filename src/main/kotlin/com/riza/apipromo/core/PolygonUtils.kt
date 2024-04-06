package riza.com.cto.core

import com.riza.apipromo.core.Point
import java.lang.Math.random
import kotlin.math.*

/**
 * Created by riza@deliv.co.id on 2/29/20.
 */


object PolygonUtils {

    /*
    https://gis.stackexchange.com/questions/2951/algorithm-for-offsetting-a-latitude-longitude-by-some-amount-of-meters
     */

    fun meterToDegree(meter: Double) = meter / 111111
    fun generateRandomPointFrom(point: Point, radiusInDegree: Double): Point {

        val a = random() * 2 * PI
        val r = radiusInDegree * sqrt(random())

        val x = r * cos(a)
        val y = r * sin(a)

        return Point(point.x + x, point.y + y)
    }

    fun generateWalkPoints(point: Point, radius: Double, total: Int): List<Point> {

        val result = arrayListOf<Point>()

        var nextPoint = point
        for (i in 0 until total) {
            val p = generateRandomPointFrom(nextPoint, meterToDegree(radius))
            result.add(p)
            nextPoint = p
        }

        return result
    }

}