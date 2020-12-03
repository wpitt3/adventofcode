package y2019.d02

import kotlin.test.assertEquals
import org.junit.Test as test

class Day02Test{

    @test
    fun testRun() {
        assertEquals(listOf(2,0,0,0,99), Day02().run(mutableListOf(1,0,0,0,99)))
        assertEquals(listOf(1,0,0,2,99), Day02().run(mutableListOf(1,0,0,3,99)))
        assertEquals(listOf(2,3,0,6,99), Day02().run(mutableListOf(2,3,0,3,99)))
        assertEquals(listOf(99,0,0,0,0), Day02().run(mutableListOf(99,0,0,0,0)))
        assertEquals(listOf(4,0,0,0,1,0,0,0,99), Day02().run(mutableListOf(1,0,0,0,1,0,0,0,99)))
        assertEquals(listOf(2,4,4,5,99,9801), Day02().run(mutableListOf(2,4,4,5,99,0)))
        assertEquals(listOf(30,1,1,4,2,5,6,0,99), Day02().run(mutableListOf(1,1,1,4,99,5,6,0,99)))
        assertEquals(listOf(48,12,2,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,99), Day02().run(mutableListOf(1,12,2,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,99)))
    }

    @test
    fun testReadInputStringAsInstructionsAndRun() {
        assertEquals(48, Day02().runWholeSystem("1,12,2,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,99", 12, 2))
        assertEquals(48, Day02().runWholeSystem("1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,99",12,2))

    }
}
