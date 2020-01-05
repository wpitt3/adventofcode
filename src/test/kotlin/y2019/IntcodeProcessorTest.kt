package y2019
import kotlin.test.assertEquals
import org.junit.Test as test

class IntcodeProcessorTest{

    @test
    fun testEverything() {
        assertEquals(listOf(2,0,0,0,99), runIntcodeAndGetInstructions(mutableListOf(1,0,0,0,99), 1))
        assertEquals(listOf(1,0,0,2,99), runIntcodeAndGetInstructions(mutableListOf(1,0,0,3,99), 1))
        assertEquals(listOf(2,3,0,6,99), runIntcodeAndGetInstructions(mutableListOf(2,3,0,3,99), 1))
        assertEquals(listOf(99,0,0,0,0), runIntcodeAndGetInstructions(mutableListOf(99,0,0,0,0), 1))
        assertEquals(listOf(4,0,0,0,1,0,0,0,99), runIntcodeAndGetInstructions(mutableListOf(1,0,0,0,1,0,0,0,99), 1))
        assertEquals(listOf(2,4,4,5,99,9801), runIntcodeAndGetInstructions(mutableListOf(2,4,4,5,99,0), 1))
        assertEquals(listOf(30,1,1,4,2,5,6,0,99), runIntcodeAndGetInstructions(mutableListOf(1,1,1,4,99,5,6,0,99), 1))
        assertEquals(listOf(1101,1,1,1,99), runIntcodeAndGetInstructions(mutableListOf(1101,0,1,1,99), 1))

        assertEquals(listOf(2210,0,7,1,0,0,0,99), runIntcodeAndGetInstructions(mutableListOf(1105,0,7,1,0,0,0,99), 1)) // jump if true val is false doesn't jump
        assertEquals(listOf(1005,0,7,1,0,0,0,99), runIntcodeAndGetInstructions(mutableListOf(1005,0,7,1,0,0,0,99), 1))// jump if true val is true jumps

        assertEquals(listOf(1106,0,7,1,0,0,0,99), runIntcodeAndGetInstructions(mutableListOf(1106,0,7,1,0,0,0,99), 1)) // jump if false val is false jumps
        assertEquals(listOf(2012,0,7,1,0,0,0,99), runIntcodeAndGetInstructions(mutableListOf(1006,0,7,1,0,0,0,99), 1)) // jump if false val is true doesn't jump

        assertEquals(listOf(11107,0,1,1,99), runIntcodeAndGetInstructions(mutableListOf(11107,0,1,3,99), 1))
        assertEquals(listOf(11107,1,0,0,99), runIntcodeAndGetInstructions(mutableListOf(11107,1,0,3,99), 1))

        assertEquals(listOf(11108,0,0,1,99), runIntcodeAndGetInstructions(mutableListOf(11108,0,0,3,99), 1))
        assertEquals(listOf(11108,1,0,0,99), runIntcodeAndGetInstructions(mutableListOf(11108,1,0,3,99), 1))
    }

    @test
    fun relativebaseCanBeModified() {
        var processor = IntcodeProcessor(mutableListOf(109,19, 99))
        processor.run(1)
        assertEquals(processor.relativeBase, 19)
    }

    @test
    fun relativebaseIsUsed() {
        assertEquals(listOf(99), runIntcodeAndGetOutput(mutableListOf(109,3,204,1,99), 1))
        assertEquals(listOf(0), runIntcodeAndGetOutput(mutableListOf(109,0,204,1,99), 1))
    }

    @test
    fun testTakeModesFromInstruction() {
        assertEquals(listOf(0, 1, 0), IntcodeProcessor(mutableListOf()).takeModesFromInstruction(1001))
        assertEquals(listOf(0, 0, 0), IntcodeProcessor(mutableListOf()).takeModesFromInstruction(1))
        assertEquals(listOf(1, 1, 1), IntcodeProcessor(mutableListOf()).takeModesFromInstruction(11101))
    }

    fun runIntcodeAndGetInstructions(instructions: MutableList<Int>, input: Int): MutableList<Int> {
        var processor = IntcodeProcessor(instructions)
        processor.run(input)
        return processor.instructions
    }

    fun runIntcodeAndGetOutput(instructions: MutableList<Int>, input: Int): MutableList<Int> {
        var processor = IntcodeProcessor(instructions)
        var result = mutableListOf<Int>()
        processor.callNextAmp = {it -> result.add(it); 1}
        processor.run(input)
        return result
    }
}
