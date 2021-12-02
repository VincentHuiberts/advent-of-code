package nl.tintie.aoc.y2021

import nl.tintie.aoc.AocPuzzle

object Puzzle2 : AocPuzzle(2021, 2) {
    private val commands = input.map {
        val (dir, amount) = it.split(" ")
        dir to amount.toInt()
    }

    override fun part1(): Any? {
        return commands.fold(0 to 0) { (x, y), (dir, amount) ->
            when (dir) {
                "forward" -> (x + amount) to y
                "down" -> x to (y + amount)
                "up" -> x to (y - amount)
                else -> throw Exception()
            }
        }.let { (x, y) -> x * y }
    }

    override fun part2(): Any? {
        return commands.fold(Triple(0, 0, 0)) { (x, y, aim), (dir, amount) ->
            when (dir) {
                "forward" ->    Triple(x + amount, y + aim * amount, aim)
                "down" ->       Triple(x, y, aim + amount)
                "up" ->         Triple(x, y, aim - amount)
                else -> throw Exception()
            }
        }.let { (x, y) -> x * y }
    }
}

fun main() {
    Puzzle2.runBoth()
}