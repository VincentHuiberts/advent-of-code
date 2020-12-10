package nl.tintie.aoc.y2020

import nl.tintie.aoc.AocPuzzle
import nl.tintie.aoc.splitCollection

object Puzzle4 : AocPuzzle(2020, 4) {
    val fieldNames = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

    override fun part1(): Any? {
        return input.splitCollection { it.isEmpty() }.map { it.joinToString(" ") }.count { passportContent ->
            fieldNames.all { passportContent.contains(it) }
        }
    }

    val byrPattern = """\bbyr:(\d{4})\b""".toRegex() to { groups: List<String> -> groups[1].toInt() in 1920..2002 }
    val iyrPattern = """\biyr:(\d{4})\b""".toRegex() to { groups: List<String> -> groups[1].toInt() in 2010..2020 }
    val eyrPattern = """\beyr:(\d{4})\b""".toRegex() to { groups: List<String> -> groups[1].toInt() in 2020..2030 }
    val hgtPattern = """\bhgt:(\d+)(cm|in)\b""".toRegex() to { groups: List<String> ->
        val height = groups[1].toInt()
        if (groups[2] == "cm") height in 150..193
        else height in 59..76
    }
    val hclPattern = """\bhcl:#[0-9a-f]{6}\b""".toRegex() to { _: List<String> -> true }
    val eclPattern = """\becl:(amb|blu|brn|gry|grn|hzl|oth)\b""".toRegex() to { _: List<String> -> true }
    val pidPattern = """\bpid:\d{9}\b""".toRegex() to { _: List<String> -> true }

    val validations = listOf(
        byrPattern,
        iyrPattern,
        eyrPattern,
        hgtPattern,
        hclPattern,
        eclPattern,
        pidPattern
    )

    override fun part2(): Any? {
        return input.splitCollection { it.isEmpty() }.map { it.joinToString(" ") }.count { passportContent ->
            validations.all { (pattern, validFun) ->
                pattern.findAll(passportContent).toList().let { it.size == 1 && validFun(it[0].groupValues) }
            }
        }
    }
}

fun main() {
    Puzzle4.runBoth()
}