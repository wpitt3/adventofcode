package y2019.d18

import java.io.File

fun main (){
    partOne()

    val grid : MutableList<MutableList<Char>> = File("src/main/resources/y2019d18.txt").readLines().map{it.split("").filter{it != ""}.map{it[0]}.toMutableList()}.toMutableList()
    grid[40][40] = '#'
    grid[41][40] = '#'
    grid[40][41] = '#'
    grid[40][39] = '#'
    grid[39][40] = '#'
    val keyToOtherKeys: Map<Char, List<Key>> =
        mapOf(Pair('1', findKeysForCharacter(grid, Pair(39,39)).values.toList())) +
        mapOf(Pair('2', findKeysForCharacter(grid, Pair(39,41)).values.toList())) +
        mapOf(Pair('3', findKeysForCharacter(grid, Pair(41,39)).values.toList())) +
        mapOf(Pair('4', findKeysForCharacter(grid, Pair(41,41)).values.toList())) +
        (0..25).map{ (it+97).toChar()}.map {
            Pair(it, findKeysForCharacter(grid, findOnGrid(grid, it)).values.toList())
    }.toMap()

    var fourKeyCollections: MutableMap<FourKeyCollection, Int> =
        mutableMapOf(Pair(FourKeyCollection( setOf('1', '2', '3', '4'), setOf()), 0))

    repeat(26){
        fourKeyCollections = fourKeyCollections.keys.map { fourKeyCollection ->
            fourKeyCollection.currentKeys.map { oldKey ->
                validKeys(keyToOtherKeys, oldKey, fourKeyCollection.seenKeys).map { Pair(oldKey, it)}
            }.flatten().map { (oldKey, newKey) ->
                Pair(
                    FourKeyCollection(replaceKey(fourKeyCollection.currentKeys, oldKey, newKey.type), fourKeyCollection.seenKeys + newKey.type),
                    (fourKeyCollections[fourKeyCollection]!! + newKey.index)
                )
            }.toMap().toMutableMap()

        }.reduce{a, b -> joinFourShits(a, b)}
    }
    println(fourKeyCollections.values.min())
}

fun replaceKey(oldKeys: Set<Char>, toReplace: Char, replacement: Char): Set<Char> {
    val result = oldKeys.toMutableSet()
    result.remove(toReplace)
    result.add(replacement)
    return result
}


fun joinFourShits(a: MutableMap<FourKeyCollection, Int>, b: MutableMap<FourKeyCollection, Int>): MutableMap<FourKeyCollection, Int> {
    b.keys.forEach{
        if (a[it] == null || a[it]!! > b[it]!!) {
            a[it] = b[it]!!
        }
    }
    return a
}

fun partOne() {
    val grid : List<List<Char>> = File("src/main/resources/y2019d18.txt").readLines().map{it.split("").filter{it != ""}.map{it[0]}}

    val startPoint: Map<Char, List<Key>> = mapOf(Pair('@', findKeysForCharacter(grid, findOnGrid(grid, '@')).values.toList()))
    val keyToOtherKeys: Map<Char, List<Key>> = startPoint + (0..25).map{ (it+97).toChar()}.map {
        Pair(it, findKeysForCharacter(grid, findOnGrid(grid, it)).values.toList())
    }.toMap()

    var keyCollections: MutableMap<KeyCollection, Int> = mutableMapOf(Pair(KeyCollection( '@', setOf()), 0))

    repeat(26){
        keyCollections = keyCollections.keys.map{ keyCollection ->
            validKeys(keyToOtherKeys, keyCollection.currentKey, keyCollection.seenKeys).map{ key ->
                Pair(KeyCollection(key.type, keyCollection.seenKeys + key.type), (keyCollections[keyCollection]!! + key.index))
            }.toMap().toMutableMap()
        }.reduce{a, b -> joinShit(a, b)}
    }
    println(keyCollections.values.min())
}

fun joinShit(a: MutableMap<KeyCollection, Int>, b: MutableMap<KeyCollection, Int>): MutableMap<KeyCollection, Int> {
    b.keys.forEach{
        if (a[it] == null || a[it]!! > b[it]!!) {
            a[it] = b[it]!!
        }
    }
    return a
}

fun validKeys(keyToOtherKeys: Map<Char, List<Key>>, currentChar: Char, obtainedKeys: Set<Char>): List<Key> {
    return keyToOtherKeys[currentChar]!!
        .filter{!obtainedKeys.contains(it.type)}
        .filter { it.passed.minus(obtainedKeys).isEmpty() }
}

fun findKeysForCharacter(grid : List<List<Char>>, start: Pair<Int, Int> ): Map<Char, Key> {
    val mutableGrid = grid.map{it.toMutableList()}.toMutableList()
    mutableGrid[start.first][start.second] = '#'

    var walkers = setOf(Walker(start.second, start.first, setOf()))
    var keyIndexes: MutableMap<Char, Key> = mutableMapOf()
    var index = 1
    while(!walkers.isEmpty()) {
        val newWalkers: Set<Walker> = walkers.map{ findNextLocations(it, mutableGrid, index, keyIndexes) }.flatten().toSet()
        newWalkers.forEach{
            mutableGrid[it.y][it.x] = '#'
        }
        walkers = newWalkers
        index++
    }
    return keyIndexes
}

fun printGrid(grid: List<List<Char>>){
    (0..(grid.size-1)).forEach { y ->
        println((0..(grid[0].size - 1)).map { x ->
            grid[y][x]
        }.joinToString(""))
    }
}

fun findOnGrid(grid: List<List<Char>>, value: Char): Pair<Int, Int> {
    (0..(grid.size-1)).forEach { y ->
        (0..(grid[0].size-1)).forEach { x ->
            if (grid[y][x] == value) {
                return Pair(y, x)
            }
        }
    }
    return Pair(-1, -1)
}

fun findNextLocations(walker: Walker, grid: List<List<Char>>, index: Int, keyIndexes: MutableMap<Char, Key>): List<Walker> {
    val nextWalkers: MutableList<Walker> = mutableListOf()
    (-1..1).forEach { xOffset -> (-1..1).forEach { yOffset ->
        val x = walker.x + xOffset
        val y = walker.y + yOffset
        if (yOffset+xOffset != 0 && xOffset != yOffset && grid[y][x] != '#') {
            nextWalkers.add(createWalker(y, x, grid, walker.passed))
            updateKeys(index, keyIndexes, grid[y][x], walker.passed)
        }
    }}
    return nextWalkers
}

fun updateKeys(index: Int, keyIndexes: MutableMap<Char, Key>, gridValue: Char, passed: Set<Char>) {
    if (gridValue != '.' && gridValue.isLowerCase()) {
        if (keyIndexes[gridValue] != null) {
            throw RuntimeException("This is a bit wonky")
        }
        keyIndexes[gridValue] = Key(index, gridValue, passed)
    }
}

fun createWalker(y: Int, x: Int, grid: List<List<Char>>, passed: Set<Char>): Walker {
    val gridValue = grid[y][x]
    val newPassed = passed.toMutableSet()
    if (gridValue != '.' && gridValue.isUpperCase()) {
        newPassed.add(gridValue.toLowerCase())
    }
    return Walker(x, y, newPassed)
}

data class Walker(val x: Int, val y: Int, val passed: Set<Char>)
data class Key(val index: Int, val type: Char, val passed: Set<Char>)
data class KeyCollection(val currentKey: Char, val seenKeys: Set<Char>)
data class FourKeyCollection(val currentKeys: Set<Char>, val seenKeys: Set<Char>)




