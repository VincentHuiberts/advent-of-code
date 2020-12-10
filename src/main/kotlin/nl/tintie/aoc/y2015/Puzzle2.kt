package nl.tintie.aoc.y2015

import nl.tintie.aoc.AocPuzzle

object Puzzle2 : AocPuzzle(2015, 2) {
    val dimensions = input.map { it.split("x").map { it.toInt() } }

    override fun part1() =
        dimensions.sumBy { (l, w, h) ->
            2 * l * w + 2 * w * h + 2 * h * l + listOf(l, w, h).sorted().take(2).reduce(Int::times)
        }

    override fun part2() = dimensions.sumBy { (l, w, h) ->
        val wrapLength = listOf(l, w, h).sorted().take(2).let { (min1, min2) -> 2 * min1 + 2 * min2 }
        wrapLength + l * w * h
    }
}

fun main() {
    Puzzle2.runBoth()
}