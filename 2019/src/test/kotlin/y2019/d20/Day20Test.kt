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
        assertTrue(result.contains(Portal(9, 2, "AA", true)))
        assertTrue(result.contains(Portal(13, 16, "ZZ", true)))
        assertTrue(result.contains(Portal(2, 8, "BC", true)))
        assertTrue(result.contains(Portal(18, 7, "HI", true)))
        assertTrue(result.contains(Portal(9, 6, "BC", false)))
        assertTrue(result.contains(Portal(11, 12, "FG", false)))
        assertTrue(result.contains(Portal(6, 10, "DE", false)))
        assertTrue(result.contains(Portal(14, 8, "HI", false)))
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
        assertEquals(true, Tile(null, " ").isWall)
    }

    @test
    fun testLineToInternalGrid() {
        val lines = File("src/test/resources/y2019d20a.txt").readLines()

        val portals = Day20().linesToPortals(lines)
        val result = Day20().lineToInternalGrid(lines, portals)

        assertEquals(true, result[2][9].isStart)
        assertEquals(false, result[2][9].isWall)
        assertEquals(false, result[1][9].isStart)
        assertEquals(true, result[1][9].isWall)

        assertEquals(true, result[16][13].isEnd)
        assertEquals(false, result[16][13].isWall)

        assertEquals("BC", result[8][2].portal?.name)
        assertEquals(true, result[8][2].portal?.outside)
        assertEquals("BC", result[6][9].portal?.name)
        assertEquals(false, result[6][9].portal?.outside)
    }

    @test
    fun testGetMatchingPortal() {
        val lines = File("src/test/resources/y2019d20a.txt").readLines()

        val portals = Day20().linesToPortals(lines)

        assertEquals(Pair(9, 2), Day20().getMatchingPortal(portals, "AA"))
        assertEquals(Pair(13, 16), Day20().getMatchingPortal(portals, "ZZ"))
    }

    @test
    fun testFindNextLocationFlat() {
        val lines = File("src/test/resources/y2019d20a.txt").readLines()

        val portals = Day20().linesToPortals(lines)
        val map = Day20().lineToInternalGrid(lines, portals)
        map[2][9].isVisited = true

        assertEquals(mutableListOf(Pair(9, 3)), Day20().findNextLocationsFlat(map, portals, Pair(9, 2)))
        assertEquals(mutableListOf(Pair(9, 4), Pair(10, 3)), Day20().findNextLocationsFlat(map, portals, Pair(9, 3)))
        assertEquals(mutableListOf(Pair(9, 5), Pair(2, 8)), Day20().findNextLocationsFlat(map, portals, Pair(9, 6)))
    }

    @test
    fun testWanderA() {
        val lines = File("src/test/resources/y2019d20a.txt").readLines()

        val portals = Day20().linesToPortals(lines)
        val map = Day20().lineToInternalGrid(lines, portals)

        val result = Day20().wanderFlat(map, portals)

        assertEquals(23, result)
    }

    @test
    fun testWanderB() {
        val lines = File("src/test/resources/y2019d20b.txt").readLines()

        val portals = Day20().linesToPortals(lines)
        val map = Day20().lineToInternalGrid(lines, portals)
        val result = Day20().wanderFlat(map, portals)

        assertEquals(58, result)
    }

    @test
    fun testWanderANotFlat() {
        val lines = File("src/test/resources/y2019d20a.txt").readLines()

        val portals = Day20().linesToPortals(lines)
        val map = Day20().lineToInternalGrid(lines, portals)

        val result = Day20().wander(map, portals)

        assertEquals(26, result)
    }

    @test
    fun testWanderCNotFlat() {
        val lines = File("src/test/resources/y2019d20c.txt").readLines()

        val portals = Day20().linesToPortals(lines)
        val map = Day20().lineToInternalGrid(lines, portals)

        val result = Day20().wander(map, portals)

        assertEquals(396, result)
    }

    @test
    fun testFindNextLocation() {
        val lines = File("src/test/resources/y2019d20a.txt").readLines()

        val portals = Day20().linesToPortals(lines)
        val map = Day20().lineToInternalGrid(lines, portals)
        val threedMap = Day20().cleanCloneMap(map, 5)
        threedMap[0][2][9].isVisited = true

        assertEquals(mutableListOf(Triple(9, 3, 0)), Day20().findNextLocations(threedMap, portals, Triple(9, 2, 0)))
        assertEquals(mutableListOf(Triple(9, 4, 0), Triple(10, 3, 0)), Day20().findNextLocations(threedMap, portals, Triple(9, 3, 0)))
        assertEquals(mutableListOf(Triple(9, 5, 0), Triple(2, 8, 1)), Day20().findNextLocations(threedMap, portals, Triple(9, 6, 0)))
    }

    @test
    fun testCleanCloneMap() {
        val lines = File("src/test/resources/y2019d20a.txt").readLines()

        val portals = Day20().linesToPortals(lines)
        val map = Day20().lineToInternalGrid(lines, portals)

        val result = Day20().cleanCloneMap(map, 5)
        result[0][2][9].isVisited = true

        assertEquals(false, result[1][2][9].isVisited)
    }
}
