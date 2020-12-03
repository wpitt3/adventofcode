package y2019.d09

import y2019.IntcodeProcessor
import java.io.File

fun main() {
    val lines: List<String> = File("src/main/resources/y2019d09.txt").readLines()
    println(runInstructions(lines, 1))
    println(runInstructions(lines, 2))
}

fun runInstructions(lines: List<String>, input: Long): MutableList<Long> {
    var instructions: MutableList<Long> = lines[0].split(",").map({x -> (x).toLong()}).toMutableList()

    var result = mutableListOf<Long>()

    val processor = IntcodeProcessor(instructions)
    processor.callOutput = { it -> result.add(it)}
    processor.callInput = { input }
    processor.run()

    return result
}
