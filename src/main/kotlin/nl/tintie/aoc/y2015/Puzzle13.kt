package nl.tintie.aoc.y2015

import nl.tintie.aoc.AocPuzzle
import nl.tintie.aoc.allArrangements

object Puzzle13 : AocPuzzle(2015, 13) {
    enum class StatementType {GAIN, LOSE}

    class Statement(
        val p1: String,
        val p2: String,
        val type: StatementType,
        val amount: Int
    ) {
        val diff = when(type) {
            StatementType.LOSE -> amount * -1
            else -> amount
        }
    }

    val linePattern = """(\w+) would (gain|lose) (\d+) happiness units by sitting next to (\w+)\.""".toRegex()

    val statements = input.map { line ->
        val (_, p1, type, amount, p2) = linePattern.matchEntire(line)!!.groupValues
        Statement(
            p1 = p1,
            p2 = p2,
            type = when(type) {
                "gain" -> StatementType.GAIN
                else -> StatementType.LOSE
            },
            amount = amount.toInt()
        )
    }

    val allP = statements.flatMap { listOf(it.p1, it.p2) }.distinct()

    fun getHappiness(people: List<String>): Int {
        val pairs = people.windowed(2, 1, false) + listOf(listOf(people.last(), people.first()))
        return pairs.sumOf { (p1, p2) ->
            statements
                .filter { (it.p1 == p1 && it.p2 == p2) || (it.p2 == p1 && it.p1 == p2) }
                .sumOf { it.diff }
        }
    }

    override fun part1(): Any? = allArrangements(allP).maxOfOrNull(::getHappiness)
    override fun part2(): Any? = allArrangements(allP + "me").maxOfOrNull(::getHappiness)
}

fun main() {
    Puzzle13.runBoth()
}