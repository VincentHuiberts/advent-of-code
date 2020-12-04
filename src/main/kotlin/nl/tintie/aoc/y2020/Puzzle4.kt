package nl.tintie.aoc.y2020

import nl.tintie.aoc.AocPuzzle

class Puzzle4 : AocPuzzle(2020, 4) {
    val fieldNames = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

    override fun part1(): Any? {
        var lines = input
        var validPassports = 0
        while (lines.isNotEmpty()) {
            val passportLines = lines.takeWhile { it.isNotBlank() }
            lines = lines.drop(passportLines.size + 1)
            val passportContent = passportLines.joinToString(" ")
            if (fieldNames.all { passportContent.contains(it) }) validPassports++
        }
        return validPassports
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
        var lines = input
        var validPassports = 0
        while (lines.isNotEmpty()) {
            val passportLines = lines.takeWhile { it.isNotBlank() }
            lines = lines.drop(passportLines.size + 1)
            val passportContent = passportLines.joinToString(" ")
            val valid = validations.all { (pattern, validFun) ->
                pattern.findAll(passportContent).toList().let { it.size == 1 && validFun(it[0].groupValues) }
            }
            if (valid) validPassports++
        }
        return validPassports
    }
}

fun main() {
    Puzzle4().runBoth()
}