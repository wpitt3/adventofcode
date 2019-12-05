package y2019.d01

import java.io.File

fun main() {
    val lines: List<String>  = File("src/main/resources/y2019d01.txt").readLines()
}

class Day01() {
    fun getFuelRequired(mass: Int):Int {
        return (mass-mass%3)/3-2
    }

    fun getFuelForAll(total: List<String>): Int {
        return total.map(String::toInt)
            .map(this::getFuelRequired)
            .reduce( { a, b -> a + b })
    }

    fun getHeavyFuelRequired(mass: Int):Int {
        if(mass < 9) return 0
        val fuelForMass = (mass-mass%3)/3-2
        return fuelForMass + getHeavyFuelRequired(fuelForMass)
    }

    fun getHeavyFuelForAll(total: List<String>): Int {
        return total.map(String::toInt)
            .map(this::getHeavyFuelRequired)
            .reduce( { a, b -> a + b })
    }
}
