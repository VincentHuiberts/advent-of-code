package nl.tintie.aoc.y2015

import nl.tintie.aoc.AocPuzzle

object Puzzle1:AocPuzzle(2015, 1) {
    val diffs = input.first().chunked(1).map { when(it) {
        "(" -> 1
        else -> -1
    } }

    override fun part1(): Any? =
        diffs.sum()

    override fun part2(): Any? {
        var floor = 0
        var i = 0
        diffs.find { floor += it; i++; floor == -1 }
        return i
    }
}

fun main() {
    Puzzle1.runPart2()
}
