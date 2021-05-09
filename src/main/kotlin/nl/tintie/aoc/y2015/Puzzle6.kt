package nl.tintie.aoc.y2015

import nl.tintie.aoc.AocPuzzle

object Puzzle6 : AocPuzzle(2015, 6) {
    enum class Action {
        TURN_ON, TURN_OFF, TOGGLE
    }

    data class Instruction(
        val action: Action,
        val start: Point,
        val end: Point
    )

    data class Point(val x: Int, val y: Int)

    val instructionPattern = """(turn (on|off)|toggle) (\d+),(\d+) through (\d+),(\d+)""".toRegex()

    val instructions = input.map { line ->
        val groups = instructionPattern.find(line)!!.groupValues
        val action = when (groups[1]) {
            "turn on" -> Action.TURN_ON
            "turn off" -> Action.TURN_OFF
            else -> Action.TOGGLE
        }
        Instruction(action, Point(groups[3].toInt(), groups[4].toInt()), Point(groups[5].toInt(), groups[6].toInt()))
    }

    fun List<List<Boolean>>.processInstruction(instruction: Instruction): List<List<Boolean>> {
        val grid = map { it.toMutableList() }.toMutableList()
        (instruction.start.x..instruction.end.x).forEach { x ->
            (instruction.start.y..instruction.end.y).forEach { y ->
                when (instruction.action) {
                    Action.TURN_ON -> grid[x][y] = true
                    Action.TURN_OFF -> grid[x][y] = false
                    Action.TOGGLE -> grid[x][y] = !grid[x][y]
                }
            }
        }
        return grid.map { it.toList() }.toList()
    }

    override fun part1(): Any? {
        val grid = List(1000) { List(1000) { false } }
        return instructions.fold(grid) { acc, curr ->
            acc.processInstruction(curr)
        }.flatten().count { it }
    }

    fun List<List<Int>>.processInstructionPart2(instruction: Instruction): List<List<Int>> {
        val grid = map { it.toMutableList() }.toMutableList()
        (instruction.start.x..instruction.end.x).forEach { x ->
            (instruction.start.y..instruction.end.y).forEach { y ->
                when (instruction.action) {
                    Action.TURN_ON -> grid[x][y] += 1
                    Action.TURN_OFF -> grid[x][y] = (grid[x][y]-1).coerceAtLeast(0)
                    Action.TOGGLE -> grid[x][y] += 2
                }
            }
        }
        return grid.map { it.toList() }.toList()
    }

    override fun part2(): Any? {
        val grid = List(1000) { List(1000) { 0 } }
        return instructions.fold(grid) { acc, curr ->
            acc.processInstructionPart2(curr)
        }.flatten().sum()
    }
}

fun main() {
    Puzzle6.runBoth()
}