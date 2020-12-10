package nl.tintie.aoc.y2020

import nl.tintie.aoc.AocPuzzle

object Puzzle10 : AocPuzzle(2020, 10) {
    val adapters = input.map { it.toLong() }.sorted().let { it + (it.last() + 3) }

    override fun part1(): Any? {
        val (_, outcome) = adapters.fold(0L to mutableMapOf<Long, Long>()) { (input, results), adapter ->
            val diff = adapter - input
            results[diff] = results.getOrDefault(diff, 0) + 1L
            adapter to results
        }
        return outcome[1]!! * outcome[3]!!
    }

    private val results = mutableMapOf<Long, Long>()

    private fun options(input: Long): Long {
        return results.getOrPut(input) {
            if (input == adapters.last()) 1 else {
                adapters.filter { it in (input + 1)..(input + 3) }
                    .let { options ->
                        options.map { options(it) }.sum()
                    }
            }
        }
    }

    override fun part2(): Any? {
        return options(0)
    }
}

fun main() {
    Puzzle10.runBoth()
}