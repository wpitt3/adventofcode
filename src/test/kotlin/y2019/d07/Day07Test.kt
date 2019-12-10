package y2019.d07
import kotlin.test.assertEquals
import org.junit.Test as test

class Day07Test{

    @test
    fun testRun() {
        assertEquals(listOf(2,0,0,0,99), Day07().runAndReturnInstructions(mutableListOf(1,0,0,0,99),1, 1))
        assertEquals(listOf(1,0,0,2,99), Day07().runAndReturnInstructions(mutableListOf(1,0,0,3,99),1, 1))
        assertEquals(listOf(2,3,0,6,99), Day07().runAndReturnInstructions(mutableListOf(2,3,0,3,99),1, 1))
        assertEquals(listOf(99,0,0,0,0), Day07().runAndReturnInstructions(mutableListOf(99,0,0,0,0),1, 1))
        assertEquals(listOf(4,0,0,0,1,0,0,0,99), Day07().runAndReturnInstructions(mutableListOf(1,0,0,0,1,0,0,0,99),1, 1))
        assertEquals(listOf(2,4,4,5,99,9801), Day07().runAndReturnInstructions(mutableListOf(2,4,4,5,99,0),1, 1))
        assertEquals(listOf(30,1,1,4,2,5,6,0,99), Day07().runAndReturnInstructions(mutableListOf(1,1,1,4,99,5,6,0,99),1, 1))
        assertEquals(listOf(1101,1,1,1,99), Day07().runAndReturnInstructions(mutableListOf(1101,0,1,1,99),1, 1))

        assertEquals(listOf(2210,0,7,1,0,0,0,99), Day07().runAndReturnInstructions(mutableListOf(1105,0,7,1,0,0,0,99),1, 1)) // jump if true val is false doesn't jump
        assertEquals(listOf(1005,0,7,1,0,0,0,99), Day07().runAndReturnInstructions(mutableListOf(1005,0,7,1,0,0,0,99),1, 1))// jump if true val is true jumps

        assertEquals(listOf(1106,0,7,1,0,0,0,99), Day07().runAndReturnInstructions(mutableListOf(1106,0,7,1,0,0,0,99),1, 1)) // jump if false val is false jumps
        assertEquals(listOf(2012,0,7,1,0,0,0,99), Day07().runAndReturnInstructions(mutableListOf(1006,0,7,1,0,0,0,99),1, 1)) // jump if false val is true doesn't jump

        assertEquals(listOf(11107,0,1,1,99), Day07().runAndReturnInstructions(mutableListOf(11107,0,1,3,99),1, 1))
        assertEquals(listOf(11107,1,0,0,99), Day07().runAndReturnInstructions(mutableListOf(11107,1,0,3,99),1, 1))

        assertEquals(listOf(11108,0,0,1,99), Day07().runAndReturnInstructions(mutableListOf(11108,0,0,3,99),1, 1))
        assertEquals(listOf(11108,1,0,0,99), Day07().runAndReturnInstructions(mutableListOf(11108,1,0,3,99),1, 1))
    }

    @test
    fun testPhaseAndInput() {
        assertEquals(listOf(6, 2, 1, 0, 0, 0, 99), Day07().runAndReturnInstructions(mutableListOf(3, 2, 2, 0, 0, 0, 99), 1, 5))
        assertEquals(listOf(6, 2, 3, 4, 1, 0, 0, 0, 99), Day07().runAndReturnInstructions(mutableListOf(3, 2, 2, 4, 2, 0, 0, 0, 99), 3, 1))
    }

    @test
    fun testExample() {
        assertEquals(54321, Day07().runAllPhases("3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0", listOf(0,1,2,3,4)))
        assertEquals(43210, Day07().runAllPhases("3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0", listOf(4,3,2,1,0)))
        assertEquals(65210, Day07().runAllPhases("3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0", listOf(1,0,4,3,2)))
    }

    @test
    fun testTakeModesFromInstruction() {
        assertEquals(listOf(0, 1, 0), Day07().takeModesFromInstruction(1001))
        assertEquals(listOf(0, 0, 0), Day07().takeModesFromInstruction(1))
        assertEquals(listOf(1, 1, 1), Day07().takeModesFromInstruction(11101))
    }

    @test
    fun testLinkedAmps() {
        assertEquals(139629729, Day07().runAllPhasesWithLinkedAmps("3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5", listOf(9,8,7,6,5)))
        assertEquals(18216, Day07().runAllPhasesWithLinkedAmps("3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10", listOf(9,7,8,5,6)))
    }
}
