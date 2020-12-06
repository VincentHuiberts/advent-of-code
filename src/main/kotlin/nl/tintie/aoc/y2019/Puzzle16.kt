package nl.tintie.aoc.y2019

import nl.tintie.aoc.AocPuzzle
import nl.tintie.aoc.getRepeating
import kotlin.math.abs

class Puzzle16 : AocPuzzle(2019, 16) {
    val nums = input.first().chunked(1).map(String::toInt)

    val basePattern = listOf(0, 1, 0, -1)

    fun patternFor(n: Int) = basePattern.flatMap { num ->
        List(n) { num }
    }

    fun List<Int>.nextPhase(): List<Int> {
        return mapIndexed { outputI, _ ->
            val pattern = patternFor(outputI + 1)
            val sum = this.mapIndexed { i, num -> num * pattern.getRepeating(i + 1) }.sum()
            abs(sum % 10)
        }
    }

    override fun part1(): Any? {
        return (0 until 100).fold(nums) { acc, _ ->
            acc.nextPhase()
        }.take(8).joinToString("")
    }

    override fun part2(): Any? {
        val offset = nums.take(7).joinToString("").toInt()
        val newLength = 10000 * nums.size
        val value = (offset until newLength).map { nums.getRepeating(it) }.toIntArray()
        repeat(100) {
            value.indices.reversed().fold(0) { acc, i ->
                (abs(acc + value[i]) % 10).also { value[i] = it }
            }
        }
        return value.take(8).joinToString("")
    }
}

fun main() {
    Puzzle16().runBoth()
}