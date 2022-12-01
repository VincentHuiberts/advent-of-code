package nl.tintie.aoc.y2022

import nl.tintie.aoc.AocPuzzle
import nl.tintie.aoc.splitToSequence

object Puzzle1 : AocPuzzle(2022, 1) {
    override fun part1(): Any? {
        return input.splitToSequence { it.isEmpty() }.maxOfOrNull { elfList ->
            elfList.sumOf { it.toInt() }
        }
    }

    override fun part2(): Any? {
        return input.splitToSequence { it.isEmpty() }.map { elfList ->
            elfList.sumOf { it.toInt() }
        }.sortedDescending().take(3).sum()
    }
}

fun main() {
    Puzzle1.runBoth()
}