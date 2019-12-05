package y2019.d02

import java.util.*

class Day02() {
    fun run(instructions: MutableList<Int>): List<Int> {
        var index: Int = 0
        while(true) {
            if (instructions[index] == 1) {
                instructions[instructions[index + 3]] =
                    instructions[instructions[index + 1]] + instructions[instructions[index + 2]]
            } else if (instructions[index] == 2) {
                instructions[instructions[index + 3]] =
                    instructions[instructions[index + 1]] * instructions[instructions[index + 2]]
            } else if (instructions[index] == 99) {
                return instructions
            }
            index += 4
        }
    }

    fun runWholeSystem(instructions: String, noun: Int, verb: Int): Int {
        var a: MutableList<Int> = instructions.split(",").map({x -> Integer.parseInt(x)}).toMutableList()
        a[1]=noun
        a[2]=verb
        return run(a)[0]
    }
}
