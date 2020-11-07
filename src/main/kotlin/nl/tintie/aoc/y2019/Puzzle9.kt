package nl.tintie.aoc.y2019

import nl.tintie.aoc.AocPuzzle

class Puzzle9 : AocPuzzle(2019, 9) {
    override fun part1(): Any? {
        val machine = IntComputer(intArrayInput)
        machine.input.add(1)
        machine.runTillFinished()
        return machine.output
    }

    override fun part2(): Any? {
        val machine = IntComputer(intArrayInput)
        machine.input.add(2)
        machine.runTillFinished()
        return machine.output
    }
}

fun main() {
    Puzzle9().runBoth()
}