package y2019
import kotlin.test.assertEquals
import org.junit.Test as test

class IntcodeProcessorTest{

    @test
    fun testEverything() {
        assertEquals(listOf<Long>(2,0,0,0,99), runIntcodeAndGetInstructions(mutableListOf(1,0,0,0,99), 1))
        assertEquals(listOf<Long>(1,0,0,2,99), runIntcodeAndGetInstructions(mutableListOf(1,0,0,3,99), 1))
        assertEquals(listOf<Long>(2,3,0,6,99), runIntcodeAndGetInstructions(mutableListOf(2,3,0,3,99), 1))
        assertEquals(listOf<Long>(99), runIntcodeAndGetInstructions(mutableListOf(99,0,0,0,0), 1))
        assertEquals(listOf<Long>(4,0,0,0,1,0,0,0,99), runIntcodeAndGetInstructions(mutableListOf(1,0,0,0,1,0,0,0,99), 1))
        assertEquals(listOf<Long>(9801,4,4,0,99), runIntcodeAndGetInstructions(mutableListOf(2,4,4,0,99,0), 1))
        assertEquals(listOf<Long>(30,1,1,4,2,5,6,0,99), runIntcodeAndGetInstructions(mutableListOf(1,1,1,4,99,5,6,0,99), 1))
        assertEquals(listOf<Long>(1101,1,1,1,99), runIntcodeAndGetInstructions(mutableListOf(1101,0,1,1,99), 1))

        assertEquals(listOf<Long>(2210,0,7,1,0,0,0,99), runIntcodeAndGetInstructions(mutableListOf(1105,0,7,1,0,0,0,99), 1)) // jump if true val is false doesn't jump
        assertEquals(listOf<Long>(1005,0,7,1,0,0,0,99), runIntcodeAndGetInstructions(mutableListOf(1005,0,7,1,0,0,0,99), 1))// jump if true val is true jumps

        assertEquals(listOf<Long>(1106,0,7,1,0,0,0,99), runIntcodeAndGetInstructions(mutableListOf(1106,0,7,1,0,0,0,99), 1)) // jump if false val is false jumps
        assertEquals(listOf<Long>(2012,0,7,1,0,0,0,99), runIntcodeAndGetInstructions(mutableListOf(1006,0,7,1,0,0,0,99), 1)) // jump if false val is true doesn't jump

        assertEquals(listOf<Long>(11107,0,1,1,99), runIntcodeAndGetInstructions(mutableListOf(11107,0,1,3,99), 1))
        assertEquals(listOf<Long>(11107,1,0,0,99), runIntcodeAndGetInstructions(mutableListOf(11107,1,0,3,99), 1))

        assertEquals(listOf<Long>(11108,0,0,1,99), runIntcodeAndGetInstructions(mutableListOf(11108,0,0,3,99), 1))
        assertEquals(listOf<Long>(11108,1,0,0,99), runIntcodeAndGetInstructions(mutableListOf(11108,1,0,3,99), 1))
    }

    @test
    fun testTakeModesFromInstruction() {
        assertEquals(listOf(0, 1, 0), IntcodeProcessor(mutableListOf()).takeModesFromInstruction(1001))
        assertEquals(listOf(0, 0, 0), IntcodeProcessor(mutableListOf()).takeModesFromInstruction(1))
        assertEquals(listOf(1, 1, 1), IntcodeProcessor(mutableListOf()).takeModesFromInstruction(11101))
    }

    @test
    fun relativebaseCanBeModified() {
        var processor = IntcodeProcessor(mutableListOf(109,19, 99))
        processor.run(1)
        assertEquals(processor.relativeBase, 19)
    }

    @test
    fun relativebaseIsUsed() {
        assertEquals(listOf<Long>(99), runIntcodeAndGetOutput(mutableListOf(109,3,204,1,99), 1))
        assertEquals(listOf<Long>(0), runIntcodeAndGetOutput(mutableListOf(109,0,204,1,99), 1))
    }

    @test
    fun instructionLengthAndSizeCanBeLarge() {
        assertEquals(listOf<Long>(0), runIntcodeAndGetOutput(mutableListOf(204,4,99), 1))
        assertEquals(listOf<Long>(109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99), runIntcodeAndGetOutput(mutableListOf(109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99), 1))
        assertEquals(listOf<Long>(1_219_070_632_396_864), runIntcodeAndGetOutput(mutableListOf(1102,34915192,34915192,7,4,7,99,0), 1))
        assertEquals(listOf<Long>(1125899906842624), runIntcodeAndGetOutput(mutableListOf(104,1125899906842624,99), 1))
    }

    fun runIntcodeAndGetInstructions(instructions: MutableList<Long>, input: Long): MutableList<Long> {
        var processor = IntcodeProcessor(instructions)
        processor.run(input)
        val endIndex = processor.instructions.lastIndexOf(99)
        return processor.instructions.subList(0, endIndex+1)
    }

    fun runIntcodeAndGetOutput(instructions: MutableList<Long>, input: Long): MutableList<Long> {
        var processor = IntcodeProcessor(instructions)
        var result = mutableListOf<Long>()
        processor.callNextAmp = {it -> result.add(it); 1}
        processor.run(input)
        return result
    }
}
