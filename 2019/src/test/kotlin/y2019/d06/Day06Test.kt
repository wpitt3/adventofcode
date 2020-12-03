package y2019.d06

import kotlin.test.assertEquals
import org.junit.Test as test

class Day06Test{

    @test
    fun testCountOrbits() {
        assertEquals(1, Day06().countOrbits(listOf("A)B")))
        assertEquals(2, Day06().countOrbits(listOf("A)B","A)C")))
        assertEquals(3, Day06().countOrbits(listOf("A)B","B)C")))
        assertEquals(8, Day06().countOrbits(listOf("COM)B", "B)C", "B)G", "G)H")))
        assertEquals(42, Day06().countOrbits(listOf("COM)B", "B)C", "C)D", "D)E", "E)F", "B)G", "G)H", "D)I", "E)J", "J)K", "K)L")))
    }

    @test
    fun testPathToSat() {
        val orbits: Map<String, String> = mapOf(Pair("B", "A"), Pair("YOU", "B"))
        assertEquals(mutableListOf("B", "A"), Day06().getWholePathForSat("YOU", orbits))
    }

    @test
    fun countOrbitalTransfers() {
        assertEquals(1, Day06().orbitTransfers(listOf("A)B", "A)SAN", "B)YOU")))
        assertEquals(2, Day06().orbitTransfers(listOf("A)B", "A)C", "C)SAN", "B)YOU")))
        assertEquals(2, Day06().orbitTransfers(listOf("A)B", "B)C", "A)SAN", "C)YOU")))
        assertEquals(4, Day06().orbitTransfers(listOf("COM)B", "B)C", "C)D", "D)E", "E)F", "B)G", "G)H", "D)I", "E)J", "J)K", "K)L", "K)YOU", "I)SAN")))
    }
}
