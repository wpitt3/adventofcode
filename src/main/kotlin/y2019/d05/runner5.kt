package y2019.d05

import java.io.File

fun main() {
    val lines: List<String>  = File("src/main/resources/y2019d05.txt").readLines()
    println(Day05().runWholeSystem(lines[0], 1))
    println(Day05().runWholeSystem(lines[0], 5))
}