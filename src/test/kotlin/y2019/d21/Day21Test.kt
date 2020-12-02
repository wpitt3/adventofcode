
import kotlin.test.assertEquals
import org.junit.Test as test

class Day21Test{

    @test
    fun test() {
        val machineCode = listOf(
                "NOT A J",
                "NOT B T",
                "OR J T",
                "NOT C J",
                "OR T J",
                "NOT J T",
                "OR E T",
                "OR H T",
                "AND D T",
                "AND T J",
                "RUN"
        )

        assertEquals(runMachineCode(machineCode, "...######"), true)
        assertEquals(runMachineCode(machineCode, "##.#.##.#"), false)
    }


    fun runMachineCode(machineCode: List<String>, scenario: String): Boolean {
        var J = false
        var T = false
        val grid = scenario.map{it.toString() == "#"}

        machineCode.forEach{
            if (it.startsWith("NOT")) {
                val x = it[4].toInt() - 65
                val y = it[6].toInt() - 65
                val xVal = if (x>8) (if (x == 9) J else T) else grid[x]
                if (y==9) {
                    J = !xVal
                } else {
                    T = !xVal
                }
            } else if(it.startsWith("AND")) {
                val x = it[4].toInt() - 65
                val y = it[6].toInt() - 65
                val xVal = if (x>8) (if (x == 9) J else T) else grid[x]
                if (y==9) {
                    J = J && xVal
                } else {
                    T = T && xVal
                }
            } else if(it.startsWith("OR")) {
                val x = it[3].toInt() - 65
                val y = it[5].toInt() - 65
                val xVal = if (x>8) (if (x == 9) J else T) else grid[x]
                if (y==9) {
                    J = J || xVal
                } else {
                    T = T || xVal
                }
            }
        }

        println("T =" + T )
        println("J =" + J )
        return J
    }
}
