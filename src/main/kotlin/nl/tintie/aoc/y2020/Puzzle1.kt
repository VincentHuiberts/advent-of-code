package nl.tintie.aoc.y2020

import nl.tintie.aoc.AocPuzzle

class Puzzle1 : AocPuzzle(2020, 1) {
    val intInput = input.map { it.toInt() }.asSequence()

    private fun Sequence<List<Int>>.findAndMultiply(): Int = find { nums ->
        nums.sum() == 2020
    }!!.reduce(Int::times)

    override fun part1(): Any? {
        return intInput.asSequence().flatMapIndexed { i, num1 ->
            intInput.drop(i + 1).map { num2 ->
                listOf(num1, num2)
            }
        }.findAndMultiply()
    }

    override fun part2(): Any? {
        return intInput.flatMapIndexed { i, num1 ->
            intInput.drop(i + 1).flatMapIndexed { i2, num2 ->
                intInput.drop(i + i2 + 1).map { num3 ->
                    listOf(num1, num2, num3)
                }
            }
        }.findAndMultiply()
    }
}

fun main() {
    Puzzle1().runBoth()
}