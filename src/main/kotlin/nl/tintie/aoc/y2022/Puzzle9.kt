package nl.tintie.aoc.y2022

import nl.tintie.aoc.AocPuzzle
import kotlin.math.abs

object Puzzle9 : AocPuzzle(2022, 9) {
//        override val testInputCodeBlockIndex = 3
//    override val testInputCodeBlockIndex = 7

    fun Pair<Int, Int>.distanceTo(other: Pair<Int, Int>): Int {
        val (x1, y1) = this
        val (x2, y2) = other
        return maxOf(abs(x1 - x2), abs(y1 - y2))
    }

    fun Pair<Int, Int>.moveTo(other: Pair<Int, Int>, otherFrom: Pair<Int, Int>): Pair<Int, Int> {
        val (x, y) = this
        val (x2, y2) = other
        return when (distanceTo(other)) {
            0, 1 -> this
            2 -> when {
                x == x2 -> x to y.stepCloser(y2)
                y == y2 -> x.stepCloser(x2) to y
                else -> x.stepCloser(x2) to y.stepCloser(y2)
            }
            else -> error("Kaboom")
        }
    }

    fun Int.stepCloser(other: Int) = if (this > other) {
        this - 1
    } else {
        this + 1
    }


    fun Pair<Int, Int>.move(dir: String): Pair<Int, Int> {
        val (x, y) = this
        return when (dir) {
            "U" -> x to y + 1
            "R" -> x + 1 to y
            "D" -> x to y - 1
            "L" -> x - 1 to y
            else -> error("boom")
        }
    }

    fun processSteps(headMoves: List<String>): Set<Pair<Int, Int>> {
        var head = 0 to 0
        var tail = 0 to 0
        val tailVisited = mutableSetOf(tail)
        headMoves.forEach { instr ->
            val (dir, dist) = instr.split(" ")
            repeat(dist.toInt()) {
                val newHead = head.move(dir)
                val newTail = tail.moveTo(newHead, head)
                head = newHead
                tail = newTail
                tailVisited += newTail
            }
        }
        return tailVisited
    }

    override fun part1(): Any? {
        return processSteps(input).size
    }

    fun processSteps2(headMoves: List<String>): Set<Pair<Int, Int>> {
        var knots = List(10) { 0 to 0 }
        val tailVisited = mutableSetOf<Pair<Int, Int>>()
        headMoves.forEach { instr ->
            val (dir, dist) = instr.split(" ")
            repeat(dist.toInt()) {
                val newHead = knots.first().move(dir)
                var previousKnot = newHead

                val newKnots = listOf(newHead) + knots.withIndex().drop(1).map { knot ->
                    knot.value.moveTo(previousKnot, knots[knot.index - 1]).also { previousKnot = it }
                }

                tailVisited += newKnots.last()
                knots = newKnots
            }
        }
        return tailVisited
    }

    override fun part2(): Any? {
        return processSteps2(input).size
    }
}

fun main() {
    Puzzle9.runBoth()
}