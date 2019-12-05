package y2019.d03

import java.io.File

fun main() {
    val lines: List<String>  = File("src/main/resources/y2019d03.txt").readLines()
    println(Day03().findNearestIntersection(lines[0].split(","), lines[1].split(",")))
    println(Day03().combinedSteps(lines[0].split(","), lines[1].split(",")))

    println(5357)
    println(101956)
}