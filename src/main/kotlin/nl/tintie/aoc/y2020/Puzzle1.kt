package nl.tintie.aoc.y2020

import nl.tintie.aoc.AocPuzzle

class Puzzle1 : AocPuzzle(2020, 1) {
    val intInput = input.map { it.toInt() }

    override fun part1(): Any? {
        val options = intInput.flatMapIndexed { i, num1 ->
            intInput.drop(i + 1).map { num2 ->
                num1 to num2
            }
        }
        return options.find { (num1, num2) ->
            num1 + num2 == 2020
        }!!.let { (num1, num2) -> num1 * num2 }
    }

    override fun part2(): Any? {
        val options = intInput.flatMapIndexed { i, num1 ->
            intInput.drop(i + 1).flatMapIndexed {i2, num2 ->
                intInput.drop(i + i2 + 1).map { num3 ->
                    Triple(num1, num2, num3)
                }
            }
        }
        return options.find { (num1, num2, num3) ->
            num1 + num2 + num3 == 2020
        }!!.let { (num1, num2, num3) -> num1 * num2 * num3 }
    }
}

fun main() {
    Puzzle1().runBoth()
}