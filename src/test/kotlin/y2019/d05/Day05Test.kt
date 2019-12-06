package y2019.d05
import kotlin.test.assertEquals
import org.junit.Test as test

class Day05Test{

    @test
    fun testRun() {
        assertEquals(listOf(2,0,0,0,99), Day05().run(mutableListOf(1,0,0,0,99), 1))
        assertEquals(listOf(1,0,0,2,99), Day05().run(mutableListOf(1,0,0,3,99), 1))
        assertEquals(listOf(2,3,0,6,99), Day05().run(mutableListOf(2,3,0,3,99), 1))
        assertEquals(listOf(99,0,0,0,0), Day05().run(mutableListOf(99,0,0,0,0), 1))
        assertEquals(listOf(4,0,0,0,1,0,0,0,99), Day05().run(mutableListOf(1,0,0,0,1,0,0,0,99), 1))
        assertEquals(listOf(2,4,4,5,99,9801), Day05().run(mutableListOf(2,4,4,5,99,0), 1))
        assertEquals(listOf(30,1,1,4,2,5,6,0,99), Day05().run(mutableListOf(1,1,1,4,99,5,6,0,99), 1))
        assertEquals(listOf(1101,1,1,1,99), Day05().run(mutableListOf(1101,0,1,1,99), 1))

        assertEquals(listOf(2210,0,7,1,0,0,0,99), Day05().run(mutableListOf(1105,0,7,1,0,0,0,99), 1)) // jump if true val is false doesn't jump
        assertEquals(listOf(1005,0,7,1,0,0,0,99), Day05().run(mutableListOf(1005,0,7,1,0,0,0,99), 1))// jump if true val is true jumps

        assertEquals(listOf(1106,0,7,1,0,0,0,99), Day05().run(mutableListOf(1106,0,7,1,0,0,0,99), 1)) // jump if false val is false jumps
        assertEquals(listOf(2012,0,7,1,0,0,0,99), Day05().run(mutableListOf(1006,0,7,1,0,0,0,99), 1)) // jump if false val is true doesn't jump

        assertEquals(listOf(11107,0,1,1,99), Day05().run(mutableListOf(11107,0,1,3,99), 1))
        assertEquals(listOf(11107,1,0,0,99), Day05().run(mutableListOf(11107,1,0,3,99), 1))

        assertEquals(listOf(11108,0,0,1,99), Day05().run(mutableListOf(11108,0,0,3,99), 1))
        assertEquals(listOf(11108,1,0,0,99), Day05().run(mutableListOf(11108,1,0,3,99), 1))
    }

    @test
    fun testTakeModesFromInstruction() {
        assertEquals(listOf(0, 1, 0), Day05().takeModesFromInstruction(1001))
        assertEquals(listOf(0, 0, 0), Day05().takeModesFromInstruction(1))
        assertEquals(listOf(1, 1, 1), Day05().takeModesFromInstruction(11101))
    }
}
