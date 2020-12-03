package y2019.d10

import java.io.File

fun main() {
    val lines: List<String> = File("src/main/resources/y2019d10.txt").readLines()
    val asteroids = Day10().convertAsteroids(lines)
    println(Day10().findBestMoonBaseCount(asteroids))
    println(Day10().findXPlanetLocation(asteroids, 200))
}