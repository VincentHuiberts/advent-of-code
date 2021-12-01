package nl.tintie.aoc.y2016

import nl.tintie.aoc.AocPuzzle
import nl.tintie.aoc.getRepeating
import kotlin.math.abs

object Puzzle1 : AocPuzzle(2016, 1) {
    private val pattern = """(R|L)(\d+)""".toRegex()

    private data class State(val x: Int, val y: Int, val dir: String)
    private class Instruction(val dir: String, val amount: Int)

    private val instructions = input.first().split(", ").map {
        val (_, dir, amount) = pattern.find(it.trim())!!.groupValues
        Instruction(dir, amount.toInt())
    }
    private val directions = listOf("N", "E", "S", "W")
    private fun String.changeDir(dir: String): String {
        val newIdx = directions.indexOf(this) + when (dir) {
            "R" -> 1
            else -> -1
        }
        return directions.getRepeating(newIdx)
    }

    override fun part1(): Any? {
        val endPoint = instructions.fold(State(0, 0, "N")) { state, instr ->
            val newDir = state.dir.changeDir(instr.dir)
            when (newDir) {
                "N" -> state.copy(y = state.y + instr.amount, dir = newDir)
                "E" -> state.copy(x = state.x + instr.amount, dir = newDir)
                "S" -> state.copy(y = state.y - instr.amount, dir = newDir)
                else -> state.copy(x = state.x - instr.amount, dir = newDir)
            }
        }
        return abs(endPoint.x) + abs(endPoint.y)
    }

    override fun part2(): Any? {
        val visited = mutableSetOf<Pair<Int, Int>>(0 to 0)
        instructions.fold(State(0, 0, "N")) { state, instr ->
            val newDir = state.dir.changeDir(instr.dir)

            var lastState: State = state

            repeat(instr.amount) {
                lastState = when (newDir) {
                    "N" -> state.copy(y = lastState.y + 1, dir = newDir)
                    "E" -> state.copy(x = lastState.x + 1, dir = newDir)
                    "S" -> state.copy(y = lastState.y - 1, dir = newDir)
                    else -> state.copy(x = lastState.x - 1, dir = newDir)
                }.also {
                    if (visited.contains(it.x to it.y)) return abs(it.x) + abs(it.y)
                    else visited.add(it.x to it.y)
                }
            }

            lastState
        }
        return null
    }
}

fun main() {
    Puzzle1.runBoth()
}