package nl.tintie.aoc.y2020

import nl.tintie.aoc.AocPuzzle

class Puzzle2 : AocPuzzle(2020, 2) {
    private val passwordPattern = """(\d+)-(\d+) (\w): (\w+)""".toRegex()

    private data class Password(
        val min: Int,
        val max: Int,
        val char: String,
        val value: String
    ) {
        fun isValidPt1(): Boolean =
            value.chunked(1).count { it == char } in min..max


        fun isValidPt2(): Boolean {
            val a = value.chunked(1)[min - 1] == char
            val b = value.chunked(1)[max - 1] == char
            return a xor b
        }
    }

    private val passwords = input.map { input ->
        val (_, min, max, char, value) = passwordPattern.find(input)!!.groupValues
        Password(min.toInt(), max.toInt(), char, value)
    }

    override fun part1(): Any? {
        return passwords.count { it.isValidPt1() }
    }

    override fun part2(): Any? {
        return passwords.count { it.isValidPt2() }
    }
}

fun main() {
    Puzzle2().runBoth()
}