package nl.tintie.aoc.y2015

import nl.tintie.aoc.AocPuzzle

object Puzzle11 : AocPuzzle(2015, 11) {
    val pairPattern = """(\w)\1""".toRegex()
    val illegalCharsPattern = """(i|o|l)""".toRegex()

    private fun isIncreasing(chars: String): Boolean {
        val first = chars[0]
        return charArrayOf(first, first + 1, first + 2).contentEquals(chars.toCharArray())
    }

    private fun containsPairs(chars: String, minNumOfMatches: Int): Boolean {
        return pairPattern.findAll(chars).toList().run {
            size >= minNumOfMatches && map { it.value }.distinct().size == size
        }
    }

    private fun containsNoIllegalChars(chars: String): Boolean {
        return !illegalCharsPattern.containsMatchIn(chars)
    }

    private fun allowedPassword(password: String): Boolean =
        containsNoIllegalChars(password) &&
                password.windowed(3, 1, false).any(::isIncreasing) &&
                containsPairs(password, 2)

    private fun incrementPassword(password: String): String {
        val chars = password.toCharArray()
        fun increment(index: Int): String {
            if (chars[index] == 'z') {
                chars[index] = 'a'
                increment(index - 1)
            } else {
                chars[index] = chars[index] + 1
            }
            return String(chars)
        }
        return increment(password.length - 1)
    }

    tailrec fun findNextValidPw(password: String): String {
        val nextPw = incrementPassword(password)
        return if (allowedPassword(nextPw)) nextPw else findNextValidPw(nextPw)
    }

    override fun part1(): Any? = findNextValidPw(input.first())
    override fun part2(): Any? = findNextValidPw(input.first()).let(::findNextValidPw)
}

fun main() {
    Puzzle11.runPart2()
}