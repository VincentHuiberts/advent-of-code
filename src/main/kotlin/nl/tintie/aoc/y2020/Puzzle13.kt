package nl.tintie.aoc.y2020

import nl.tintie.aoc.AocPuzzle

object Puzzle13: AocPuzzle(2020, 13) {
    val arriveBy: Int = input[0].toInt()
    val busses = input[1].split(",").map { it.takeIf { it != "x" }?.toInt() }

    override fun part1(): Any? {
        val bus = busses.filterNotNull().minByOrNull { it - (arriveBy % it) }!!
        return bus * (bus - (arriveBy % bus))
    }
}

fun main() {
    Puzzle13.runPart1()
}