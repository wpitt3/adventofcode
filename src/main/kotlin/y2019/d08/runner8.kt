package y2019.d08

import java.io.File

fun main() {
    val lines: List<String> = File("src/main/resources/y2019d08.txt").readLines()
    val pixels: List<Int> = lines[0].map({x -> Integer.parseInt(x.toString())}).toList()
    println(Day08().findPixelCount(pixels, 25, 6))
    Day08().createImage(pixels, 25, 6)

}