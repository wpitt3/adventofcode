package y2019.d08

import kotlin.test.assertEquals
import org.junit.Test as test

class Day08Test{

    @test
    fun testOneLayer() {
        val result: Int = Day08().findPixelCount(listOf(1,2,2,1), 2, 2)
        assertEquals(result, 4)
    }

    @test
    fun testTwoLayers() {
        val result: Int = Day08().findPixelCount(listOf(0,0,2,1,0,2,1,1), 2, 2)
        assertEquals(result, 2)
    }
    @test
    fun testTwoMoreLayers() {
        val result: Int = Day08().findPixelCount(listOf(0,0,0,1,0,2,1,0), 2, 2)
        assertEquals(result, 1)
    }
}
