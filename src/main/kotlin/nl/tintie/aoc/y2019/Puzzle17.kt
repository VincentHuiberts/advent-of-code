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

    /**
     * L8, R12, R12, R10, R10, R12, R10, L8, R12, R12, R10, R10, R12, R10, L10, R10, L6, L10, R10, L6, R10
     * R12, R10, L8, R12, R12, R10, R10, R12, R10, L10, R10, L6
     */

    override fun part2(): Any? {
        val computer = IntComputer(intArrayInput)
        val print = false
        computer.program[0] = 2
        val funtions = """C,B,C,B,A,A,B,C,B,A
            |L,10,R,10,L,6
            |R,10,R,12,R,10
            |L,8,R,12,R,12,R,10
            |N
            |
        """.trimMargin()
        computer.input.addAll(funtions.map { it.toLong() })
        if (print) {
            while (!computer.finished) {
                print(computer.runToNextOutput().toChar())
            }
        }
        computer.runTillFinished()
        return computer.output.maxOrNull()
    }
}

fun main() {
    Puzzle17().runBoth()
}