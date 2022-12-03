package nl.tintie.aoc.y2022

import nl.tintie.aoc.AocPuzzle

object Puzzle3 : AocPuzzle(2022, 3) {
    private val allChars = (('a'..'z') + ('A'..'Z')).toCharArray()

    private fun Char.getPriority(): Int = allChars.indexOf(this) + 1

    private fun List<String>.getDuplicates(): Set<Char> = drop(1).fold(first().toSet()) { acc, curr ->
        acc.intersect(curr.toSet())
    }

    override fun part1(): Any? {
        return input.sumOf { line ->
            val parts = line.chunked(line.length / 2)
            parts.getDuplicates().single().getPriority()
        }
    }

    override fun part2(): Any? {
        return input.windowed(3, 3, false).sumOf { lines ->
            lines.getDuplicates().single().getPriority()
        }
    }
}

fun main() {
    Puzzle3.runBoth()
}