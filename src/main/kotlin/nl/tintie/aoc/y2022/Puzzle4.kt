package nl.tintie.aoc.y2022

import nl.tintie.aoc.AocPuzzle

object Puzzle4 : AocPuzzle(2022, 4) {
    val inputRanges: List<Pair<IntRange, IntRange>> = input.map { line ->
        val (r1s, r2s) = line.split(",")
        val r1 = r1s.split("-").let { (s, e) -> s.toInt()..e.toInt() }
        val r2 = r2s.split("-").let { (s, e) -> s.toInt()..e.toInt() }
        r1 to r2
    }

    operator fun IntRange.contains(other: IntRange): Boolean =
        this.first <= other.first && this.last >= other.last

    override fun part1(): Any? {
        return inputRanges.count { (r1, r2) ->
            r1 in r2 || r2 in r1
        }
    }

    private infix fun IntRange.overlaps(other: IntRange): Boolean =
        first in other || last in other || other.first in this || other.last in this

    override fun part2(): Any? {
        return inputRanges.count { (r1, r2) ->
            r1 in r2 || r2 in r1 || r1 overlaps r2
        }
    }
}

fun main() {
    Puzzle4.runBoth()
}