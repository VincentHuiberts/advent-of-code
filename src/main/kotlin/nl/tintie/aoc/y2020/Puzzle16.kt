package nl.tintie.aoc.y2020

import nl.tintie.aoc.AocPuzzle
import nl.tintie.aoc.splitCollection

object Puzzle16 : AocPuzzle(2020, 16) {
    class Rule(
        val name: String,
        val range1: IntRange,
        val range2: IntRange
    ) {
        fun ruleApplies(value: Int) = value in range1 || value in range2
    }

    val groups = input.splitCollection { it == "" }

    val rules = groups[0].map { line ->
        val (name, ranges) = line.split(": ")
        val (range1, range2) = ranges.split(" or ").map {
            val (lower, upper) = it.split("-").map(String::toInt)
            lower..upper
        }
        Rule(name, range1, range2)
    }
    val myTicket = groups[1].last().split(",").map(String::toInt)

    val nearbyTickets = groups[2].drop(1).map { line ->
        line.split(",").map(String::toInt)
    }

    override fun part1(): Any? {
        return nearbyTickets.map { ticket ->
            ticket.find { num ->
                rules.none { it.ruleApplies(num) }
            } ?: 0
        }.sum()
    }

    private fun matchingIdxs(rule: Rule): List<Int> {
        val maxIdx = nearbyTickets.maxOf { it.size }
        return (0 until maxIdx).filter { idx ->
            (nearbyTickets.plusElement(myTicket)).filter { ticket ->
                ticket.all { num ->
                    rules.any { it.ruleApplies(num) }
                }
            }.map { it[idx] }.all { rule.ruleApplies(it) }
        }
    }

    override fun part2(): Any {
        val ruleIdxs = mutableMapOf<Rule, MutableList<Int>>()
        rules
            .map { it to matchingIdxs(it) }
            .map { (rule, idxs) -> ruleIdxs[rule] = idxs.toMutableList() }

        while(!ruleIdxs.filter { (k, _) -> k.name.startsWith("departure") }.all { (_, v) -> v.size == 1 }) {
            val uniqueIdxs = ruleIdxs.values.filter { it.size == 1 }.map { it[0] }
            ruleIdxs.values.filter { it.size > 1 }.forEach { it.removeAll(uniqueIdxs) }
        }

        return ruleIdxs.filterKeys { it.name.startsWith("departure") }
            .map { it.value.first() }
            .map { myTicket[it].toLong() }
            .reduce(Long::times)
    }
}

fun main() {
    Puzzle16.runBoth()
}
