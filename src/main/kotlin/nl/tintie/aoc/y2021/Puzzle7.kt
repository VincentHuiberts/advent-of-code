package nl.tintie.aoc.y2021

import nl.tintie.aoc.AocPuzzle
import nl.tintie.aoc.MemoizedFunc
import kotlin.math.abs

object Puzzle7 : AocPuzzle(2021, 7) {
    val positions = input.first().split(",").map { it.toInt() }

    private fun List<Int>.cost(position: Int) = sumOf { abs(it - position) }

    override fun part1(): Any? {
        val (min, max) = positions.sorted().run { first() to last() }
        return (min..max).minOfOrNull { positions.cost(it) }
    }

    tailrec fun moveCostPt2(n: Int): Long {
        return if (n <= 1) n.toLong() else n + moveCostPt2(n-1)
    }

    val memoizedMoveCost = MemoizedFunc<Int, Long> {
        moveCostPt2(it)
    }

    private fun List<Int>.costPt2(position: Int) = sumOf {  memoizedMoveCost(abs(it - position)) }

    override fun part2(): Any? {
        val (min, max) = positions.sorted().run { first() to last() }
        return (min..max).minOfOrNull { positions.costPt2(it) }
    }
}

fun main() {
    Puzzle7.runPart2()
}
