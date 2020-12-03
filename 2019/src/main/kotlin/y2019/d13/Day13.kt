package y2019.d13

import y2019.IntcodeProcessor
import java.io.File

fun main() {
    val lines: List<String> = File("src/main/resources/y2019d13.txt").readLines()

    part1(lines[0].split(",").map({x -> (x).toLong()}).toMutableList())

    var instructions: MutableList<Long> = lines[0].split(",").map({x -> (x).toLong()}).toMutableList()
    instructions[0] = 2
    val robot = ArcadeCabinet(instructions)
    robot.run()

}

fun part1(instructions: MutableList<Long>) {
    val processor = IntcodeProcessor(instructions)
    var output = mutableListOf<Long>()
    processor.callOutput = { it -> output.add(it) }
    processor.callInput = {-1 }
    processor.run()
    println(output.chunked(3).filter{it[2] == 2L}.count())
}


class ArcadeCabinet(instructions: MutableList<Long>) {
    val processor = IntcodeProcessor(instructions)
    var grid: MutableList<MutableList<Int>> = (1..25).map{(1..45).map{0}.toMutableList()}.toMutableList()
    var score: Long = 0L

    fun run() {
        var output = mutableListOf<Long>()
        processor.callOutput = { it -> output.add(it) }
        processor.callInput = {
            addOutputToGrid(output); calcWhereToMoveFlipper()
        }
        processor.run()
        addOutputToGrid(output);
//        printGrid();
        println(score)

    }

    fun calcWhereToMoveFlipper(): Long {
        var ballX = findBall().first
        var batX = findPaddle()
        if (ballX < batX) {
            return -1
        } else if (ballX > batX) {
            return 1
        } else {
            return 0
        }
    }

    fun addOutputToGrid(output: MutableList<Long>) {
        output.chunked(3).forEach{
            if(it[0] == -1L) {
                score = it[2]
            } else {
                grid[it[1].toInt()][it[0].toInt() ] = it[2].toInt()
            }
        }
        output.clear()
    }

    fun printGrid() {
        grid.forEach { row ->
            val z = row.map{
                if (it==1) {
                    "|"
                } else if (it==2) {
                    "*"
                } else if (it==3) {
                    "-"
                } else if (it==4) {
                    "."
                } else {
                    " "
                }
            }.joinToString("")
            println(z)
        }
    }

    fun findBall(): Pair<Int, Int> {
        (0..24).forEach{ y ->
            (0..44).forEach { x ->
                if (grid[y][x] == 4) {
                    return Pair(x, y)
                }
            }
        }
        return Pair(-1, -1)
    }

    fun findPaddle(): Int {
        (0..44).forEach { x ->
            if (grid[22][x] == 3) {
                return x
            }
        }
        return -1
    }
}