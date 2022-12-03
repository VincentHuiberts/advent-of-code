package nl.tintie.aoc.y2022

import nl.tintie.aoc.AocPuzzle

object Puzzle3 : AocPuzzle(2022, 3) {
//    override val input: List<String>
//        get() = """vJrwpWtwJgWrhcsFMMfFFhFp
//jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
//PmmdzqPrVvPwwTWBwg
//wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
//ttgJtRGJQctTZtZT
//CrZsJsPPZsGzwwsLwLmpwMDw""".lines()

    fun getDuplicate(line: String): Char {
        val (left, right) = line.chunked(line.length / 2)
        val duplicate = left.toCharArray().intersect(right.toSet())
        return duplicate.first()
    }

    val allChars = (('a'..'z') + ('A'..'Z')).toCharArray()

    fun getPriority(char: Char): Int = allChars.indexOf(char) + 1

    override fun part1(): Any? {
        return input.map(::getDuplicate).sumOf(::getPriority)
    }

    fun getDuplicatePart2(line1: String, line2: String, line3: String): Char {
        return line1.toCharArray().intersect(line2.toSet()).intersect(line3.toSet()).first()
    }

    override fun part2(): Any? {
        return input.windowed(3, 3, false).sumOf { (line1, line2, line3) ->
            getPriority(getDuplicatePart2(line1, line2, line3))
        }
    }
}

fun main() {
    Puzzle3.runBoth()
}