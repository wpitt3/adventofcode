package y2019.d06

class Day06() {
    fun countOrbits(lines: List<String>): Int {
        val orbits: Map<String, List<String>> = lines.map{ it.split(")") }.groupBy({it[0]}, {it[1]})
        val rootNodes: Set<String> = orbits.keys - orbits.values.flatMap { it }
        var nodes = rootNodes

        var total: Int = 0
        var orbitIndex = 1
        while(nodes.isNotEmpty()) {
            nodes = nodes.filter{orbits.get(it) != null}.map{orbits.get(it)!!}.flatMap { it }.toMutableSet()
            total += nodes.size * orbitIndex++
        }

        return total
    }

    fun orbitTransfers(lines: List<String>): Int {
        val orbits: Map<String, String> = lines.map{ it.split(")") }.map{ it[1] to it[0] }.toMap()

        val pathToYou = getWholePathForSat("YOU", orbits);
        val pathToSan = getWholePathForSat("SAN", orbits);

        return (pathToSan - pathToYou).size + (pathToYou - pathToSan).size
    }

    fun getWholePathForSat(satName: String, orbits: Map<String, String>): MutableList<String> {
        val pathToSat: MutableList<String> = mutableListOf()
        var node: String = satName
        while (orbits.get(node) != null) {
            node = orbits.get(node)!!
            pathToSat.add(node)
        }
        return pathToSat
    }

}
