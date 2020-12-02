import y2019.IntcodeProcessor
import java.io.File

fun main() {
    val lines: List<String> = File("src/main/resources/y2019d21.txt").readLines()
    val instructions: List<Long> = lines[0].split(",").map({x -> (x).toLong()}).toList()
    day21a(instructions)
    day21b(instructions)
}

fun day21a(instructions: List<Long>) {
    val robot = Robot(instructions, listOf(
            "NOT A J",
            "NOT B T",
            "OR J T",
            "NOT C J",
            "OR T J",
            "AND D J",
            "WALK"
    ))
    robot.run()
    println(robot.result())
}

fun day21b(instructions: List<Long>) {
    val robot = Robot(instructions, listOf(
            "NOT A J",
            "NOT B T",
            "OR J T",
            "NOT C J",
            "OR T J",

            "NOT J T",
            "OR E T",
            "OR H T",
            "AND D T",
            "AND T J",
            "RUN"
    ))
    robot.run()
    println(robot.result())
}

class Robot(val intCodeInstructions: List<Long>, springScriptInstructions: List<String>) {
    var processor = IntcodeProcessor(intCodeInstructions.toMutableList())
    var output = mutableListOf<Int>()
    var inputs = mutableListOf<Long>()
    var inputIndex = 0

    init {
        springScriptInstructions.forEach{line ->
            inputs.addAll(line.toCharArray().map{it.toInt().toLong()})
            inputs.add(10)
        }
    }

    fun run() {
        processor.callOutput = { it ->  output.add(it.toInt())}
        processor.callInput = { inputs[inputIndex++] }
        processor.reset(intCodeInstructions.toMutableList())
        processor.run()
    }

    fun result(): String {
        return output.map{if (it < 1000000) it.toInt().toChar().toString() else it.toString()}.joinToString()
    }
}
