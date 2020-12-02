package nl.tintie.aoc.y2020

import nl.tintie.aoc.AocPuzzle
import nl.tintie.aoc.combinationsFiltered

class Puzzle1 : AocPuzzle(2020, 1) {
    val intInput = input.map { it.toInt() }.asSequence()

    private fun Sequence<List<Int>>.findAndMultiply(): Int = find { nums ->
        nums.sum() == 2020
    }!!.reduce(Int::times)

    override fun part1() = intInput.combinationsFiltered(2, listOf({it < 1010}, {it >= 1010})).findAndMultiply()

    override fun part2() = intInput.combinationsFiltered(3, listOf({it <= 1010}, {it < 1010})).findAndMultiply()
}

fun main() {
    Puzzle1().runBoth()
}