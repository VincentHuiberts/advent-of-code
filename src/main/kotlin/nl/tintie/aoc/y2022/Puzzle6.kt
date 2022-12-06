package nl.tintie.aoc.y2022

import nl.tintie.aoc.AocPuzzle

object Puzzle6 : AocPuzzle(2022, 6) {
    fun startOfPacketMartker(line: String): Int {
        var start = 3
        var solution = -1
        line.windowed(4, 1, false) { block ->
            start++
            if (solution == -1 && block.toSet().size == block.toList().size) {
                solution = start
            }
        }
        return solution
    }

    override fun part1(): Any? {
        return startOfPacketMartker(input.first())
    }

    fun startOfPacketMartker2(line: String): Int {
        var start = 13
        var solution = -1
        line.windowed(14, 1, false) { block ->
            start++
            if (solution == -1 && block.toSet().size == block.toList().size) {
                solution = start
            }
        }
        return solution
    }

    override fun part2(): Any? {
        return startOfPacketMartker2(input.first())
    }
}

fun main() {
    Puzzle6.runBoth()
}