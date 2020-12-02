package nl.tintie.aoc.y2019

import nl.tintie.aoc.AocPuzzle

class Puzzle5: AocPuzzle(2019, 5) {
    override fun part1(): Any? {
        val machine = IntComputer(intArrayInput, listOf(1))
        machine.runTillFinished()
        return machine.output.last()
    }

    override fun part2(): Any? {
        val machine = IntComputer(intArrayInput, listOf(5))
        machine.runTillFinished()
        return machine.output.last()
    }
}

fun main() {
    Puzzle5().runPart2()
}