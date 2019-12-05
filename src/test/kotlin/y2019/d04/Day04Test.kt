package y2019.d04

import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse
import org.junit.Test as test

class Day04Test{

    @test
    fun testIsValid() {
        assertTrue(Day04().isValid(112345))
        assertFalse(Day04().isValid(1))
        assertFalse(Day04().isValid(123456))
        assertFalse(Day04().isValid(113454))
    }

    @test
    fun testtoUnitIntList() {
        assertEquals(listOf(1,1,2,3,4,5), Day04().toUnitIntList(112345))
    }

    @test
    fun countValid() {
        assertEquals(1, Day04().countValidInRange(123454, 123456))
        assertEquals(1, Day04().countValidInRange(123455, 123456))
        assertEquals(6, Day04().countValidInRange(112340, 112350))
    }

    @test
    fun testIsValidRule2() {
        assertTrue(Day04().isValidRule2(112345))
        assertTrue(Day04().isValidRule2(112233))
        assertTrue(Day04().isValidRule2(111122))
        assertFalse(Day04().isValidRule2(1))
        assertFalse(Day04().isValidRule2(123456))
        assertFalse(Day04().isValidRule2(113454))
        assertFalse(Day04().isValidRule2(123444))
    }
}
