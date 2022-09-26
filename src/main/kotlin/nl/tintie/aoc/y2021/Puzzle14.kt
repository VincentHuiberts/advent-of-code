package nl.tintie.aoc.y2021

import nl.tintie.aoc.AocPuzzle
import java.time.Instant

object Puzzle14 : AocPuzzle(2021, 14) {
    val template = input.first()
    val rules = input.drop(2).map {
        val (pair, insert) = it.split(" -> ")
        pair to insert
    }.toMap()

    fun Map<String, Long>.stepTimes(times: Int): Map<String, Long> {
        return (1..times).fold(this) { acc, step ->
            println("${Instant.now()} Step $step")
            acc.step()
        }
    }

    fun String.countPairs(): Map<String, Long> =
        windowed(2, 1).groupBy { it }.mapValues { (_, v) -> v.size.toLong() }

    fun Map<String, Long>.step(): Map<String, Long> {
        val newState = mutableMapOf<String, Long>()
        entries.forEach { (pair, count) ->
            val newPair1 = pair.first() + rules[pair]!!
            val newPair2 = rules[pair]!! + pair.last()
            newState[newPair1] = newState.getOrDefault(newPair1, 0) + count
            newState[newPair2] = newState.getOrDefault(newPair2, 0) + count
        }
        return newState.toMap()
    }

    fun Map<String, Long>.countByChars(): Map<String, Long> {
        val counts = mutableMapOf<String, Long>()
        counts.put(template.windowed(1).first(), 1)
        forEach { (pair, count) ->
            val char = pair.drop(1)
            counts[char] = counts.getOrDefault(char, 0) + count
        }
        return counts
    }

    override fun part1(): Any? {
        val charCount = template.countPairs().stepTimes(10).countByChars()
        return charCount.values.maxOrNull()!! - charCount.values.minOrNull()!!
    }

    override fun part2(): Any? {
        val charCount = template.countPairs().stepTimes(40).countByChars()
        return charCount.values.maxOrNull()!! - charCount.values.minOrNull()!!
    }
}

fun main() {
    Puzzle14.runBoth()
}