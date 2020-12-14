package nl.tintie.aoc.y2020

import nl.tintie.aoc.AocPuzzle
import kotlin.math.abs

object Puzzle12 : AocPuzzle(2020, 12) {
    val directions = input.map { it.substring(0, 1) to it.substring(1).toInt() }

    val dirs = listOf("N", "E", "S", "W")

    private fun nextDir(currDir: String, turnDir: String, amount: Int): String {
        val idx = dirs.indexOf(currDir)
        val nextIdx = idx + (amount / 90) * if (turnDir == "L") -1 else 1
        return dirs[(dirs.size + nextIdx) % dirs.size]
    }

    private fun Pair<Int, Int>.nextDir(dir: String, amount: Int): Pair<Int, Int> {
        val (x, y) = this
        return when (dir) {
            "N" -> x to (y - amount)
            "E" -> (x + amount) to y
            "S" -> x to (y + amount)
            "W" -> (x - amount) to y
            else -> error("Unknown dir $dir")
        }
    }

    override fun part1(): Any? {
        val (_, x, y) = directions.fold(Triple("E", 0, 0)) { (currentDir, x, y), (instrDir, instrAmount) ->
            when (instrDir) {
                "L", "R" -> Triple(nextDir(currentDir, instrDir, instrAmount), x, y)
                "N", "E", "S", "W" -> {
                    (x to y).nextDir(instrDir, instrAmount).let { (newX, newY) ->
                        Triple(currentDir, newX, newY)
                    }
                }
                "F" -> (x to y).nextDir(currentDir, instrAmount).let { (newX, newY) ->
                    Triple(currentDir, newX, newY)
                }
                else -> error("Unknown instr $instrDir")
            }
        }
        return abs(x) + abs(y)
    }

    private fun Pair<Int, Int>.nextDiffPos(dir: String, amount: Int): Pair<Int, Int> {
        val timesOffset = ((amount / 90) % 4) * if(dir == "L") -1 else 1

        val transformations = listOf<(Pair<Int, Int>) -> Pair<Int, Int>>(
            { (x, y) -> x to y },
            { (x, y) -> (y * -1) to x },
            { (x, y) -> (x * -1) to (y * -1) },
            { (x, y) -> y to (x * -1) }
        )

        return transformations[(transformations.size + timesOffset) % transformations.size](this)
    }

    override fun part2(): Any? {
        val (pos, _) = directions.fold(
            Pair(
                0 to 0,
                10 to -1
            )
        ) { (pos, wpDiff), (instrDir, instrAmount) ->
            println("Shippos $pos")
            println("wpDiff $wpDiff")
            println()
            val (x, y) = pos
            val (dx, dy) = wpDiff

            when (instrDir) {
                "L", "R" -> (x to y) to (dx to dy).nextDiffPos(instrDir, instrAmount)
                "N", "E", "S", "W" -> {
                    (dx to dy).nextDir(instrDir, instrAmount).let { (newX, newY) ->
                        (x to y) to (newX to newY)
                    }
                }
                "F" -> (x + dx * instrAmount to y + dy * instrAmount) to (dx to dy)
                else -> error("Unknown instr $instrDir")
            }
        }
        return abs(pos.first) + abs(pos.second)
    }
}

fun main() {
    Puzzle12.runPart2()
}