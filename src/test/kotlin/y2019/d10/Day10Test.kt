import Day10
import kotlin.test.assertEquals
import org.junit.Test as test

class Day10Test{

    @test
    fun testHcf() {
        assertEquals(Day10().hcf(2,3), 1)
        assertEquals(Day10().hcf(4,2), 2)
        assertEquals(Day10().hcf(2,4), 2)
        assertEquals(Day10().hcf(6,3), 3)
        assertEquals(Day10().hcf(24,12), 12)
        assertEquals(Day10().hcf(-50,10), 10)
        assertEquals(Day10().hcf(6,-3), 3)
    }

    @test
    fun testFindAsteroids() {
        assertEquals(Day10().convertAsteroids(listOf(".#")), listOf(Pair(1,0)))
        assertEquals(Day10().convertAsteroids(listOf(".#.", "..#","#..")), listOf(Pair(1,0), Pair(2,1), Pair(0,2)))
    }

    @test
    fun countVisibleAsteroids() {
        val asteroids: List<Pair<Int, Int>> = Day10().convertAsteroids(listOf(".#..#", ".....", "#####","....#","...##"))
        assertEquals(Day10().countVisibleAsteroids(asteroids, Pair(3, 4)), 8)
        assertEquals(Day10().countVisibleAsteroids(asteroids, Pair(1, 0)), 7)
        assertEquals(Day10().countVisibleAsteroids(asteroids, Pair(4, 0)), 7)
        assertEquals(Day10().countVisibleAsteroids(asteroids, Pair(0, 2)), 6)
        assertEquals(Day10().countVisibleAsteroids(asteroids, Pair(1, 2)), 7)
        assertEquals(Day10().countVisibleAsteroids(asteroids, Pair(2, 2)), 7)
        assertEquals(Day10().countVisibleAsteroids(asteroids, Pair(3, 2)), 7)
        assertEquals(Day10().countVisibleAsteroids(asteroids, Pair(4, 2)), 5)
        assertEquals(Day10().countVisibleAsteroids(asteroids, Pair(4, 3)), 7)
        assertEquals(Day10().countVisibleAsteroids(asteroids, Pair(4, 4)), 7)
    }
}
