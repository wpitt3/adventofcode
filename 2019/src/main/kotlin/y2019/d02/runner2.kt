package y2019.d02

import java.io.File

fun main() {
    val lines: List<String>  = File("src/main/resources/y2019d02.txt").readLines()
    println(Day02().runWholeSystem(lines[0], 12, 2))
    for(noun in 0..99) {
        for(verb in 0..99) {
            if (Day02().runWholeSystem(lines[0], noun, verb) == 19690720) {
                println (noun * 100 + verb)
            }
        }
    }
}