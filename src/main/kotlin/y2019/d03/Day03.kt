package y2019.d03

class Day03() {
    fun findNearestIntersection(wireAStrings: List<String>, wireBStrings: List<String>):Int {
        val wireA : List<Vector>  = wireAStrings.map({Vector(it)})
        val wireB : List<Vector>  = wireBStrings.map({Vector(it)})

        val visitedPointsA = calculateVisited(wireA)
        val visitedPointsB = calculateVisited(wireB)

        val x = visitedPointsA.intersect(visitedPointsB)
        return x.map({Math.abs(it.first) + Math.abs(it.second)}).sortedDescending().reversed()[0]
    }

    fun combinedSteps(wireAStrings: List<String>, wireBStrings: List<String>):Int {
        val wireA : List<Vector>  = wireAStrings.map({Vector(it)})
        val wireB : List<Vector>  = wireBStrings.map({Vector(it)})

        val visitedPointsA = calculateVisited(wireA)
        val visitedPointsB = calculateVisited(wireB)
        val collisions: Set<Pair<Int, Int>> = visitedPointsA.intersect(visitedPointsB)

        val collisionToStepsA: MutableMap<Pair<Int, Int>, Int> = collisions.map({it to 0}).toMap().toMutableMap()
        for (step in (0..(visitedPointsA.size-1))) {
            if (collisionToStepsA.get(visitedPointsA[step]) != null && collisionToStepsA.get(visitedPointsA[step]) == 0) {
                collisionToStepsA.put(visitedPointsA[step], step+1)
            }
        }

        val collisionToStepsB: MutableMap<Pair<Int, Int>, Int> = collisions.map({it to 0}).toMap().toMutableMap()
        for (step in (0..(visitedPointsB.size-1))) {
            if (collisionToStepsB.get(visitedPointsB[step]) != null && collisionToStepsB.get(visitedPointsB[step]) == 0) {
                collisionToStepsB.put(visitedPointsB[step], step+1)
            }
        }
        return collisions.map({collisionToStepsB.getOrDefault(it, 0) + collisionToStepsA.getOrDefault(it, 0)}).sortedDescending().reversed()[0]
    }

    fun calculateVisited(wireA : List<Vector>): MutableList<Pair<Int, Int>> {
        var curPos: Pair<Int, Int> = Pair(0, 0)
        var visited: MutableList<Pair<Int, Int>> = mutableListOf()

        for (vector in wireA) {
            if (vector.direction == "R") {
                curPos = move(vector.magnitude, curPos, visited, ({Pair(it.first+1, it.second)}))
            } else if (vector.direction == "L") {
                curPos = move(vector.magnitude, curPos, visited, ({Pair(it.first-1, it.second)}))
            } else if (vector.direction == "U") {
                curPos = move(vector.magnitude, curPos, visited, ({Pair(it.first, it.second+1)}))
            } else if (vector.direction == "D") {
                curPos = move(vector.magnitude, curPos, visited, ({Pair(it.first, it.second-1)}))
            }
        }
        return visited
    }

    fun move(magnitude: Int,
             curPos: Pair<Int, Int>,
             visited: MutableList<Pair<Int, Int>>,
             doIt: (Pair<Int, Int>)->Pair<Int, Int>):
            Pair<Int, Int> {
        var pos: Pair<Int, Int> = curPos
        for(i in (0..(magnitude-1))) {
            pos = doIt(pos)
            visited.add(pos)
        }
        return pos
    }
}


data class Vector(var vectorString: String) {
    var magnitude: Int = -1
    var direction: String = "A"

    init {
        magnitude = Integer.parseInt(vectorString.substring(1))
        direction = vectorString.substring(0, 1)
    }
}
