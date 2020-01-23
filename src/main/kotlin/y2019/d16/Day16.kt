package y2019.d16

import java.io.File
import kotlin.math.abs

fun main() {
    val lines: List<String> = File("src/main/resources/y2019d16.txt").readLines()
    var numbers = lines[0].split("").filter{it != ""}.map{ it.toInt()}
    val start: Long = System.currentTimeMillis()
    println(runPattern100Times(numbers).take(8).joinToString(""))
    println(82435530)
    println(System.currentTimeMillis() - start)
}

fun runPattern100Times(input: List<Int>): List<Int> {
    var numbers = input
    repeat(100){
        numbers = runPattern(numbers)
    }
    return numbers
}

fun runPattern(input: List<Int>): List<Int> {
    return (0..(input.size-1)).map { y ->
        val z:Int = (0..(input.size-1)).map { x ->
            findMultiplierForIndex(y, x) * input[x]
        }.sum()
        abs(z) % 10
    }
}

fun findMultiplierForIndex(row: Int, column: Int): Int {
    val step = (row + 1) * 4
    val patternIndex = ((column + 1) % step) / (row + 1)
    if (patternIndex == 1) {
        return 1
    } else if (patternIndex == 3) {
        return -1
    } else {
        return 0
    }
}

