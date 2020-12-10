package nl.tintie.aoc.y2020

import nl.tintie.aoc.AocPuzzle
import nl.tintie.aoc.combinations

object Puzzle1 : AocPuzzle(2020, 1) {
    val intInput = input.map { it.toInt() }.asSequence()

    private fun Sequence<List<Int>>.findAndMultiply(): Int = find { nums ->
        nums.sum() == 2020
    }!!.reduce(Int::times)

    override fun part1() = intInput.combinations(2).findAndMultiply()

    override fun part2() = intInput.combinations(3).findAndMultiply()
}

fun main() {
    Puzzle1.runPart2()
}