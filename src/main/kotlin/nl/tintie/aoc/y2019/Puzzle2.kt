package nl.tintie.aoc.y2019

import nl.tintie.aoc.AocPuzzle

class Puzzle2 : AocPuzzle(2019, 2) {
    override fun part1(): Any {
        val machine = IntComputer(intArrayInput)
        machine.program[1] = 12
        machine.program[2] = 2
        machine.runTillFinished()
        return machine.program[0]
    }

    override fun part2(): Any {
        val desiredOutput = 19690720L
        (0..100L).forEach{noun ->
            (0..100L).forEach { verb ->
                val machine = IntComputer(intArrayInput)
                machine.program[1] = noun
                machine.program[2] = verb
                machine.runTillFinished()
                if(machine.program[0] == desiredOutput) return 100 * noun + verb
            }
        }
        return "No cigar"
    }
}

fun main() {
    Puzzle2().runBoth()
}