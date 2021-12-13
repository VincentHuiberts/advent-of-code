package nl.tintie.aoc.y2021

import nl.tintie.aoc.AocPuzzle
import nl.tintie.aoc.MemoizedFunc

object Puzzle6 : AocPuzzle(2021, 6) {
    val state = input.first().split(",").map { it.toInt() }

    fun Int.numberOfFishAfter(days: Int): Long {
        return (0 until days).fold(mapOf(this to 1L)) { fish, _ ->
            val newFish = mutableMapOf<Int, Long>()
            fish.entries.forEach { (k, v) ->
                when (k) {
                    0 -> {
                        newFish.merge(6, v, Long::plus)
                        newFish.merge(8, v, Long::plus)
                    }
                    else -> newFish.merge(k - 1, v, Long::plus)
                }
            }
            newFish
        }.entries.map { (_, v) -> v }.sum()
    }

    val memoizedNumOfFish = MemoizedFunc<Pair<Int, Int>, Long> { (input, days) ->
        input.numberOfFishAfter(days)
    }

    override fun part1(): Any? {
        return state.sumOf { memoizedNumOfFish(it to 80) }
    }

    override fun part2(): Any? {
        return state.sumOf { memoizedNumOfFish(it to 256) }
    }
}

fun main() {
    Puzzle6.runBoth()
}
