package nl.tintie.aoc.y2020

import nl.tintie.aoc.AocPuzzle
import nl.tintie.aoc.MemoizedFunc

object Puzzle10 : AocPuzzle(2020, 10) {
    private val adapters = input.map { it.toLong() }.sorted().let { it + (it.last() + 3) }

    override fun part1(): Any? {
        val (_, outcome) = adapters.fold(0L to mutableMapOf<Long, Int>()) { (input, results), adapter ->
            val diff = adapter - input
            results[diff] = results.getOrDefault(diff, 0) + 1
            adapter to results
        }
        return outcome[1]!! * outcome[3]!!
    }

    val options = MemoizedFunc<Long, Long> { input: Long ->
        if (input == adapters.last()) 1 else {
            adapters.filter { it in (input + 1)..(input + 3) }.map { recurse(it) }.sum()
        }
    }

    override fun part2(): Any? {
        return options(0)
    }
}

fun main() {
    Puzzle10.runBoth()
}