package y2019.d03

import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.Test as test

class Day03Test{

    @test
    fun testFindNearestIntersetion() {
        assertEquals(20, Day03().findNearestIntersection(listOf("R10","U10"), listOf("U10","R10")))
        assertEquals(2, Day03().findNearestIntersection(listOf("R1","U1"), listOf("U1","R1")))
        assertEquals(6, Day03().findNearestIntersection(listOf("R10","U6", "L10"), listOf("U10")))
        assertEquals(4, Day03().findNearestIntersection(listOf("R10","U6", "L12", "D2", "R10"), listOf("U10")))
    }

    @test
    fun testFindCombinedSteps() {
        assertEquals(32, Day03().combinedSteps(listOf("R10","U6", "L12", "D2", "R10"), listOf("U10")))
    }

    @test
    fun testMove() {
        val visitedGrid: MutableList<Pair<Int, Int>> = mutableListOf()
        val result = Day03().move(2, Pair(0,0), visitedGrid, ({x -> Pair(x.first+1, x.second)}))

        assertEquals(Pair(2,0), result)
        assertTrue(visitedGrid.contains(Pair(2,0)))
        assertTrue(visitedGrid.contains(Pair(1,0)))
    }

    @test
    fun testcalculateVisited() {
        val wireA : List<Vector> = listOf("R1","U2", "L1", "D1", "U2").map({it -> Vector(it)})
        val visitedGrid = Day03().calculateVisited(wireA)

        assertEquals(7, visitedGrid.size)
        assertTrue(visitedGrid.contains(Pair(1,0)))
        assertTrue(visitedGrid.contains(Pair(1,1)))
        assertTrue(visitedGrid.contains(Pair(1,2)))
        assertTrue(visitedGrid.contains(Pair(0,2)))
        assertTrue(visitedGrid.contains(Pair(0,1)))
        assertTrue(visitedGrid.contains(Pair(0,2)))
        assertTrue(visitedGrid.contains(Pair(0,3)))
    }

    @test
    fun testCreatingVector() {
        val result: Vector = Vector("R10")

        assertEquals(10, result.magnitude)
        assertEquals("R", result.direction)
    }
}
