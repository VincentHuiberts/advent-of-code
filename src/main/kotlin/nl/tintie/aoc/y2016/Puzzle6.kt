package nl.tintie.aoc.y2016

import nl.tintie.aoc.AocPuzzle
import java.math.BigInteger
import java.security.MessageDigest

object Puzzle6 : AocPuzzle(2016, 6) {
    override fun part1(): Any? {
        return input.fold(listOf<String>()) { cols, cur ->
            cur.toCharArray().mapIndexed { i, c -> cols.getOrElse(i){""} + c }
        }.map { it.groupBy { it }.entries.sortedByDescending { it.value.size }.first().key }
            .let { String(it.toCharArray()) }
    }

    override fun part2(): Any? {
        return input.fold(listOf<String>()) { cols, cur ->
            cur.toCharArray().mapIndexed { i, c -> cols.getOrElse(i){""} + c }
        }.map { it.groupBy { it }.entries.sortedBy { it.value.size }.first().key }
            .let { String(it.toCharArray()) }
    }
}

fun main() {
    Puzzle6.runPart2()
}