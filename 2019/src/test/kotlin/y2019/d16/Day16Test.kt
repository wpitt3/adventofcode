package y2019.d15

import y2019.d16.runPattern
import kotlin.test.assertEquals
import org.junit.Test as test

class Day16Test{


    @test
    fun runPattern2() {
        assertEquals(listOf<Long>(4,8,2,2,6,1,5,8), runPattern(listOf(1,2,3,4,5,6,7,8)))
        assertEquals(listOf<Long>(3,4,0,4,0,4,3,8), runPattern(listOf(4,8,2,2,6,1,5,8)))
        assertEquals(listOf<Long>(0,3,4,1,5,5,1,8), runPattern(listOf(3,4,0,4,0,4,3,8)))
        assertEquals(listOf<Long>(0,1,0,2,9,4,9,8), runPattern(listOf(0,3,4,1,5,5,1,8)))
    }
}
