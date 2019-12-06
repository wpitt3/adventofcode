package y2019.d06

import java.io.File

fun main() {
    val lines: List<String>  = File("src/main/resources/y2019d06.txt").readLines()
    println(Day06().countOrbits(lines))
    println(Day06().orbitTransfers(lines))
}