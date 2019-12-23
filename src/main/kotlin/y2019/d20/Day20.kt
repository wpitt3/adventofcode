package y2019.d20

fun main():String {
    return ""
}
class Day20() {

    fun wander(portals: List<Portal>) {

    }

    fun lineToInternalGrid(lines: List<String>, portals: List<Portal>): List<List<Tile>> {
        val gridSquares: List<List<String>> = lines.map{it.split("").filter{!it.isEmpty()}}

        return listOf(listOf())
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

        portals.addAll(findPortalsX(gridSquares, yInternalRange[0]-3, yInternalRange[0], xInternalRange, false))
        portals.addAll(findPortalsX(gridSquares, yMaxInterIndex-1, yMaxInterIndex-1, xInternalRange, false))

        portals.addAll(findPortalsX(gridSquares, 0, 0, (2..gridWidth).toList(), true))
        portals.addAll(findPortalsX(gridSquares, gridHeight-4, gridHeight-1, (2..gridWidth).toList(), true))

        portals.addAll(findPortalsY(gridSquares, xInternalRange[0]-3, xInternalRange[0], yInternalRange, false))
        portals.addAll(findPortalsY(gridSquares, xMaxInterIndex-1, xMaxInterIndex-1, yInternalRange, false))

        portals.addAll(findPortalsY(gridSquares, 0, 0, (2..gridHeight).toList(), true))
        portals.addAll(findPortalsY(gridSquares, gridWidth-4, gridWidth-1, (2..gridHeight).toList(), true))
        return portals
    }

    fun findPortalsX(gridSquares: List<List<String>>, yIndex: Int, yLabelIndex: Int, xInternalRange: List<Int>, outside: Boolean): List<Portal> {
        return xInternalRange.filter{!gridSquares[yLabelIndex][it].isBlank() && !gridSquares[yLabelIndex+1][it].isBlank()}
            .map {Portal(it-2, yIndex, gridSquares[yLabelIndex][it]+gridSquares[yLabelIndex+1][it], outside)}
    }

    fun findPortalsY(gridSquares: List<List<String>>, xIndex: Int, xLabelIndex: Int, yInternalRange: List<Int>, outside: Boolean): List<Portal> {
        return yInternalRange.filter{!gridSquares[it][xLabelIndex].isBlank() && !gridSquares[it][xLabelIndex+1].isBlank()}
            .map {Portal(xIndex, it-2, gridSquares[it][xLabelIndex]+gridSquares[it][xLabelIndex+1], outside)}
    }
}

class Tile(val portal: Portal?, label: String) {
    val isStart: Boolean = portal?.name == "AA"
    val isEnd: Boolean = portal?.name == "ZZ"
    val isWall: Boolean = label == "#"
    var isVisited: Boolean = false

    fun isPortal(): Boolean {
        return portal != null
    }
}

data class Portal(val x: Int, val y: Int, val name: String, val outside: Boolean) {
}