package y2019.d07

class Day07() {

    fun runAllPhasesWithLinkedAmps(instructions: String, phases: List<Int>): Int {
        var a: List<Int> = instructions.split(",").map({x -> Integer.parseInt(x)}).toList()
        val ampA = Amp(a.toMutableList(), phases[0], "A")
        val ampB = Amp(a.toMutableList(), phases[1], "B")
        val ampC = Amp(a.toMutableList(), phases[2], "C")
        val ampD = Amp(a.toMutableList(), phases[3], "D")
        val ampE = Amp(a.toMutableList(), phases[4], "E")
        ampA.callNextAmp = {ampB.run(it)}
        ampB.callNextAmp = {ampC.run(it)}
        ampC.callNextAmp = {ampD.run(it)}
        ampD.callNextAmp = {ampE.run(it)}
        ampE.callNextAmp = {ampA.run(it)}
        return ampA.run(0)
    }

    fun runAllPhases(instructions: String, phases: List<Int>): Int {
        var a: List<Int> = instructions.split(",").map({x -> Integer.parseInt(x)}).toList()
        var x = 0
        for(phase in phases) {
            x = runAndGetResponse(a.toMutableList(), phase, x)
        }
        return x
    }

    fun runAndReturnInstructions(instructions: MutableList<Int>, phase: Int, input: Int): List<Int> {
        return runInstructions(instructions, phase, input, true)
    }

    fun runAndGetResponse(instructions: MutableList<Int>, phase: Int, input: Int): Int {
        return runInstructions(instructions, phase, input, false).get(0)
    }

    fun runInstructions(instructions: MutableList<Int>, phase: Int, input: Int, returnIntructions: Boolean): List<Int> {
        var inputIndex: Int = 0
        var index: Int = 0
        var lastOutput: Int = Int.MAX_VALUE
        while(true) {
            val optCode: Int =  instructions[index]%100
            val modes: List<Int> = takeModesFromInstruction(instructions[index])

            if (optCode == 1) {
                instructions[getInstIndex(instructions, modes, index, 3)] =
                    instructions[getInstIndex(instructions, modes, index, 1)] + instructions[getInstIndex(instructions, modes, index, 2)]
                index += 4
            } else if (optCode == 2) {
                instructions[getInstIndex(instructions, modes, index, 3)] =
                    instructions[getInstIndex(instructions, modes, index, 1)] * instructions[getInstIndex(instructions, modes, index, 2)]
                index += 4
            } else if (optCode == 3) {
                instructions[getInstIndex(instructions, modes, index, 1)] = (if (inputIndex == 0) phase else input)
                inputIndex++
                index += 2
            } else if (optCode == 4) {
                lastOutput = instructions[getInstIndex(instructions, modes, index, 1)];
                index += 2
            } else if (optCode == 5) {
                val ifTrueJumpValue : Int = instructions[getInstIndex(instructions, modes, index, 1)]
                instructions[getInstIndex(instructions, modes, index, 2)]
                index = if (ifTrueJumpValue != 0) instructions[getInstIndex(instructions, modes, index, 2)] else (index + 3)
            } else if (optCode == 6) {
                val ifFalseJumpValue : Int = instructions[getInstIndex(instructions, modes, index, 1)]
                index = if (ifFalseJumpValue == 0) instructions[getInstIndex(instructions, modes, index, 2)] else (index + 3)
            } else if (optCode == 7) {
                val isLessThan : Boolean = instructions[getInstIndex(instructions, modes, index, 1)] < instructions[getInstIndex(instructions, modes, index, 2)]
                instructions[getInstIndex(instructions, modes, index, 3)] = if (isLessThan) 1 else 0
                index += 4
            } else if (optCode == 8) {
                val isEqualTo : Boolean = instructions[getInstIndex(instructions, modes, index, 1)] == instructions[getInstIndex(instructions, modes, index, 2)]
                instructions[getInstIndex(instructions, modes, index, 3)] = if (isEqualTo) 1 else 0
                index += 4
            } else if (optCode == 99) {
                if(returnIntructions) {
                    return instructions
                } else {
                    return listOf(lastOutput);
                }
            } else {
                throw Exception("Fail")
            }
        }
    }

    fun getInstIndex(instructions: MutableList<Int>, modes: List<Int>, index: Int, argIndex:Int): Int{
        return if (modes[argIndex-1] == 0) instructions[index + argIndex] else index + argIndex
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
