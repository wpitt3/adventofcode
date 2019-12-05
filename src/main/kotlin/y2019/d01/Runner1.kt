package y2019.d01.y2019.d01

import y2019.d01.Day01
import java.io.File

fun main() {
    val lines: List<String>  = File("src/main/resources/y2019d01.txt").readLines()
    println(Day01().getFuelForAll(lines))
    println(Day01().getHeavyFuelForAll(lines))
}