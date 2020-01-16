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
///
//1720187
class Robot(val instructions: List<Long>) {
    var processor = IntcodeProcessor(instructions.toMutableList())
    val size = 3000
    val goal = 99
    var grid: MutableList<MutableList<Int>> = (1..size).map { (1..size).map { -1 }.toMutableList() }.toMutableList()

    fun result(): Int {
        return (0..49).map{y -> (0..49).count{x -> grid[y][x]==1}}.sum()
    }

    fun run() {
        val scanSize = 10

        (0..(scanSize-1)).forEach{y ->
            (0..(scanSize-1)).forEach{ x ->
                addOutputToGrid(getResultAt(x, y), x, y)
            }
        }


        var yIndex = scanSize-1

        var firstIndex = indexOfBeam(yIndex)
        var lastIndex = firstIndex+1
        yIndex++

        while (yIndex < size) {
            while(grid[yIndex][firstIndex] != 1) {
                if(grid[yIndex][firstIndex] == -1) {
                    addOutputToGrid(getResultAt(firstIndex, yIndex), firstIndex, yIndex)
                } else {
                    firstIndex++
                }
            }
            while(grid[yIndex][lastIndex] != 0) {
                if(grid[yIndex][lastIndex] == -1) {
                    addOutputToGrid(getResultAt(lastIndex, yIndex), lastIndex, yIndex)
                } else {
                    lastIndex++
                }
            }
            (firstIndex..(lastIndex-1)).forEach{x -> addOutputToGrid(1, x, yIndex)}

            if( yIndex > goal && grid[yIndex-goal][firstIndex+goal] == 1) {
                println((firstIndex * 10000 + yIndex-goal))
                break
            }

            yIndex++
        }
    }

    fun getResultAt(x: Int, y: Int): Int {
        var inputIndex = 0
        val inputs = listOf(x.toLong(), y.toLong())
        var output: Int = -1
        processor.callOutput = { it ->  output = it.toInt()}
        processor.callInput = { inputs[inputIndex++] }
        processor.reset(instructions.toMutableList())
        processor.run()
        return output
    }

    fun addOutputToGrid(result: Int, x: Int, y: Int) {
        grid[y][x] = result
    }

    fun printGrid() {
        grid.forEach{ row ->
            println( row.map{ if (it==-1) " " else ( if (it==1) "#" else ".") }.joinToString(""))
        }
    }

    fun indexOfBeam(row: Int): Int {
        return (0..(grid[row].size-1)).first { grid[row][it] == 1 }!!
    }
}