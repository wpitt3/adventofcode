package y2019.d15

import y2019.d16.Day16
import kotlin.test.assertEquals
import org.junit.Test as test

class Day16Test{

    @test
    fun row0FollowsPatter010minus1OffetByOne() {
        assertEquals(1, Day16().findMultiplierForIndex(0, 0))
        assertEquals(0, Day16().findMultiplierForIndex(0, 1))
        assertEquals(-1, Day16().findMultiplierForIndex(0, 2))
        assertEquals(0, Day16().findMultiplierForIndex(0, 3))
    }

    @test
    fun row1FollowsPatternButMultipliedInPatchesOf2() {
        assertEquals(0, Day16().findMultiplierForIndex(1, 0))
        assertEquals(1, Day16().findMultiplierForIndex(1, 1))
        assertEquals(1, Day16().findMultiplierForIndex(1, 2))
        assertEquals(0, Day16().findMultiplierForIndex(1, 3))
        assertEquals(0, Day16().findMultiplierForIndex(1, 4))
        assertEquals(-1, Day16().findMultiplierForIndex(1, 5))
    }

    @test
    fun row7FollowsPattern() {
        assertEquals(0, Day16().findMultiplierForIndex(8, 7))
        assertEquals(1, Day16().findMultiplierForIndex(8, 8))
    }

    @test
    fun convertInput() {
        assertEquals(listOf(4,8,2,2,6,1,5,8), Day16().runPattern(listOf(1,2,3,4,5,6,7,8)))
        assertEquals(listOf(3,4,0,4,0,4,3,8), Day16().runPattern(listOf(4,8,2,2,6,1,5,8)))
    }
}
