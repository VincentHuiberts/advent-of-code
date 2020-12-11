package nl.tintie.aoc.y2020

import nl.tintie.aoc.AocPuzzle
import kotlin.math.abs

object Puzzle11 : AocPuzzle(2020, 11) {
    val seats = input.map { line -> line.chunked(1) }

    private fun List<List<String>>.nextPhasePt1() = mapIndexed { y, row ->
        val rowBefore = if (y > 0) get(y - 1) else null
        val rowAfter = if ((y + 1) < size) get(y + 1) else null
        row.mapIndexed { x, seat ->
            val seatLeftX = if (x > 0) x - 1 else null
            val seatRightX = if ((x + 1) < row.size) x + 1 else null

            val filedSeats = listOfNotNull(rowBefore, row, rowAfter).sumBy { row2 ->
                listOfNotNull(seatLeftX, x, seatRightX).count { seatX ->
                    row2[seatX] == "#"
                }
            }

            when (seat) {
                "L" -> if (filedSeats == 0) "#" else "L"
                "#" -> if (filedSeats >= 5) "L" else "#" // In including itself
                else -> seat
            }
        }
    }

    fun List<List<String>>.print() = map { it.joinToString("") }.forEach(::println)

    fun List<List<String>>.countOccupied() = sumBy { row -> row.count { it == "#" } }

    override fun part1(): Any? {
        var previousState = seats
        var nextState = previousState.nextPhasePt1()
        while (previousState != nextState) {
            previousState = nextState
            nextState = nextState.nextPhasePt1()
        }
        return nextState.countOccupied()
    }

     private fun List<List<String>>.findInDirection(point: Pair<Int, Int>, direction: String): String? {
        val nextPoint: (Pair<Int, Int>) -> Pair<Pair<Int, Int>, String?> = when (direction) {
            "n" -> { (x, y) -> x to (y - 1) to getOrNull(y - 1)?.getOrNull(x) }
            "ne" -> { (x, y) -> (x + 1) to (y - 1) to getOrNull(y - 1)?.getOrNull(x + 1) }
            "e" -> { (x, y) -> (x + 1) to y to getOrNull(y)?.getOrNull(x + 1) }
            "se" -> { (x, y) -> (x + 1) to (y + 1) to getOrNull(y + 1)?.getOrNull(x + 1) }
            "s" -> { (x, y) -> x to (y + 1) to getOrNull(y + 1)?.getOrNull(x) }
            "sw" -> { (x, y) -> (x - 1) to (y + 1) to getOrNull(y + 1)?.getOrNull(x - 1) }
            "w" -> { (x, y) -> (x - 1) to y to getOrNull(y)?.getOrNull(x - 1) }
            "nw" -> { (x, y) -> (x - 1) to (y - 1) to getOrNull(y - 1)?.getOrNull(x - 1) }
            else -> error("Unknown dir $direction")
        }
        var currentPoint = point
        while (true) {
            val (newPoint, value) = nextPoint(currentPoint)
            currentPoint = newPoint
            if (value != ".") return value
        }
    }

    private fun List<List<String>>.getFirstVisibleSeats(point: Pair<Int, Int>): List<String> =
        listOf("n", "ne", "e", "se", "s", "sw", "w", "nw").mapNotNull { findInDirection(point, it) }


    private fun List<List<String>>.nextPhasePt2(): List<List<String>> {
        return mapIndexed { y, row ->
            row.mapIndexed { x, seat ->
                val visible = getFirstVisibleSeats(x to y)
                when (seat) {
                    "L" -> if (visible.none{ it == "#" }) "#" else "L"
                    "#" -> if (visible.count { it == "#" } >= 5) "L" else "#"
                    else -> seat
                }
            }
        }
    }

    override fun part2(): Any? {
        var previousState = seats
        var nextState = previousState.nextPhasePt2()
        while (previousState != nextState) {
            previousState = nextState
            nextState = nextState.nextPhasePt2()
        }
        return nextState.countOccupied()
    }
}

fun main() {
    Puzzle11.runBoth()
}