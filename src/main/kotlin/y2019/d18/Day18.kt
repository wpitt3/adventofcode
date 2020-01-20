import java.io.File

fun main (){
    val map : List<List<String>> = File("src/main/resources/y2019d18.txt").readLines().map{it.split("")}




}

class Day18() {
    fun findNextLocations(x: Int, y: Int, grid: List<List<String>>, walker: Walker): List<Walker> {
        var nextWalkers: MutableList<Walker> = mutableListOf()
        if ( grid[y+1][x] != "#") {
//            nextWalkers.add(Pair(location.first+1, location.second))
        }
        if ( grid[y-1][x] != "#") {
//            nextWalkers.add(Pair(location.first-1, location.second))
        }
        if ( grid[y][x+1] != "#") {
//            nextWalkers.add(Pair(location.first, location.second + 1))
        }
        if ( grid[y][x-1] != "#") {
//            nextWalkers.add(Pair(location.first, location.second-1))
        }
        return nextWalkers
    }

}

data class Walker(val x: Int, val y: Int, val passed: List<String>)


