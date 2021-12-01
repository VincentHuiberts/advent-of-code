package nl.tintie.aoc.y2016

import nl.tintie.aoc.AocPuzzle

object Puzzle3 : AocPuzzle(2016, 3) {
    val linePattern = """(\d+)\s+(\d+)\s+(\d+)""".toRegex()
    val sides = input.map { line -> linePattern.find(line)!!.groupValues.drop(1).map { it.toInt() } }

    override fun part1(): Any? {
        return sides.count { sides ->
            sides.sum() > (sides.maxOf { it } * 2)
        }
    }

    override fun part2(): Any? {
        return sides.windowed(3, 3, false).flatMap {
            listOf(
                it.map { it[0] },
                it.map { it[1] },
                it.map { it[2] }
            )
        }.count{ sides ->
            sides.sum() > (sides.maxOf { it } * 2)
        }
    }
}

fun main() {
    Puzzle3.runPart2()
}