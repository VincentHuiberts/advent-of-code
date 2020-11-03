package nl.tintie.aoc.y2019

import nl.tintie.aoc.AocPuzzle

class Puzzle5: AocPuzzle(2019, 5) {
    override fun part1(): Any? {
        val machine = StateMachine(intArrayInput, 1)
        machine.run()
        return machine.output
    }

    override fun part2(): Any? {
        val machine = StateMachine(intArrayInput, 5)
        machine.run()
        return machine.output
    }
}

fun main() {
    Puzzle5().runPart2()
}