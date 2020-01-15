package y2019.d19

import y2019.IntcodeProcessor
import java.io.File

fun main() {
    val lines: List<String> = File("src/main/resources/y2019d19.txt").readLines()
    var instructions: MutableList<Long> = lines[0].split(",").map({x -> (x).toLong()}).toMutableList()
    val robot = Robot(instructions)
    robot.run()
    println(robot.result())

}


class Robot(val instructions: List<Long>) {
    var processor = IntcodeProcessor(instructions.toMutableList())
    val size = 50
    var grid: MutableList<MutableList<Long>> = (1..size).map { (1..size).map { 0L }.toMutableList() }.toMutableList()

    fun result(): Int {
        return grid.map{row -> row.count{it == 1L}}.sum()
    }

    fun run() {
        val scanSize = size

        val inputs: List<Long> = (0..(scanSize-1)).map{ y -> (0..(scanSize-1)).map{ x -> listOf(y.toLong(), x.toLong()) }}.flatten().flatten()

        var inputIndex = 0

        while(inputs.size > inputIndex) {
            processor = IntcodeProcessor(instructions.toMutableList())
            processor.callOutput = { it -> addOutputToGrid(it, inputs, inputIndex) }
            processor.callInput = { inputs[inputIndex++] }
            processor.run()

        }

//        printGrid()
    }

    fun addOutputToGrid(result: Long, inputs: List<Long>, inputIndex: Int) {

        val x = inputs[inputIndex-2]
        val y = inputs[inputIndex-1]

        grid[y.toInt()][x.toInt()] = result
    }

    fun printGrid() {
        grid.forEach{ row ->
            println( row.map{ if (it==1L) "#" else "." }.joinToString(""))
        }
    }
}