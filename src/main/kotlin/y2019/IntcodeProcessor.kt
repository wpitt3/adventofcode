package y2019

class IntcodeProcessor(var instructions: MutableList<Int>) {
    var inputIndex: Int = 0
    var index: Int = 0
    var callNextAmp: (x: Int) -> Int = {it}
    var relativeBase: Int = 0

    fun run(input: Int) {
        while(true) {
            val optCode: Int =  instructions[index]%100
            val modes: List<Int> = takeModesFromInstruction(instructions[index])

            if (optCode == 1) {
                // add
                instructions[getInstIndex(instructions, modes, index, 3)] =
                        instructions[getInstIndex(instructions, modes, index, 1)] + instructions[getInstIndex(instructions, modes, index, 2)]
                index += 4
            } else if (optCode == 2) {
                // multiply
                instructions[getInstIndex(instructions, modes, index, 3)] =
                        instructions[getInstIndex(instructions, modes, index, 1)] * instructions[getInstIndex(instructions, modes, index, 2)]
                index += 4
            } else if (optCode == 3) {
                // get input
                instructions[getInstIndex(instructions, modes, index, 1)] = input
                inputIndex++
                index += 2
            } else if (optCode == 4) {
                // put output
                val toOutput = instructions[getInstIndex(instructions, modes, index, 1)]
                index += 2
                callNextAmp(toOutput)
            } else if (optCode == 5) {
                // jump if true
                val ifTrueJumpValue : Int = instructions[getInstIndex(instructions, modes, index, 1)]
                instructions[getInstIndex(instructions, modes, index, 2)]
                index = if (ifTrueJumpValue != 0) instructions[getInstIndex(instructions, modes, index, 2)] else (index + 3)
            } else if (optCode == 6) {
                // jump if false
                val ifFalseJumpValue : Int = instructions[getInstIndex(instructions, modes, index, 1)]
                index = if (ifFalseJumpValue == 0) instructions[getInstIndex(instructions, modes, index, 2)] else (index + 3)
            } else if (optCode == 7) {
                // is less than
                val isLessThan : Boolean = instructions[getInstIndex(instructions, modes, index, 1)] < instructions[getInstIndex(instructions, modes, index, 2)]
                instructions[getInstIndex(instructions, modes, index, 3)] = if (isLessThan) 1 else 0
                index += 4
            } else if (optCode == 8) {
                // is equal to
                val isEqualTo : Boolean = instructions[getInstIndex(instructions, modes, index, 1)] == instructions[getInstIndex(instructions, modes, index, 2)]
                instructions[getInstIndex(instructions, modes, index, 3)] = if (isEqualTo) 1 else 0
                index += 4
            } else if (optCode == 9) {
                // modify relativeBase
                val instruction = instructions[getInstIndex(instructions, modes, index, 1)]
                relativeBase += instruction
                index += 2
            }  else if (optCode == 99) {
                return
            } else {
                throw Exception("Fail")
            }
        }
    }

    fun getInstIndex(instructions: MutableList<Int>, modes: List<Int>, index: Int, argIndex:Int): Int{
        if (modes[argIndex-1] == 0) {
            // position mode
            return instructions[index + argIndex]
        } else if (modes[argIndex-1] == 1) {
            // immediate mode
            return index + argIndex
        } else if (modes[argIndex-1] == 2) {
            // relative mode
            return instructions[index + argIndex] + relativeBase
        } else {
            throw Exception("broken mode")
        }
    }

    fun takeModesFromInstruction(instruction :Int): List<Int> {
        var instructionString = "0"+instruction.toString()
        instructionString = instructionString.substring(0, instructionString.length - 2)
        var result =  instructionString
                .map({ if(it != null) Integer.parseInt(it.toString()) else 0})
        .reversed()
                .toMutableList()
        result.addAll(listOf(0,0,0))
        return result.take(3)
    }



}
