package y2019.d12

import kotlin.test.assertEquals
import org.junit.Test as test

class Day12Test{

    @test
    fun test() {
        val xMovements = listOf(Movement(-1, 0), Movement(2, 0), Movement(4, 0), Movement(3, 0))
        assertEquals(Movement(2,3), Day12().calcNewMovements(xMovements[0], Day12().listWithout(xMovements, 0)))
        assertEquals(Movement(3,1), Day12().calcNewMovements(xMovements[1], Day12().listWithout(xMovements, 1)))
        assertEquals(Movement(1,-3), Day12().calcNewMovements(xMovements[2], Day12().listWithout(xMovements, 2)))
        assertEquals(Movement(2,-1), Day12().calcNewMovements(xMovements[3], Day12().listWithout(xMovements, 3)))
    }

    @test
    fun testListWithout() {
        val list = listOf(4,5,6,7)
        assertEquals(listOf(5,6,7), Day12().listWithout(list, 0))
        assertEquals(listOf(4,6,7), Day12().listWithout(list, 1))
        assertEquals(listOf(4,5,7), Day12().listWithout(list, 2))
        assertEquals(listOf(4,5,6), Day12().listWithout(list, 3))
    }

    @test
    fun testCalcEnergy() {
        val xMovements = listOf(Movement(2, -3), Movement(1, -1), Movement(3, 3), Movement(2, 1))
        val yMovements = listOf(Movement(1, -2), Movement(-8, 1), Movement(-6, 2), Movement(0, -1))
        val zMovements = listOf(Movement(-3, 1), Movement(0, 3), Movement(1, -3), Movement(4, -1))

        assertEquals(179, Day12().calcEnergy(listOf(xMovements, yMovements, zMovements)))
    }

    @test
    fun testPartA() {
        val xMovements = listOf(-19, -9, -4, 1).map{Movement(it, 0)}
        val yMovements = listOf(-4, 8, 5, 9).map{Movement(it, 0)}
        val zMovements = listOf(2, -16, -11, -13).map{Movement(it, 0)}

        assertEquals(8287, Day12().partA(xMovements, yMovements, zMovements))
    }

    @test
    fun testFindLoop() {
        val xMovements = listOf(-1, 2, 4, 3).map{Movement(it, 0)}
        val yMovements = listOf(0, -10, -8, 5).map{Movement(it, 0)}
        val zMovements = listOf(2, -7, 8, -1).map{Movement(it, 0)}
        assertEquals(18, Day12().findLoop(xMovements))
        assertEquals(28, Day12().findLoop(yMovements))
        assertEquals(44, Day12().findLoop(zMovements))
    }

    @test
    fun testPartB() {
        val xMovements = listOf(-1, 2, 4, 3).map{Movement(it, 0)}
        val yMovements = listOf(0, -10, -8, 5).map{Movement(it, 0)}
        val zMovements = listOf(2, -7, 8, -1).map{Movement(it, 0)}
        assertEquals(2772, Day12().partB(xMovements, yMovements, zMovements))
    }

    @test
    fun testHcf() {
        assertEquals(3, Day12().hcf(Day12().hcf(3,6),9))
    }

}
