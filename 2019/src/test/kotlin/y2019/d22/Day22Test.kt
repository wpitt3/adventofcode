import java.math.BigInteger
import kotlin.test.assertEquals
import org.junit.Test as test


class Day22Test {
    @test
    fun test() {
        val deck = (0..9).map { it }.toList()

        assertEquals(perform(deck, "deal into new stack"), listOf(9, 8, 7, 6, 5, 4, 3, 2, 1, 0))
        assertEquals(perform(deck, "cut 3"), listOf(3, 4, 5, 6, 7, 8, 9, 0, 1, 2))
        assertEquals(perform(deck, "cut -4"), listOf(6, 7, 8, 9, 0, 1, 2, 3, 4, 5))
        assertEquals(perform(deck, "deal with increment 3"), listOf(0, 7, 4, 1, 8, 5, 2, 9, 6, 3))
    }

    @test
    fun test2() {
        var deck = (0..10006).map { it }.toList()

        val x = 2019.toBigInteger()
        val y = 10007.toBigInteger()

        assertEquals(perform(deck, "deal into new stack")[2019].toBigInteger(), perform2(x, y, "deal into new stack"))
        assertEquals(perform(deck, "cut 3")[2019].toBigInteger(), perform2(x, y, "cut 3"))
        assertEquals(perform(deck, "cut -4")[2019].toBigInteger(), perform2(x, y, "cut -4"))

        assertEquals(perform(deck, "deal with increment 7")[1].toBigInteger(), perform2(BigInteger.ONE, y, "deal with increment 7"))
        assertEquals(perform(deck, "deal with increment 7")[2019].toBigInteger(), perform2(x, y, "deal with increment 7"))
        assertEquals(perform(deck, "deal with increment 13")[2019].toBigInteger(), perform2(x, y, "deal with increment 13"))
        assertEquals(perform(deck, "deal with increment 145")[2019].toBigInteger(), perform2(x, y, "deal with increment 145"))
    }

    @test
    fun inverseMod() {
        val increment = 145.toBigInteger()
        val decksize = 10007.toBigInteger()
        val z = 2019.toBigInteger()


        assertEquals(inverseMod(decksize, increment, BigInteger.ONE), increment.modInverse(decksize))
        assertEquals(inverseMod(decksize, increment, z), ((increment.modInverse(decksize) * z) % decksize + decksize) % decksize)

        assertEquals(increment.negate().modInverse(decksize), (decksize - increment).modInverse(decksize))
    }
}

