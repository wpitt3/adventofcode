import java.io.File
import java.math.BigInteger


fun main() {
    val lines = File("src/main/resources/y2019d22.txt").readLines()
    day22a(lines)
    day22b(lines)
}

fun day22a(lines: List<String>) {
    var deck = (0..10006).map { it }.toList()

    for (line in lines) {
        deck = perform(deck, line)
    }
    (0..10006).forEach{
        if (deck[it] == 2019) {
            println(it)
        }
    }
}

fun day22b(lines: List<String>) {
    val startIndex = BigInteger("2020")
    val deckSize = BigInteger("119315717514047")
    val repeats = BigInteger("101741582076661")
    val timesToShuffle = deckSize - repeats - BigInteger.ONE

    val commands = lines.map{calculateCommand( deckSize, it)}

    val oneStep = commands.fold(startIndex){ x, command -> command(x)}
    val twoStep = commands.fold(oneStep){ x, command -> command(x)}

    val a = (oneStep-twoStep) * (startIndex-oneStep+deckSize).modInverse(deckSize) % deckSize
    val b = (oneStep-a*startIndex) % deckSize

    println((a.modPow(timesToShuffle, deckSize) * startIndex +
            (a.modPow(timesToShuffle, deckSize) - BigInteger.ONE) * (a - BigInteger.ONE).modInverse(deckSize) * b) % deckSize)
}

fun perform(deck: List<Int>, command: String): List<Int> {
    val deckSize = deck.size
    if (command.startsWith("cut ")) {
        var index = command.subSequence(4, command.length).toString().toInt()
        while(index < 0) {
            index += deckSize
        }
        return deck.drop(index) + deck.take(index)

    } else if (command.startsWith("deal with increment ")) {
        val index = command.subSequence(20, command.length).toString().toInt()
        val newDeck = List(deckSize){-1}.toMutableList()
        (0..(deckSize-1)).forEach{
            newDeck[index*it%deckSize] = deck[it]
        }
        return newDeck
    } else {
        return deck.reversed()
    }
}

fun perform2(index: BigInteger, deckSize: BigInteger, command: String): BigInteger {
    return calculateCommand2(deckSize, command)(index)
}

fun calculateCommand(deckSize: BigInteger, command: String): (BigInteger) -> BigInteger {
    if (command.startsWith("cut ")) {
        val index = command.subSequence(4, command.length).toString().toBigInteger()
        return { x: BigInteger -> (x - index + deckSize) % deckSize }
    } else if (command.startsWith("deal with increment ")) {
        val index = command.subSequence(20, command.length).toString().toBigInteger()
        return { x: BigInteger -> (index * x) % deckSize }
    } else {
        return { x: BigInteger -> (deckSize - x - BigInteger.ONE + deckSize) % deckSize }
    }
}

fun calculateCommand2(deckSize: BigInteger, command: String): (BigInteger) -> BigInteger {
    if (command.startsWith("cut ")) {
        val index = command.subSequence(4, command.length).toString().toBigInteger()
        return { x: BigInteger -> (x + index + deckSize) % deckSize }
    } else if (command.startsWith("deal with increment ")) {
        val index = command.subSequence(20, command.length).toString().toBigInteger()
        val inversedMod = index.modInverse(deckSize)
        return { x: BigInteger -> ((inversedMod * x) % deckSize + deckSize) % deckSize }
    } else {
        return { x: BigInteger -> deckSize - x - BigInteger.ONE }
    }
}

fun inverseMod(deckSize: BigInteger, index: BigInteger, x: BigInteger): BigInteger {
    val remainders = mutableListOf(index)
    val multipliers = mutableListOf<BigInteger>()
    var a = deckSize
    while(remainders.last() > BigInteger.ONE) {
        val b = remainders.last()
        remainders.add(a % b)
        multipliers.add((a - remainders.last()) / b)
        a = b
    }

    var alpha = BigInteger.ONE
    var beta = - multipliers.last()

    for (i in (multipliers.size-2) downTo 0) {
        val newAlpha = beta
        beta = alpha + (multipliers[i] * beta * (-BigInteger.ONE))
        alpha = newAlpha
    }

    return ((beta * x) % deckSize + deckSize) % deckSize
}

