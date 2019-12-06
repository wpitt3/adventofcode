package y2019.d05

class Day05() {
    fun runWholeSystem(instructions: String): Int {
        var a: MutableList<Int> = instructions.split(",").map({x -> Integer.parseInt(x)}).toMutableList()
        return run(a)[0]
    }

    fun run(instructions: MutableList<Int>): List<Int> {
        var index: Int = 0
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
                instructions[getInstIndex(instructions, modes, index, 1)] = 1
                index += 2
            } else if (optCode == 4) {
                println("diagnostics " + instructions[getInstIndex(instructions, modes, index, 1)])
                index += 2
            } else if (optCode == 99) {
                return instructions
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
