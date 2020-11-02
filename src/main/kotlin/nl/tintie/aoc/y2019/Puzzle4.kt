package nl.tintie.aoc.y2019

import nl.tintie.aoc.AocPuzzle

class Puzzle4 : AocPuzzle(2019, 4) {
    private fun numberOk(number: Int, groupAssert: (List<String>) -> Boolean): Boolean {
        val chars = number.toString()
            .split("").drop(1).take(6)

        val double = chars
            .groupBy { it }
            .values
            .any(groupAssert)

        val sorted = chars.sorted() == chars
        return double && sorted
    }


    override fun part1(): Any? {
        val (from, to) = input.first().split("-").map { it.toInt() }
        return (from..to).count {
            numberOk(it) {
                it.size > 1
            }
        }
    }

    override fun part2(): Any? {
        val (from, to) = input.first().split("-").map { it.toInt() }
        return (from..to).count {
            numberOk(it) {
                it.size == 2
            }
        }
    }
}

fun main() {
    Puzzle4().runPart2()
}