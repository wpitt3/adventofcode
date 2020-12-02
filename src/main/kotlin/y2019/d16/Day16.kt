package y2019.d16

import java.io.File
import kotlin.math.abs
import kotlin.math.min

fun main() {
    val lines: List<String> = File("src/main/resources/y2019d16.txt").readLines()
    var numbers = lines[0].split("").filter{it != ""}.map{ it.toLong()}
    var longNumbers = (1..10000).map{ numbers }.flatten()

    println(runPattern100Times(numbers).take(8).joinToString(""))
    val offset = numbers.take(7).joinToString("").toInt()
    println(runPattern100Times(longNumbers).drop(offset).take(8).joinToString(""))

}

fun runPattern100Times(input: List<Long>): List<Long> {
    val start: Long = System.currentTimeMillis()
    var numbers = input
    repeat(100){
        numbers = runPattern(numbers)
        println(it.toString() + ": " + (System.currentTimeMillis() - start))
    }
    return numbers
}

fun runPattern(input: List<Long>): List<Long> {
    val cumulativeTotal = calcCumulativeTotal(input)
    val result = mutableListOf<Long>()
    for (row in 0..(input.size-1)) {
        val groupSize = row+1
        var index = row
        var total = 0L
        var multiplier = 1
        while ( index < input.size) {
            total += multiplier * calcTotalForRange(cumulativeTotal, index, min(index+groupSize-1, input.size-1))
            multiplier = multiplier * -1
            index = index + groupSize * 2
        }
        result.add(abs(total)%10)
    }
    return result
}

fun calcCumulativeTotal(input: List<Long>): List<Long> {
    val cumulativeTotal = mutableListOf(input[0])
    for (i in 1..(input.size-1)) {
        cumulativeTotal.add((cumulativeTotal[i-1] + input[i]))
    }
    return cumulativeTotal
}

fun calcTotalForRange(cumulativeTotal: List<Long>, from: Int, to: Int): Long {
    if  (from == 0) {
        return cumulativeTotal[to]
    } else {
        return cumulativeTotal[to] - cumulativeTotal[from-1]
    }
}