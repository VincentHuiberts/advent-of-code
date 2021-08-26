package nl.tintie.aoc.y2015

import nl.tintie.aoc.AocPuzzle
import nl.tintie.aoc.combinations
import nl.tintie.aoc.uniqueOrderedCombinations

object Puzzle10 : AocPuzzle(2015, 10) {
    val repeatingDigitPattern = """(\d)\1*""".toRegex()

    fun String.nextLine() = repeatingDigitPattern.findAll(this).map { match ->
        val char = match.value.chunked(1).first()
        val size = match.value.length
        "$size$char"
    }.joinToString("")

    override fun part1(): Any? {
        var line = input.first()
        repeat(40) {
            line = line.nextLine()
        }
        return line.length
    }

    override fun part2(): Any? {
        var line = input.first()
        repeat(50) {
            line = line.nextLine()
        }
        return line.length
    }
}

fun main() {
    Puzzle10.runBoth()
}