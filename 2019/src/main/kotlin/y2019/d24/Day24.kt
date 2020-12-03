package y2019.d24

fun main() {
    //#....
    //.#.##
    //#...#
    //#.#..
    //..##.
    val grid = Day24().emptyGrid()
    grid[0][0] = true
    grid[1][1] = true
    grid[1][3] = true
    grid[1][4] = true
    grid[2][0] = true
    grid[2][4] = true
    grid[3][0] = true
    grid[3][2] = true
    grid[4][2] = true
    grid[4][3] = true

    println(Day24().findFirstRepeat(BugGrid(grid.toList())).bRating())

    //1944
    var bugGrids = Day24().toMultiDepth(BugGrid(grid.toList()), 200)
    (0..199).forEach{
        bugGrids = Day24().stepMultiDepth(bugGrids)
    }

    println(Day24().countBugs(bugGrids))


}
class Day24() {
    fun toMultiDepth(grid: BugGrid, depth: Int): List<BugGrid> {
        var bugGrids = (0..(depth*2)).map{BugGrid(emptyGrid())}.toMutableList()
        bugGrids[depth] = grid
        return bugGrids
    }

    fun countBugs(bugGrids: List<BugGrid>): Int {
        return bugGrids.map{ grid -> (0..4).map{ y -> (0..4).count{ x -> grid.bugs[y][x] }}.sum()}.sum()
    }

    fun stepMultiDepth(bugGrids: List<BugGrid>): List<BugGrid> {
        return (0..(bugGrids.size-1)).map{z -> BugGrid((0..4).map{y -> (0..4).map{ x -> isMultiDepthBug(x, y, z, bugGrids)}})}
    }

    fun isMultiDepthBug(x: Int, y: Int, z: Int, grids: List<BugGrid>): Boolean {
        if (x == 2 && y == 2) {
            return false
        }
        val maxZ = grids.size - 1
        var count = 0
        if (x > 0) {
            if (x != 3 || y != 2) {
                if (grids[z].bugs[y][x - 1]) {
                    count++
                }
            } else if (z != maxZ) {
                count += (0..4).count{grids[z+1].bugs[it][4]}
            }
        } else if (z != 0 && grids[z-1].bugs[2][1]) {
            count++
        }

        if (x < 4) {
            if (x !=  1 || y != 2) {
                if (grids[z].bugs[y][x+1]) {
                    count++
                }
            } else if (z != maxZ) {
                count += (0..4).count{grids[z+1].bugs[it][0]}
            }
        } else if (z != 0 && grids[z-1].bugs[2][3]) {
            count++
        }

        if (y > 0) {
            if (x != 2 || y != 3) {
                if (grids[z].bugs[y-1][x]) {
                    count++
                }
            } else if (z != maxZ) {
                count += (0..4).count{grids[z+1].bugs[4][it]}
            }
        } else if (z != 0 && grids[z-1].bugs[1][2]) {
            count++
        }

        if (y < 4) {
            if (x != 2 || y != 1) {
                if (grids[z].bugs[y+1][x]) {
                    count++
                }
            } else if (z != maxZ) {
                count += (0..4).count{grids[z+1].bugs[0][it]}
            }
        } else if (z != 0 && grids[z-1].bugs[3][2]) {
            count++
        }
        return count == 1 || (!grids[z].bugs[y][x] && count == 2)
    }

    fun findFirstRepeat(grid: BugGrid): BugGrid {
        var bugGridSet: MutableSet<BugGrid> = mutableSetOf()
        var newGrid = grid.copy()
        while(!bugGridSet.contains(newGrid)) {
            bugGridSet.add(newGrid)
            newGrid = step(newGrid)
        }
        return newGrid
    }

    fun step(grid: BugGrid): BugGrid {
        return BugGrid((0..4).map{y -> (0..4).map{ x -> isBug(x, y, grid)}})
    }

    fun isBug(x: Int, y: Int, grid: BugGrid): Boolean {
        var count = 0
        if (x > 0 && grid.bugs[y][x-1]) {
            count++
        }
        if (x < 4 && grid.bugs[y][x+1]) {
            count++
        }
        if (y > 0 && grid.bugs[y-1][x]) {
            count++
        }
        if (y < 4 && grid.bugs[y+1][x]) {
            count++
        }
        return count == 1 || (!grid.bugs[y][x] && count == 2)
    }

    fun emptyGrid(): MutableList<MutableList<Boolean>> {
        return mutableListOf(
            mutableListOf(false, false, false, false, false),
            mutableListOf(false, false, false, false, false),
            mutableListOf(false, false, false, false, false),
            mutableListOf(false, false, false, false, false),
            mutableListOf(false, false, false, false, false)
        )
    }
}

data class BugGrid(val bugs : List<List<Boolean>>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as BugGrid
        if (bugs != other.bugs) return false
        return true
    }

    override fun hashCode(): Int {
        return Integer.parseInt(toBinaryString(), 2)
    }

    fun bRating(): Int {
        return Integer.parseInt(toBinaryString(), 2)
    }

    fun toBinaryString(): String {
        return bugs.flatten().map{ if (it) '1' else '0'}.reversed().joinToString("")
    }

    override fun toString(): String {
        return bugs.map{ a -> a.map{if (it) '#' else '.'}.joinToString("")}.joinToString("\n")
    }

}