package y2019.d20

import java.io.File

fun main() {
    val lines = File("src/main/resources/y2019d20.txt").readLines()
    val portals = Day20().linesToPortals(lines)
    val map = Day20().lineToInternalGrid(lines, portals)
    println (Day20().wanderFlat(map, portals))
    println (Day20().wander(map, portals))
}

class Day20() {
    fun wander(map: List<List<Tile>>, portals: List<Portal>): Int {
        val startPosPair = getMatchingPortal(portals, "AA")
        val startPos = Triple(startPosPair.first, startPosPair.second, 0)
        val threeDMap = cleanCloneMap(map, 200)
        threeDMap[startPos.third][startPos.second][startPos.first].isVisited = true

        var counter = 0
        var cursors = mutableListOf(startPos)
        while(cursors.all{!(threeDMap[it.third][it.second][it.first].isEnd && it.third == 0) }) {
            cursors = cursors.map { findNextLocations(threeDMap, portals, it) }.flatten().toMutableList()
            cursors.forEach{ threeDMap[it.third][it.second][it.first].isVisited = true }
            counter++
        }
        return counter
    }

    fun findNextLocations(map: List<List<List<Tile>>>, portals: List<Portal>, currentPos: Triple<Int, Int, Int>): MutableList<Triple<Int, Int, Int>> {
        var nextPositions: MutableList<Triple<Int, Int, Int>> = mutableListOf()
        if ( isLocationAvailable(map[currentPos.third], currentPos.first, currentPos.second-1)) {
            nextPositions.add(Triple(currentPos.first, currentPos.second-1, currentPos.third))
        }
        if ( isLocationAvailable(map[currentPos.third], currentPos.first, currentPos.second+1)) {
            nextPositions.add(Triple(currentPos.first, currentPos.second+1, currentPos.third))
        }
        if ( isLocationAvailable(map[currentPos.third], currentPos.first-1, currentPos.second)) {
            nextPositions.add(Triple(currentPos.first-1, currentPos.second, currentPos.third))
        }
        if ( isLocationAvailable(map[currentPos.third], currentPos.first+1, currentPos.second)) {
            nextPositions.add(Triple(currentPos.first+1, currentPos.second, currentPos.third))
        }
        if ( map[currentPos.third][currentPos.second][currentPos.first].isPortal()
            && !map[currentPos.third][currentPos.second][currentPos.first].isStart
            && !map[currentPos.third][currentPos.second][currentPos.first].isEnd) {
            val portal = map[currentPos.third][currentPos.second][currentPos.first].portal !!
            val otherSide = portals.filter{it.name == portal.name && it.outside != portal.outside}.first() !!
            val modifier = if (portal.outside) -1 else 1
            if (currentPos.third + modifier >= 0 && !map[currentPos.third + modifier][otherSide.y][otherSide.x].isVisited) {
                nextPositions.add(Triple(otherSide.x, otherSide.y, currentPos.third + modifier))
            }
        }
        return nextPositions
    }

    fun cleanCloneMap(map: List<List<Tile>>, depth: Int): List<List<List<Tile>>> {
        return (1..(depth)).map{ map.map{a -> a.map{Tile(it.portal, it.label)}}}
    }

    fun wanderFlat(map: List<List<Tile>>, portals: List<Portal>): Int {
        val startPos = getMatchingPortal(portals, "AA")

        map[startPos.second][startPos.first].isVisited = true

        var counter = 0
        var cursors = mutableListOf(startPos)
        while(cursors.all{!map[it.second][it.first].isEnd}) {
            cursors = cursors.map { findNextLocationsFlat(map, portals, it) }.flatten().toMutableList()

            cursors.forEach{ map[it.second][it.first].isVisited = true }

            counter++
        }

        return counter
    }

    fun findNextLocationsFlat(map: List<List<Tile>>, portals: List<Portal>, currentPos: Pair<Int, Int>): MutableList<Pair<Int, Int>> {
        var nextPositions: MutableList<Pair<Int, Int>> = mutableListOf()
        if ( isLocationAvailable(map, currentPos.first, currentPos.second-1)) {
            nextPositions.add(Pair(currentPos.first, currentPos.second-1))
        }
        if ( isLocationAvailable(map, currentPos.first, currentPos.second+1)) {
            nextPositions.add(Pair(currentPos.first, currentPos.second+1))
        }
        if ( isLocationAvailable(map, currentPos.first-1, currentPos.second)) {
            nextPositions.add(Pair(currentPos.first-1, currentPos.second))
        }
        if ( isLocationAvailable(map, currentPos.first+1, currentPos.second)) {
            nextPositions.add(Pair(currentPos.first+1, currentPos.second))
        }
        if ( map[currentPos.second][currentPos.first].isPortal() && !map[currentPos.second][currentPos.first].isStart && !map[currentPos.second][currentPos.first].isEnd) {
            val portal = map[currentPos.second][currentPos.first].portal !!
            val otherSide = portals.filter{it.name == portal.name && it.outside != portal.outside}.first() !!
            nextPositions.add(Pair(otherSide.x, otherSide.y))
        }
        return nextPositions
    }

    fun isLocationAvailable(map: List<List<Tile>>, x: Int, y: Int): Boolean {
        return !map[y][x].isWall && !map[y][x].isVisited
    }

    fun getMatchingPortal(portals: List<Portal>, name :String): Pair<Int, Int> {
        return portals.filter{it.name == name}.map{Pair(it.x, it.y)}.first() !!
    }

    fun lineToInternalGrid(lines: List<String>, portals: List<Portal>): List<List<Tile>> {
        val gridSquares: List<List<String>> = lines.map{it.split("").filter{!it.isEmpty()}}

        return (0..(gridSquares.size - 1)).map { y ->
            (0..(gridSquares[0].size-1)).map { x ->
                val portal: Portal? = portals.filter{it.x == x && it.y == y}.firstOrNull()
                Tile(portal, gridSquares[y][x])
            }
        }
    }

    fun linesToPortals(lines: List<String>): List<Portal> {
        val gridSquares: List<List<String>> = lines.map{it.split("").filter{!it.isEmpty()}}
        val portals: MutableList<Portal> = mutableListOf()

        val gridHeight = gridSquares.size-1
        val gridWidth = gridSquares[0].size-1
        val xInternalRange = (2..(gridWidth-2)).filter{ val a = gridSquares[gridHeight/2][it];  a != "." && a != "#" }
        val yInternalRange = (2..(gridHeight-2)).filter{ val a = gridSquares[it][gridWidth/2];  a != "." && a != "#" }
        val xMaxInterIndex = xInternalRange[xInternalRange.size-1]
        val yMaxInterIndex = yInternalRange[yInternalRange.size-1]

        portals.addAll(findPortalsX(gridSquares, yInternalRange[0]-1, yInternalRange[0], xInternalRange, false))
        portals.addAll(findPortalsX(gridSquares, yMaxInterIndex+1, yMaxInterIndex-1, xInternalRange, false))

        portals.addAll(findPortalsX(gridSquares, 2, 0, (2..gridWidth).toList(), true))
        portals.addAll(findPortalsX(gridSquares, gridHeight-2, gridHeight-1, (2..gridWidth).toList(), true))

        portals.addAll(findPortalsY(gridSquares, xInternalRange[0]-1, xInternalRange[0], yInternalRange, false))
        portals.addAll(findPortalsY(gridSquares, xMaxInterIndex+1, xMaxInterIndex-1, yInternalRange, false))

        portals.addAll(findPortalsY(gridSquares, 2, 0, (2..gridHeight).toList(), true))
        portals.addAll(findPortalsY(gridSquares, gridWidth-2, gridWidth-1, (2..gridHeight).toList(), true))
        return portals
    }

    fun findPortalsX(gridSquares: List<List<String>>, yIndex: Int, yLabelIndex: Int, xInternalRange: List<Int>, outside: Boolean): List<Portal> {
        return xInternalRange.filter{!gridSquares[yLabelIndex][it].isBlank() && !gridSquares[yLabelIndex+1][it].isBlank()}
            .map {Portal(it, yIndex, gridSquares[yLabelIndex][it]+gridSquares[yLabelIndex+1][it], outside)}
    }

    fun findPortalsY(gridSquares: List<List<String>>, xIndex: Int, xLabelIndex: Int, yInternalRange: List<Int>, outside: Boolean): List<Portal> {
        return yInternalRange.filter{!gridSquares[it][xLabelIndex].isBlank() && !gridSquares[it][xLabelIndex+1].isBlank()}
            .map {Portal(xIndex, it, gridSquares[it][xLabelIndex]+gridSquares[it][xLabelIndex+1], outside)}
    }
}

data class Tile(val portal: Portal?, val label: String) {
    val isStart: Boolean = portal?.name == "AA"
    val isEnd: Boolean = portal?.name == "ZZ"
    val isWall: Boolean = label != "."
    var isVisited: Boolean = false

    fun isPortal(): Boolean {
        return portal != null
    }

    override fun toString(): String {
        if (isWall) {
            return "."
        }
        if (isStart) {
            return "s"
        }
        if (isEnd) {
            return "e"
        }
        if (isVisited) {
            return "v"
        }
        if (isPortal()) {
            return "p"
        }
        return " "
    }
}

data class Portal(val x: Int, val y: Int, val name: String, val outside: Boolean) {
}