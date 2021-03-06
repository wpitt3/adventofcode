package y2019.d15

import y2019.IntcodeProcessor
import java.io.File

fun main() {
    val lines: List<String> = File("src/main/resources/y2019d15.txt").readLines()
    var instructions: MutableList<Long> = lines[0].split(",").map({x -> (x).toLong()}).toMutableList()
    val robot = Robot(instructions)
    robot.run()
    println(robot.findOxygen())
    println(robot.fillWithOxygen())
}

class Robot(instructions: MutableList<Long>) {
    val processor = IntcodeProcessor(instructions)
    var index = 0
    val size = 50
    var grid: MutableList<MutableList<Int>> = (1..size).map{(1..size).map{-1}.toMutableList()}.toMutableList()
    var currentPos = Pair(size/2, size/2)
    var previousDirection: Int = 1

    fun run() {
        grid[currentPos.first][currentPos.second] = 1
        var output = mutableListOf<Long>()
        processor.callOutput = { it -> output.add(it)}
        processor.callInput = { if (output.isEmpty()) 1 else calculateNextDirect(output)}
        processor.run()
    }

    fun findOxygen():Int {
        var cursors = mutableListOf(Pair(size/2, size/2))
        var counter = 0
        while(cursors.all{grid[it.first][it.second] != 2}) {
            cursors = cursors.map { findNextLocations(it, 2) }.flatten().toMutableList()
            cursors.filter{grid[it.first][it.second] != 2}.forEach{ grid[it.first][it.second] = 3 }
            counter++
        }
        println(cursors.filter{grid[it.first][it.second] == 2})
        return counter
    }

    fun fillWithOxygen():Int {
        var cursors = mutableListOf(Pair(43, 41))
        var counter = 0
        while(cursors.isNotEmpty()) {
//            printGrid()
            cursors = cursors.map { findNextLocations(it, 3) }.flatten().toMutableList()
            cursors.forEach{ grid[it.first][it.second] = 2 }
            counter++
        }
        return counter -1
    }

    fun findNextLocations(location: Pair<Int, Int>, allowed: Int): List<Pair<Int, Int>> {
        var nextPositions: MutableList<Pair<Int, Int>> = mutableListOf()
        if ( grid[location.first+1][location.second] == 1 || grid[location.first+1][location.second] == allowed) {
            nextPositions.add(Pair(location.first+1, location.second))
        }
        if ( grid[location.first-1][location.second] == 1 || grid[location.first-1][location.second] == allowed) {
            nextPositions.add(Pair(location.first-1, location.second))
        }
        if ( grid[location.first][location.second+1] == 1 || grid[location.first][location.second+1] == allowed) {
            nextPositions.add(Pair(location.first, location.second + 1))
        }
        if ( grid[location.first][location.second-1] == 1 || grid[location.first][location.second-1] == allowed) {
            nextPositions.add(Pair(location.first, location.second-1))
        }
        return nextPositions
    }

    fun calculateNextDirect(output: MutableList<Long>): Long {
        val result = output[0]
        output.clear()

        val positionOfResult = getGridInDirect(previousDirection)

        if (result == 0L) {
            grid[positionOfResult.first][positionOfResult.second] = 0
        } else if (result == 1L) {
            grid[positionOfResult.first][positionOfResult.second] = 1
            currentPos = positionOfResult
        } else {
            grid[positionOfResult.first][positionOfResult.second] = 2
            currentPos = positionOfResult
        }
//        printGrid()
//        Thread.sleep(10)

        if(index++ > 10000) {
            grid[size/2][size/2] = 3
            printGrid()
            return -1
        }

        var newDirection = if (result == 0L) turnLeft() else turnRight()
        previousDirection = newDirection
        return newDirection.toLong()
    }

    fun turnLeft(): Int {
        return listOf(-1, 3, 4, 2, 1)[previousDirection]
    }

    fun turnRight(): Int {
        return listOf(-1, 4, 3, 1, 2)[previousDirection]
    }

    fun getGridInDirect(direction: Int): Pair<Int, Int> {
        if (direction == 1) {
            return Pair(currentPos.first - 1, currentPos.second)
        }
        if (direction == 2) {
            return Pair(currentPos.first + 1, currentPos.second)
        }
        if (direction == 3) {
            return Pair(currentPos.first, currentPos.second - 1)
        }
        return Pair(currentPos.first, currentPos.second + 1)
    }

    fun printGrid() {
        println(currentPos)
        grid.forEach { row ->
            val z = row.map{
                if (it==0) {
                    "#"
                } else if (it==1) {
                    "."
                } else if (it==2) {
                    "o"
                } else if (it==3) {
                    "D"
                } else {
                    " "
                }
            }.joinToString("")
            println(z)
        }
    }

}