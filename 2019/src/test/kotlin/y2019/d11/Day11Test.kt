package y2019.d11

import kotlin.test.assertEquals
import org.junit.Test as test

class Day11Test{

    @test
    fun calculateNextLocation() {
        val robot = Robot(mutableListOf(99))
        val middle = robot.size/2
        assertEquals(robot.direction, 0)
        assertEquals(robot.x, middle)
        assertEquals(robot.y, middle)

        robot.calculateNextLocation(mutableListOf(1, 0))

        assertEquals(robot.x, middle-1)
        assertEquals(robot.y, middle)
        assertEquals(robot.direction, 3)
        assertEquals(robot.grid[middle][middle], 1)
        assertEquals(robot.result(), 1)
    }

    @test
    fun slightlyComplicatedRobot() {
        val robot = Robot(mutableListOf<Long>(99))
        val middle = robot.size/2

        robot.calculateNextLocation(mutableListOf(0, 1)) // black right
        robot.calculateNextLocation(mutableListOf(1, 0)) // white left
        robot.calculateNextLocation(mutableListOf(0, 1)) // black right
        robot.calculateNextLocation(mutableListOf(1, 0)) // white left
        robot.calculateNextLocation(mutableListOf(0, 1)) // black right

        assertEquals(robot.x, middle+3)
        assertEquals(robot.y, middle-2)
        assertEquals(robot.direction, 1)
        assertEquals(robot.grid[middle][middle], 0)
        assertEquals(robot.grid[middle][middle+1], 1)
        assertEquals(robot.grid[middle-1][middle+1], 0)
        assertEquals(robot.grid[middle-1][middle+2], 1)
        assertEquals(robot.grid[middle-2][middle+2], 0)
        assertEquals(robot.result(), 5)
    }

    @test
    fun doNotCountRepaintedAsNew() {
        val robot = Robot(mutableListOf<Long>(99))
        val middle = robot.size/2

        robot.calculateNextLocation(mutableListOf(0, 1)) // black right
        robot.calculateNextLocation(mutableListOf(1, 1)) // white right
        robot.calculateNextLocation(mutableListOf(0, 1)) // black right
        robot.calculateNextLocation(mutableListOf(1, 1)) // white right
        robot.calculateNextLocation(mutableListOf(0, 0)) // black left

        assertEquals(robot.x, middle-1)
        assertEquals(robot.y, middle)
        assertEquals(robot.direction, 3)
        assertEquals(robot.grid[middle][middle], 0)
        assertEquals(robot.grid[middle][middle+1], 1)
        assertEquals(robot.grid[middle+1][middle+1], 0)
        assertEquals(robot.grid[middle+1][middle], 1)
        assertEquals(robot.result(), 4)
    }

    @test
    fun robotCanTakeOneInputAndTurnAndMove() {
        // return paint floor white and then turn based on input
        var instructions = mutableListOf<Long>(3, 10, 104, 1, 4, 10, 99)

        val robot = Robot(instructions)
        val middle = robot.size/2
        assertEquals(robot.direction, 0)
        assertEquals(robot.x, middle)
        assertEquals(robot.y, middle)

        robot.run()

        assertEquals(robot.x, middle-1)
        assertEquals(robot.y, middle)
        assertEquals(robot.direction, 3)
        assertEquals(robot.grid[middle][middle], 1)
    }

    @test
    fun robotCanTakeManyInputs() {
        // return paint floor white and then turn based on input
        var instructions = mutableListOf<Long>(3, 50, 104, 1, 4, 50, 3, 50, 104, 1, 104, 1, 99)

        val robot = Robot(instructions)
        val middle = robot.size/2
        assertEquals(robot.direction, 0)
        assertEquals(robot.x, middle)
        assertEquals(robot.y, middle)

        robot.run()

        assertEquals(robot.x, middle-1)
        assertEquals(robot.y, middle-1)
        assertEquals(robot.direction, 0)
        assertEquals(robot.grid[middle][middle], 1)
        assertEquals(robot.grid[middle][middle-1], 1)
    }

}
