package nl.tintie.aoc.y2022

import nl.tintie.aoc.AocPuzzle

object Puzzle2 : AocPuzzle(2022, 2) {
//    override val input: List<String>
//        get() = """A Y
//B X
//C Z""".lines()

    private fun getScorePart1(left: String, right: String) = when (left) {
        "A" -> when (right) {
            "X" -> 3 + 1
            "Y" -> 6 + 2
            "Z" -> 0 + 3
            else -> error("impossible")
        }
        "B" -> when (right) {
            "X" -> 0 + 1
            "Y" -> 3 + 2
            "Z" -> 6 + 3
            else -> error("impossible")
        }
        "C" -> when (right) {
            "X" -> 6 + 1
            "Y" -> 0 + 2
            "Z" -> 3 + 3
            else -> error("impossible")
        }
        else -> error("impossible")
    }

    override fun part1(): Any? {
        return input.sumOf {
            val (left, right) = it.split(" ")
            getScorePart1(left, right)
        }
    }

    private fun getScorePart2(left: String, right: String) = when (left) {
        "A" -> when (right) {
            "X" -> 0 + 3
            "Y" -> 3 + 1
            "Z" -> 6 + 2
            else -> error("impossible")
        }
        "B" -> when (right) {
            "X" -> 0 + 1
            "Y" -> 3 + 2
            "Z" -> 6 + 3
            else -> error("impossible")
        }
        "C" -> when (right) {
            "X" -> 0 + 2
            "Y" -> 3 + 3
            "Z" -> 6 + 1
            else -> error("impossible")
        }
        else -> error("impossible")
    }

    override fun part2(): Any? {
        return input.sumOf {
            val (left, right) = it.split(" ")
            getScorePart2(left, right)
        }
    }
}

fun main() {
    Puzzle2.runBoth()
}