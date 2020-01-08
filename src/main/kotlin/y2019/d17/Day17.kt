package y2019.d17

import y2019.IntcodeProcessor
import java.io.File


fun main() {
    val lines: List<String> = File("src/main/resources/y2019d17.txt").readLines()
    var instructions: MutableList<Long> = lines[0].split(",").map({x -> (x).toLong()}).toMutableList()
    val robot = Robot(instructions)
    robot.run()
//    println(robot.findOxygen())
//    println(robot.fillWithOxygen())
}

class Robot(instructions: MutableList<Long>) {
    val processor = IntcodeProcessor(instructions)
    var index = 0
    val size = 50
    var grid: MutableList<MutableList<String>> = mutableListOf()
    var currentPos = Pair(size / 2, size / 2)

    fun run() {
        var output = mutableListOf<Long>()
        processor.callOutput = { it -> output.add(it) }
        processor.callInput = { -1 }
        processor.run()

        grid = output.map{ if (it == 10L) "|" else it.toInt().toChar() }.joinToString("").split("|")
            .map{
                it.split("").filter{it!=""}.toMutableList()
            }.filter{!it.isEmpty()}.toMutableList()

//        grid.forEach{
//            println(it.joinToString(""))
//        }

        println((0..(grid.size-1)).map{ y ->
            (0..(grid[0].size-1)).map { x ->
                if (isAnIntersection(x, y)) x * y else 0
            }.sum()
        }.sum())
    }

    fun isAnIntersection(x: Int, y: Int): Boolean {
        if (grid[y][x] != "#") {
            return false
        }
        var count = 0
        if (x != 0 && grid[y][x-1] == "#") {
            count += 1
        }
        if (x != grid[0].size-1 && grid[y][x+1] == "#") {
            count += 1
        }
        if (y != 0 && grid[y-1][x] == "#") {
            count += 1
        }
        if (y != grid.size-1 && grid[y+1][x] == "#") {
            count += 1
        }

        return count > 2
    }
}