package nl.tintie.aoc.y2015

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import nl.tintie.aoc.AocPuzzle

object Puzzle8 : AocPuzzle(2015, 8) {
    val stringCharPattern = """(\\x.{2}|\\.|.)""".toRegex()

    override fun part1(): Any? = input.map { line ->
        val strippedLine = line.drop(1).dropLast(1)
        val numOfChars = stringCharPattern.findAll(strippedLine).toList().size
        line.length - numOfChars
    }.sum()

    override fun part2(): Any? = input.map { line ->
        Json.encodeToString(line).length - line.length
    }.sum()
}

fun main() {
    Puzzle8.runBoth()
}