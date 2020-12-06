package nl.tintie.aoc.y2020

import nl.tintie.aoc.AocPuzzle

class Puzzle6: AocPuzzle(2020, 6) {
    override fun part1(): Any? {
        var lines = input
        val uniqueAnswers = mutableListOf<Int>()
        while (lines.isNotEmpty()) {
            val group = lines.takeWhile { it.isNotBlank() }
            lines = lines.drop(group.size + 1)
            uniqueAnswers.add(
                group.joinToString ("").chunked(1).filter{ it in "a".."z" }.distinct().size
            )
        }
        return uniqueAnswers.sum()
    }

    override fun part2(): Any? {
        var lines = input
        val allSameAnswers = mutableListOf<Int>()
        while (lines.isNotEmpty()) {
            val group = lines.takeWhile { it.isNotBlank() }
            lines = lines.drop(group.size + 1)
            val allAnswered = ('a'..'z').filter { char -> group.all { person -> person.contains(char)} }.size
            allSameAnswers.add(allAnswered)
        }
        return allSameAnswers.sum()
    }
}

fun main() {
    Puzzle6().runBoth()
}