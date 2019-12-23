package y2019.d20

import java.io.File
import kotlin.test.assertTrue
import kotlin.test.assertEquals
import org.junit.Test as test

class Day20Test{

    @test
    fun testReadLines() {
        val lines = File("src/test/resources/y2019d20a.txt").readLines()

        val result = Day20().linesToPortals(lines)
        assertTrue(result.contains(Portal(7, 0, "AA", true)))
        assertTrue(result.contains(Portal(11, 14, "ZZ", true)))
        assertTrue(result.contains(Portal(0, 6, "BC", true)))
        assertTrue(result.contains(Portal(16, 5, "HI", true)))
        assertTrue(result.contains(Portal(7, 4, "BC", false)))
        assertTrue(result.contains(Portal(9, 10, "FG", false)))
        assertTrue(result.contains(Portal(4, 8, "DE", false)))
        assertTrue(result.contains(Portal(12, 6, "HI", false)))
        assertEquals(10, result.size)
    }

    @test
    fun testTile() {
        assertEquals(Portal(0,0,"", true), Tile(Portal(0,0,"", true), ".").portal)
        assertEquals(true, Tile(Portal(0,0,"", true), ".").isPortal())
        assertEquals(false, Tile(null, ".").isPortal())
        assertEquals(false, Tile(Portal(0,0,"", true), ".").isStart)
        assertEquals(true, Tile(Portal(0,0,"AA", true), ".").isStart)
        assertEquals(false, Tile(Portal(0,0,"", true), ".").isEnd)
        assertEquals(true, Tile(Portal(0,0,"ZZ", true), ".").isEnd)
        assertEquals(false, Tile(Portal(0,0,"", true), ".").isVisited)
        assertEquals(true, Tile(null, "#").isWall)
        assertEquals(false, Tile(null, ".").isWall)
    }

    @test
    fun testLineToInternalGrid() {
        val lines = File("src/test/resources/y2019d20a.txt").readLines()

        val portals = Day20().linesToPortals(lines)
        val result = Day20().lineToInternalGrid(lines, portals)

        println(result)

        assertEquals(false, Tile(null, ".").isWall)
    }
}
