package nl.tintie.aoc.y2021

import nl.tintie.aoc.AocPuzzle

object Puzzle1 : AocPuzzle(2021, 1) {
    val depths = input.map { it.toInt() }

    override fun part1(): Any? {
        return depths.windowed(2).count { (d1, d2) ->
            d2 > d1
        }
    }

    override fun part2(): Any? {
        return depths.windowed(3)
            .map { it.sum() }
            .windowed(2).count { (s1, s2) ->
                s2 > s1
            }
    }
}

fun main() {
    Puzzle1.runBoth()
}