package y2019.d24

import kotlin.test.assertEquals
import org.junit.Test as test

class Day24Test{

    @test
    fun testEmptyGrid() {
        val result: BugGrid = emptyGrid()

        assertEquals(result.bugs.size, 5)
        assertEquals(result.bugs[0].size, 5)
        assertEquals(result.bRating(), 0)
    }

    @test
    fun testBRatingGridSmallNumber() {
        val grid = Day24().emptyGrid()
        grid[0][1] = true
        grid[1][2] = true

        val result = BugGrid(grid.toList())
        assertEquals(result.toBinaryString(), "0000000000000000010000010")
        assertEquals(result.bRating(), 130)
        assertEquals(result.hashCode(), 130)
        assertEquals(result.toString(),
            ".#...\n" +
            "..#..\n" +
            ".....\n" +
            ".....\n" +
            "....."
        )
    }

    @test
    fun testBRatingGridBigNumber() {
        val grid = Day24().emptyGrid()
        grid[3][0] = true
        grid[4][1] = true

        val result = BugGrid(grid.toList())
        assertEquals(result.toBinaryString(), "0001000001000000000000000")
        assertEquals(result.bRating(), 2129920)
        assertEquals(result.hashCode(), 2129920)
        assertEquals(result.toString(),
            ".....\n" +
            ".....\n" +
            ".....\n" +
            "#....\n" +
            ".#..."
        )
    }

    @test
    fun stepEmpty() {
        val grid = Day24().emptyGrid()

        val result = Day24().step(BugGrid(grid.toList()))

        assertEquals(result.bRating(), 0)
    }

    @test
    fun stepTopLeft() {
        val grid = Day24().emptyGrid()
        grid[0][1] = true

        val result = Day24().step(BugGrid(grid.toList()))
        assertEquals(result.toString(),
            "#.#..\n" +
            ".#...\n" +
            ".....\n" +
            ".....\n" +
            "....."
        )
        assertEquals(result.bRating(), 69)
    }

    @test
    fun stepFromExample() {
        val grid = Day24().emptyGrid()
        grid[0][4] = true
        grid[1][0] = true
        grid[1][3] = true
        grid[2][0] = true
        grid[2][3] = true
        grid[2][4] = true
        grid[3][2] = true
        grid[4][0] = true

        assertEquals(BugGrid(grid.toList()).toString(),
            "....#\n" +
            "#..#.\n" +
            "#..##\n" +
            "..#..\n" +
            "#...."
        )

        val result = Day24().step(BugGrid(grid.toList()))
        assertEquals(result.toString(),
            "#..#.\n" +
            "####.\n" +
            "###.#\n" +
            "##.##\n" +
            ".##.."
        )
    }

    @test
    fun findFirstRepeat() {
        val grid = Day24().emptyGrid()
        grid[0][4] = true
        grid[1][0] = true
        grid[1][3] = true
        grid[2][0] = true
        grid[2][3] = true
        grid[2][4] = true
        grid[3][2] = true
        grid[4][0] = true

        val result = Day24().findFirstRepeat(BugGrid(grid.toList()))

        assertEquals(result.toString(),
            ".....\n" +
            ".....\n" +
            ".....\n" +
            "#....\n" +
            ".#..."
        )
    }

    @test
    fun toMultiDepth() {
        val grid = Day24().emptyGrid()
        grid[2][1] = true

        val result = Day24().toMultiDepth(BugGrid(grid.toList()), 1)

        assertEquals(result.size, 3)
        assertEquals(result[0].bugs[2][1], false)
        assertEquals(result[1].bugs[2][1], true)
        assertEquals(result[2].bugs[2][1], false)
    }

    @test
    fun stepOnMultiDepthRightUpLevel() {
        val grid = Day24().emptyGrid()
        grid[1][4] = true

        val x = Day24().toMultiDepth(BugGrid(grid.toList()), 1)
        val result = Day24().countBugs(Day24().stepMultiDepth(x))

        assertEquals(result, 4)
    }

    @test
    fun stepOnMultiDepth() {
        val grid = Day24().emptyGrid()
        grid[2][1] = true

        val x = Day24().toMultiDepth(BugGrid(grid.toList()),1)
        val result = Day24().stepMultiDepth(x)

        assertEquals(result[0].toString(),
            ".....\n" +
            ".....\n" +
            ".....\n" +
            ".....\n" +
            "....."
        )
        assertEquals(result[1].toString(),
            ".....\n" +
            ".#...\n" +
            "#....\n" +
            ".#...\n" +
            "....."
        )
        assertEquals(result[2].toString(),
            "#....\n" +
            "#....\n" +
            "#....\n" +
            "#....\n" +
            "#...."
        )
    }

    @test
    fun stepMultiDepthExample() {
        val grid = Day24().emptyGrid()
        grid[0][4] = true
        grid[1][0] = true
        grid[1][3] = true
        grid[2][0] = true
        grid[2][3] = true
        grid[2][4] = true
        grid[3][2] = true
        grid[4][0] = true


        var x = Day24().toMultiDepth(BugGrid(grid.toList()), 5)
        (0..9).forEach{
            x = Day24().stepMultiDepth(x)
        }

        val result = Day24().countBugs(x)

        assertEquals(result, 99)
    }

    fun emptyGrid(): BugGrid {
        return BugGrid(listOf(
            listOf(false, false, false, false, false),
            listOf(false, false, false, false, false),
            listOf(false, false, false, false, false),
            listOf(false, false, false, false, false),
            listOf(false, false, false, false, false)
        ))
    }

}
