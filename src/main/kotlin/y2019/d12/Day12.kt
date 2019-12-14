package y2019.d12

import kotlin.math.abs

fun main (){
    Day12().inputMoons()
}

class Day12() {
    fun inputMoons() {
        val xMovements = listOf(-19, -9, -4, 1).map{Movement(it, 0)}
        val yMovements = listOf(-4, 8, 5, 9).map{Movement(it, 0)}
        val zMovements = listOf(2, -16, -11, -13).map{Movement(it, 0)}
        println (partA(xMovements, yMovements, zMovements))
        println (partB(xMovements, yMovements, zMovements))
    }

    fun partB(xMovements: List<Movement>, yMovements: List<Movement>, zMovements: List<Movement>): Long {
        var x = findLoop(xMovements)
        var y = findLoop(yMovements)
        var z = findLoop(zMovements)

        var hcf = hcf(x, hcf(y, z))

        return x.toLong()*y.toLong()*z.toLong()/hcf/hcf/hcf
    }

    fun hcf(a: Int, b: Int): Int {
        var cd = if (abs(a) > abs(b)) Pair(abs(a), abs(b)) else Pair (abs(b), abs(a))
        while( cd.second != 0) {
            cd = Pair(cd.second, cd.first%cd.second)
        }
        return cd.first
    }

    fun findLoop(movements: List<Movement>): Int {
        var newMoves = (0..3).map{calcNewMovements(movements[it], listWithout(movements, it))}
        var index: Int = 1;
        while(hash(newMoves) != hash(movements)) {
            index++
            newMoves = (0..3).map{calcNewMovements(newMoves[it], listWithout(newMoves, it))}
        }
        return index
    }

    fun hash(movements: List<Movement>): String {
        return movements.map{it.pos.toString()+":"+it.speed}.joinToString()
    }


    fun partA(xMovements: List<Movement>, yMovements: List<Movement>, zMovements: List<Movement>): Int {
        var newX = xMovements
        var newY = yMovements
        var newZ = zMovements
        (0..999).forEach{
            newX = (0..3).map{calcNewMovements(newX[it], listWithout(newX, it))}
            newY = (0..3).map{calcNewMovements(newY[it], listWithout(newY, it))}
            newZ = (0..3).map{calcNewMovements(newZ[it], listWithout(newZ, it))}
        }
        return calcEnergy(listOf(newX, newY, newZ))
    }

    fun calcEnergy(movements: List<List<Movement>>): Int {
        return (0..3).map{ i ->  (0..2).map{abs(movements[it][i].pos)}.sum()*(0..2).map{ abs(movements[it][i].speed) }.sum()}.sum()
    }

    fun calcNewMovements(movement: Movement, otherMovements: List<Movement>): Movement {
        val change: Int = otherMovements.map{ if (it.pos > movement.pos) { 1 } else if (it.pos == movement.pos) { 0 } else { -1 } }.sum()
        return Movement(movement.pos + movement.speed + change, movement.speed + change)
    }

    fun <T> listWithout(list: List<T>, x: Int): List<T> {
        return list.take(x) + list.drop(x+1)
    }
}

data class Movement(var posA: Int, var speedA: Int) {
    var pos: Int
    var speed: Int

    init{
        this.pos = posA
        this.speed = speedA
    }
}