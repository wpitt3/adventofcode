package y2019.d17

import y2019.IntcodeProcessor
import java.io.File
import java.lang.RuntimeException


fun main() {
    val lines: List<String> = File("src/main/resources/y2019d17.txt").readLines()
    var instructions: MutableList<Long> = lines[0].split(",").map({x -> (x).toLong()}).toMutableList()
    val robot = Robot(instructions)
    robot.run()

    var instructions2: MutableList<Long> = lines[0].split(",").map({x -> (x).toLong()}).toMutableList()
    instructions2[0] = 2
    val robot2 = Robot(instructions2)
    robot2.resolvePartTwo()
}

class Robot(instructions: MutableList<Long>) {
    val processor = IntcodeProcessor(instructions)
    var index = 0
    val size = 50
    var grid: MutableList<MutableList<String>> = mutableListOf()
    var currentPos = Pair(0, 0)
    var currentDirection = 0 // north
    val routineA = listOf("L", "4", "L", "4", "L", "6", "R", "10", "L", "6")
    val routineB = listOf("L", "12", "L", "6", "R", "10", "L", "6")
    val routineC = listOf("R", "8", "R", "10", "L", "6")
    val mainRoutine = listOf("A","A","B","C","C","A","C","B","C","B")

    fun run() {
        var output = mutableListOf<Long>()
        processor.callOutput = { it -> output.add(it) }
        processor.callInput = { -1 }
        processor.run()

        grid = output.map{ if (it == 10L) "|" else it.toInt().toChar() }.joinToString("").split("|")
            .map{
                it.split("").filter{it!=""}.toMutableList()
            }.filter{!it.isEmpty()}.toMutableList()

        printPartOne()

        currentPos = Pair(0, 14)

//        printGrid()
        mainRoutine.forEach{
            if (it == "A") {
                runRoutine(routineA)
            } else if (it == "B") {
                runRoutine(routineB)
            } else {
                runRoutine(routineC)
            }
        }
//        printGrid()
    }

    fun resolvePartTwo() {
        var output = mutableListOf<Long>()
        var index = 0
        var input = convertToAscii(mainRoutine) +
                10L + convertToAscii(routineA) +
                10L + convertToAscii(routineB) +
                10L + convertToAscii(routineC) +
                10L + "N"[0].toInt().toLong() + 10L

        processor.callOutput = { it -> output.add(it) }
        processor.callInput = { input[index++] }
        processor.run()
        println(output.last())

    }

    fun convertToAscii(routine: List<String>): List<Long> {
        return routine.joinToString(",").map{it.toChar().toLong()}
    }

    fun runRoutine(routine: List<String>) {
        routine.forEach{runCommand(it)}
    }


    fun runCommand(command: String) {
        if (command == "L") {
            currentDirection = (currentDirection + 3)%4
        } else if (command == "R") {
            currentDirection = (currentDirection + 1)%4
        } else {
            val distance = command.toInt()
            if (currentDirection == 0) {
                (1..distance).forEach{
                    currentPos = Pair(currentPos.first-1, currentPos.second)
                    setTile(currentPos)
                }
            }
            if (currentDirection == 2) {
                (1..distance).forEach{
                    currentPos = Pair(currentPos.first+1, currentPos.second)
                    setTile(currentPos)
                }
            }
            if (currentDirection == 1) {
                (1..distance).forEach{
                    currentPos = Pair(currentPos.first, currentPos.second+1)
                    setTile(currentPos)
                }
            }
            if (currentDirection == 3) {
                (1..distance).forEach{
                    currentPos = Pair(currentPos.first, currentPos.second-1)
                    setTile(currentPos)
                }
            }
        }
    }

    fun setTile(position: Pair<Int, Int>) {
        if (grid[position.first][position.second] == ".") {
            throw RuntimeException("Goofed " + position.first + "," + position.second)
        }
        grid[position.first][position.second] = "_"
    }

    fun printGrid() {
        var toPrint = grid.toMutableList()
        var x = grid[currentPos.first].toMutableList()
        x[currentPos.second] = "D"
        toPrint[currentPos.first] = x
        toPrint.forEach{
            println(it.joinToString(""))
        }
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

    fun printPartOne() {
        println((0..(grid.size-1)).map{ y ->
            (0..(grid[0].size-1)).map { x ->
                if (isAnIntersection(x, y)) x * y else 0
            }.sum()
        }.sum())
    }
}