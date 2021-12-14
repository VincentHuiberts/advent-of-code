package nl.tintie.aoc.y2021

import nl.tintie.aoc.AocPuzzle

object Puzzle10 : AocPuzzle(2021, 10) {
    fun findIllegalChar(line: String): Char? {
        line.toCharArray().fold(listOf<String>()) { acc, cur ->
            when (cur) {
                '(' -> acc + "()"
                ')' -> if (acc.last() == "()") acc.dropLast(1) else return cur
                '[' -> acc + "[]"
                ']' -> if (acc.last() == "[]") acc.dropLast(1) else return cur
                '{' -> acc + "{}"
                '}' -> if (acc.last() == "{}") acc.dropLast(1) else return cur
                '<' -> acc + "<>"
                '>' -> if (acc.last() == "<>") acc.dropLast(1) else return cur
                else -> throw Exception()
            }
        }
        return null
    }

    fun findMissingChars(line: String): List<Char> =
        line.toCharArray().fold(listOf<String>()) { acc, cur ->
            when (cur) {
                '(' -> acc + "()"
                ')' -> acc.dropLast(1)
                '[' -> acc + "[]"
                ']' -> acc.dropLast(1)
                '{' -> acc + "{}"
                '}' -> acc.dropLast(1)
                '<' -> acc + "<>"
                '>' -> acc.dropLast(1)
                else -> throw Exception()
            }
        }.reversed().map {
            when (it) {
                "()" -> ')'
                "[]" -> ']'
                "{}" -> '}'
                "<>" -> '>'
                else -> throw Exception()
            }
        }

    fun getScore(char: Char): Long = when (char) {
        ')' -> 3
        ']' -> 57
        '}' -> 1197
        '>' -> 25137
        else -> throw Exception()
    }

    fun getScorePart2(chars: List<Char>): Long =
        chars.map {
            when (it) {
                ')' -> 1
                ']' -> 2
                '}' -> 3
                '>' -> 4
                else -> throw Exception()
            }
        }.fold(0) { acc, curr ->
            acc * 5 + curr
        }

    override fun part1(): Any? {
        return input.mapNotNull { findIllegalChar(it)?.let(::getScore) }.reduce(Long::plus)
    }

    override fun part2(): Any? {
        return input.filter { findIllegalChar(it) == null }.mapNotNull { line ->
            findMissingChars(line).takeIf { it.isNotEmpty() }?.let { getScorePart2(it) }
        }.sorted().let { it[it.size / 2] }
    }
}

fun main() {
    Puzzle10.runPart2()
}