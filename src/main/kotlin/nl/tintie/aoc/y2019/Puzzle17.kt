package nl.tintie.aoc.y2019

import nl.tintie.aoc.AocPuzzle

class Puzzle17 : AocPuzzle(2019, 17) {
    fun List<List<String>>.print() = forEach {
        println(it.joinToString(""))
    }

    override fun part1(): Any? {
        val computer = IntComputer(intArrayInput)
        val scaffold = mutableListOf<MutableList<String>>(mutableListOf())
        while (!computer.finished) {
            when (val char = computer.runToNextOutput().toChar()) {
                '\n' -> scaffold.add(mutableListOf())
                else -> scaffold.last().add(char.toString())
            }
        }
        scaffold.print()
        val (height, width) = scaffold.filter { it.size > 0 }.size to scaffold.first().size
        return (1 until height - 1).sumBy { y ->
            (1 until width - 1).sumBy { x ->
                val intersection = listOf(
                    x to y,
                    x - 1 to y,
                    x + 1 to y,
                    x to y - 1,
                    x to y + 1
                ).all { (x, y) ->
                    scaffold[y][x] == "#"
                }
                if (intersection) x * y else 0
            }
        }
    }
}

fun main() {
    Puzzle17().runPart1()
}