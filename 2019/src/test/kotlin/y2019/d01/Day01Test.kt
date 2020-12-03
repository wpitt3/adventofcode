package y2019.d01

import kotlin.test.assertEquals
import org.junit.Test as test

class Day01Test{

    @test
    fun testGetFuelRequired() {
        assertEquals(Day01().getFuelRequired(12), 2)
        assertEquals(Day01().getFuelRequired(14), 2)
        assertEquals(Day01().getFuelRequired(1969), 654)
        assertEquals(Day01().getFuelRequired(100756), 33583)
    }

    @test
    fun testGetFuelForAll() {
        val mulipleMasses: List<String> = listOf("12","11")

        assertEquals(Day01().getFuelForAll(mulipleMasses), 3)
    }

    @test
    fun testGetFuelRequiredForFuelAsWell() {
        assertEquals(Day01().getHeavyFuelRequired(12), 2)
        assertEquals(Day01().getHeavyFuelRequired(14), 2)
        assertEquals(Day01().getHeavyFuelRequired(1969), 966)
        assertEquals(Day01().getHeavyFuelRequired(100756), 50346)
    }

    @test
    fun testGetHeavyFuelForAll() {
        val mulipleMasses: List<String> = listOf("1969","12")

        assertEquals(Day01().getHeavyFuelForAll(mulipleMasses), 968)
    }
}
