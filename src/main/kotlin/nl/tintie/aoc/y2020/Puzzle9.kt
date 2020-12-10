package nl.tintie.aoc.y2020

import nl.tintie.aoc.AocPuzzle
import nl.tintie.aoc.combinations

object Puzzle9: AocPuzzle(2020, 9) {
    val nums = input.map { it.toLong() }

    override fun part1(): Any? {
        val preambleSize = 25
        return nums.mapIndexed { i, num -> i to num }
            .filter { (i, _) -> i >= preambleSize }
            .find { (i, currentNum) ->
                nums.subList(i - preambleSize, i).asSequence().combinations(2).none { it.sum() == currentNum }
            }?.second
    }

    override fun part2(): Any? {
        val contagiousNum = part1() as Long
        var numsUsed = 2
        while(true) {
            val found = nums.windowed(numsUsed, 1).find { it.sum() == contagiousNum }
            if(found != null) {
                return found.minOrNull()!! + found.maxOrNull()!!
            } else {
              numsUsed++
            }
        }
    }
}

fun main() {
    Puzzle9.runBoth()
}