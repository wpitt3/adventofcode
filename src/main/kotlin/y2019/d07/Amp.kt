package y2019.d07

class Amp(instructionsX: MutableList<Int>, phaseX: Int, nameX: String) {

    var instructions : MutableList<Int> = mutableListOf()
    var phase :Int = 0
    var inputIndex: Int = 0
    var index: Int = 0
    var callNextAmp: (x: Int) -> Int = {it}
    var name: String = ""

    init {
        instructions = instructionsX
        phase = phaseX
        name = nameX
    }

    fun run(input: Int): Int {
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
                lastOutput = instructions[getInstIndex(instructions, modes, index, 1)]
                index += 2
//                println(name + " " + lastOutput)
                val x = callNextAmp(lastOutput)
                return if (name != "E" || x != -15342) x else lastOutput
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
                return if (name == "E") lastOutput else -15342 // obvious issue here so magic number
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
