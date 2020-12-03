package y2019.d10

import kotlin.math.abs

class Day10() {

    fun findXPlanetLocation(asteroids: List<Pair<Int, Int>>, x: Int): Int {
        val baseLocation = findBestMoonBaseLocationToCount(asteroids).second
        val asteroidLocations = asteroids.filter { it != baseLocation }.map{ Pair(it.first-baseLocation.first,it.second-baseLocation.second) }
        val asteroidDirections = asteroidLocations.map{ val x = hcf(it.first, it.second); Pair(it.first/x, it.second/x)}
            .toSet().toList().sortedBy { convertDirectionToSomethingSortable(it) }.reversed()
        val directionX = asteroidDirections.get(x-1)
        val firstAsteroidsInRightDirection = asteroidLocations
            .filter{ val x = hcf(it.first, it.second); it.first/x == directionX.first && it.second/x == directionX.second }
            .sortedBy { it.first }.reversed().get(0)
        return (firstAsteroidsInRightDirection.first + baseLocation.first)*100 + firstAsteroidsInRightDirection.second + baseLocation.second
    }

    fun convertDirectionToSomethingSortable(direction: Pair<Int,Int>): Double {
        var a : Double = abs((if (direction.first == 0) 0.0001 else direction.first.toDouble()) /
                (if (direction.second == 0) 0.0001 else direction.second.toDouble()))
        if (direction.first >= 0 && direction.second < 0) {
            a = 1/a
            a += 300000
        } else if (direction.first > 0 && direction.second >= 0) {
            a += 200000
        } else if (direction.first <= 0 && direction.second > 0) {
            a = 1/a
            a += 100000
        }
        return a
    }


    fun findBestMoonBaseLocationToCount(asteroids: List<Pair<Int, Int>>): Pair<Int, Pair<Int,Int>> {
        return asteroids.map{Pair(countVisibleAsteroids(asteroids, it),it)}.sortedBy{it.first}.reversed().get(0)
    }

    fun findBestMoonBaseCount(asteroids: List<Pair<Int, Int>>): Int {
        return findBestMoonBaseLocationToCount(asteroids).first
    }

    fun countVisibleAsteroids(asteroids: List<Pair<Int, Int>>, location: Pair<Int, Int>): Int {
        return asteroids.filter { it != location }
            .map{ Pair(it.first-location.first,it.second-location.second) }
            .map{ val x = hcf(it.first, it.second); Pair(it.first/x, it.second/x)}
            .toSet().size
    }

    fun convertAsteroids(lines: List<String>): List<Pair<Int, Int>> {
        val asteroids : MutableList<Pair<Int, Int>> = mutableListOf()
        for (y in 0..(lines.size-1)) {
            val isAsteroids: List<Boolean> = lines[y].map({x -> x.toString()}).map{it == "#"}.toList()
            for (x in 0..(isAsteroids.size-1)) {
                if(isAsteroids[x]) {
                    asteroids.add(Pair(x,y))
                }
            }
        }
        return asteroids
    }

    fun hcf(a: Int, b: Int): Int {
        var cd = if (abs(a) > abs(b)) Pair(abs(a), abs(b)) else Pair (abs(b), abs(a))
        while( cd.second != 0) {
            cd = Pair(cd.second, cd.first%cd.second)
        }
        return cd.first
    }
}
