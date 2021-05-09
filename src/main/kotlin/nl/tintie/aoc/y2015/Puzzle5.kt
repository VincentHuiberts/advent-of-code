package nl.tintie.aoc.y2015

import nl.tintie.aoc.AocPuzzle

object Puzzle5: AocPuzzle(2015, 5) {
    val vowels = "aeiou".chunked(1)
    val badCombinations = listOf("ab", "cd", "pq", "xy")

    fun String.countVowels() = chunked(1).count { it in vowels }

    fun String.hasBadCombination() = windowed(2, 1).any { it in badCombinations }

    fun String.hasDoubleLetter() = windowed(2, 1 ).any { it[0] == it[1] }

    fun isGood(line: String): Boolean = line.run {
        !hasBadCombination() && hasDoubleLetter() && countVowels() >= 3
    }

    override fun part1(): Any? {
        return input.count(::isGood)
    }

    fun String.hasNonOverlappingPairs() =
        windowed(2, 1).groupBy { it }.any { it.value.size > 1 && replaceFirst(it.key, "").contains(it.key) }

    val sandwichPattern = """(.).\1""".toRegex()

    fun String.hasSandwichLetter() = sandwichPattern.containsMatchIn(this)

    fun isNice(line: String): Boolean {
        return line.hasSandwichLetter() && line.hasNonOverlappingPairs()
    }

    override fun part2(): Any? {
        return input.count(::isNice)
    }
}

fun main() {
    Puzzle5.runPart2()
}