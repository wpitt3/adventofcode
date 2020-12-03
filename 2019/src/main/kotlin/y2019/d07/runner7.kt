package y2019.d07

import java.io.File

fun main() {
    val lines: List<String> = File("src/main/resources/y2019d07.txt").readLines()

    val permutations: List<List<Int>> = permutations(listOf(0,1,2,3,4), listOf())
    val x = permutations.map{Day07().runAllPhases(lines[0], it)}.sorted().reversed()
    println(x.get(0))

    val permutations2: List<List<Int>> = permutations(listOf(5,6,7,8,9), listOf())
    val x2 = permutations2.map{Day07().runAllPhasesWithLinkedAmps(lines[0], it)}.sorted().reversed()
    println(x2.get(0))
}

fun permutations(remaining: List<Int>, soFar: List<Int> ): List<List<Int>> {
    if(remaining.size == 0) {
        return listOf(soFar)
    }

    val result: MutableList<List<Int>> = mutableListOf()
    for (i in remaining) {
        val x: List<List<Int>> = permutations(remaining.toMutableList() - i, soFar.toMutableList() + i)
        result.addAll(x)
    }
    return result
}