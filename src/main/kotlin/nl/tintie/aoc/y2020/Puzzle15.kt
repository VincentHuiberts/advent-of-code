package nl.tintie.aoc.y2020

import nl.tintie.aoc.AocPuzzle

object Puzzle15 : AocPuzzle(2020, 15) {
    val startNums = input.first().split(",").map(String::toLong)

    private fun playGame(turns: Long): Long {
        val spokenNums = mutableMapOf<Long, Long>()
        var spoken = startNums[0]
        repeat(turns.toInt()) { turnI ->
            val turn = turnI + 1L
            val newSpoken = when {
                turnI < startNums.size -> startNums[turnI]
                spokenNums.contains(spoken) -> turn - spokenNums[spoken]!!
                else -> 0
            }

            spokenNums[spoken] = turn
            spoken = newSpoken
        }

        return spoken
    }

    override fun part1(): Any? = playGame(2020)
    override fun part2(): Any? = playGame(30000000)
}

fun main() {
    Puzzle15.runBoth()
}