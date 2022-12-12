package nl.tintie.aoc.y2022

import nl.tintie.aoc.AocPuzzle

object Puzzle10 : AocPuzzle(2022, 10) {
//    override val testInputCodeBlockIndex = 1

    override fun part1(): Any? {
        val cycles = generateSequence(20) { it + 40 }.take(6).toList()
        var cycle = 0
        var signalSum = 0
        input.fold(1) { x, instr ->
            if (instr == "noop") {
                if (cycle + 1 in cycles) {
                    signalSum += (cycle + 1) * x
                    println("added ${(cycle + 1) * x}")
                }
                cycle++
                x
            } else {
                val amount = instr.split(" ")[1].toInt()
                if (cycle + 1 in cycles) {
                    signalSum += (cycle + 1) * x
                    println("added ${(cycle + 1) * x}")
                } else if (cycle + 2 in cycles) {
                    signalSum += (cycle + 2) * x
                    println("added ${(cycle + 2) * x}")
                }
                cycle += 2
                x + amount
            }
        }
        return signalSum
    }

    override fun part2(): Any? {
        var cycle = 0
        var outputBuffer = MutableList(240) { "." }
        input.fold(1) { x, instr ->
            val xRange = (x - 1)..(x + 1)
            if (instr == "noop") {
                if (cycle.rem(40) in xRange) {
                    outputBuffer[cycle] = "#"
                }
                cycle++
                x
            } else {
                repeat(2) {
                    if (cycle.rem(40) in xRange) {
                        outputBuffer[cycle] = "#"
                    }
                    cycle++
                }
                x + instr.split(" ")[1].toInt()
            }
        }
        return "\n" + outputBuffer.chunked(40).map { it.joinToString("") }.joinToString("\n")
    }

    override val part2Answer = """
####..##..#....#..#.###..#....####...##.
#....#..#.#....#..#.#..#.#....#.......#.
###..#....#....####.###..#....###.....#.
#....#.##.#....#..#.#..#.#....#.......#.
#....#..#.#....#..#.#..#.#....#....#..#.
####..###.####.#..#.###..####.#.....##.."""
}

fun main() {
    Puzzle10.runBoth()
}