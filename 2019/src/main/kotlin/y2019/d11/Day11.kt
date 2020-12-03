package y2019.d11

import y2019.IntcodeProcessor
import java.io.File

fun main() {
    val lines: List<String> = File("src/main/resources/y2019d11.txt").readLines()
    var instructions: MutableList<Long> = lines[0].split(",").map({x -> (x).toLong()}).toMutableList()
    val robot = Robot(instructions)
    robot.run()
    println(robot.result())

    var instructions2: MutableList<Long> = lines[0].split(",").map({x -> (x).toLong()}).toMutableList()
    val robot2 = Robot(instructions2)
    robot2.grid[robot2.size/2][robot2.size/2] = 1L
    robot2.run()
    robot2.grid.forEach { y ->
        println(y.map{if (it == 1L) '#' else '.'}.joinToString(""))
    }
}


class Robot(instructions: MutableList<Long>) {
    val processor = IntcodeProcessor(instructions)
    val size = 130
    var grid: MutableList<MutableList<Long>> = (1..size).map{(1..size).map{0L}.toMutableList()}.toMutableList()
    var x :Int = size/2
    var y :Int = size/2
    var direction = 0 // north (east is 1)
    var paintedTiles: MutableSet<Pair<Int,Int>> = mutableSetOf()

    fun result():Int {
        return paintedTiles.size
    }

    fun run() {
        var output = mutableListOf<Long>()
        processor.callOutput = { it -> output.add(it)}
        processor.callInput = { if (output.isEmpty()) grid[y][x] else calculateNextLocation(output)}
        processor.run()
        if(!output.isEmpty()) {
            paintedTiles.add(Pair(size/2,size/2))
            calculateNextLocation(output, false)
        }
    }

    fun calculateNextLocation(output: MutableList<Long>): Long {
        return calculateNextLocation(output, true)
    }

    fun calculateNextLocation(output: MutableList<Long>, painted: Boolean): Long {
        val paint = output[0]
        val turn = output[1]
        output.clear()
        grid[y][x] = paint
        if (painted) {
            paintedTiles.add(Pair(y,x))
        }
        direction = (direction + (if (turn == 1L) 1 else 3)) % 4
        if (direction == 0) {
            y -= 1
        } else if (direction == 2) {
            y += 1
        } else if (direction == 1) {
            x += 1
        } else {
            x -= 1
        }

        return grid[y][x]
    }

}