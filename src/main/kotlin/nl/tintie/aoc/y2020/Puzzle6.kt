package nl.tintie.aoc.y2020

import nl.tintie.aoc.AocPuzzle
import nl.tintie.aoc.splitCollection

class Puzzle6: AocPuzzle(2020, 6) {
    override fun part1(): Any? {
        return input.splitCollection { it.isEmpty() }.sumBy { group ->
            group.joinToString ("").chunked(1).filter{ it in "a".."z" }.distinct().size
        }
    }

    override fun part2(): Any? {
        return input.splitCollection { it.isEmpty() }.sumBy { group ->
            ('a'..'z').filter { char -> group.all { person -> person.contains(char)} }.size
        }
    }
}

fun main() {
    Puzzle6().runBoth()
}