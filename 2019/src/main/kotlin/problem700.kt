import java.math.BigInteger

fun main() {
    problem700()


}

fun multiplierForRemainder(index :BigInteger, mod :BigInteger, x: BigInteger): BigInteger {
    return ((index.modInverse(mod) * x) % mod + mod) % mod
}

fun problem700() {

//    val inversedMod = index.modInverse(deckSize)
//    return { x: BigInteger -> ((inversedMod * x) % deckSize + deckSize) % deckSize }


    // 80 * 4 % 11 == 1
    // 80 * 3 % 11 == 9
    // 80 modInverse(11) == 4

//    var a = BigInteger("80")
//    var b = BigInteger("11")
//    var c = BigInteger("9")
//    println((80*4) % 11)
//    println(a.modInverse(b))
//
//    println((80*3) % 11)








    var c = BigInteger("1504170715041707")
    var d = BigInteger("4503599627370517")
//    var e = BigInteger("8912517754604")

    var total = BigInteger("1")
    total = total.add(BigInteger("1517926264989119"))

    var min = multiplierForRemainder(c, d, BigInteger.ONE)
    for (i in 1..14_578_936) {
        var x = multiplierForRemainder(c, d, BigInteger(i.toString()))
        if (x < min) {
            total = total.add(BigInteger(i.toString()))
            min = x
            println()
            println(i)
            println(min)
        }
    }
    println(total)

    //893779696
    //10543898410

//    println(c.modInverse(d))
//    println(d.modInverse(c))
//    println(e.modInverse(c))
//    println(e.modInverse(d))
//    println(d.modInverse(e))
//    println(c.modInverse(e))
//    BigInteger modInverse
}