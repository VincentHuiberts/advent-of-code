package nl.tintie.aoc.y2019

import nl.tintie.aoc.AocPuzzle
import nl.tintie.aoc.getRepeating
import java.time.LocalDateTime

class Puzzle16 : AocPuzzle(2019, 16) {
    val nums = input.first().chunked(1).map(String::toInt)

    val basePattern = listOf(0, 1, 0, -1)

    fun patternFor(n: Int) = basePattern.flatMap { num ->
        List(n) { num }
    }

    fun List<Int>.nextPhase(): List<Int> {
        return mapIndexed { outputI, _ ->
            val pattern = patternFor(outputI + 1)
            this.mapIndexed { i, num -> num * pattern.getRepeating(i + 1) }.sum().toString().chunked(1).last().toInt()
        }
    }

    override fun part1(): Any? {
        return (0 until 100).fold(nums) { acc, _ ->
            acc.nextPhase()
        }.take(8).joinToString("")
    }
}

fun main() {
    Puzzle16().runPart1()
}