package nl.tintie.aoc.y2020

import nl.tintie.aoc.AocPuzzle

class Puzzle5 : AocPuzzle(2020, 5) {
    fun getRow(boardingPass: String): Int {
        val (lowerRange, upperRange) = boardingPass
            .chunked(1)
            .take(7)
            .fold(0 to 127) { (lower, upper), region ->
                val middle = (upper - lower) / 2 + lower
                val roundedDown = ((upper - lower) % 2) != 0
                when (region) {
                    "F" -> lower to middle
                    "B" -> (if (roundedDown) middle + 1 else middle) to upper
                    else -> error("Unknown region: $region")
                }
            }
        return if (lowerRange == upperRange) lowerRange else error("Not the same $upperRange, $lowerRange")
    }

    fun getCol(boardingPass: String): Int {
        val (lowerRange, upperRange) = boardingPass
            .chunked(1)
            .drop(7)
            .fold(0 to 7) { (lower, upper), region ->
                val middle = (upper - lower) / 2 + lower
                val roundedDown = ((upper - lower) % 2) != 0
                when (region) {
                    "L" -> lower to middle
                    "R" -> (if (roundedDown) middle + 1 else middle) to upper
                    else -> error("Unknown region: $region")
                }
            }
        return if (lowerRange == upperRange) lowerRange else error("Not the same $upperRange, $lowerRange")
    }

    override fun part1(): Any? =
        input.map { getRow(it) * 8 + getCol(it) }.maxOrNull()

    override fun part2(): Any = input
        .map { getCol(it) to getRow(it) }
        .groupBy { (_, row) -> row }
        .values
        .sortedBy { seats -> seats.first().second  }
        .drop(1)
        .dropLast(1)
        .find { it.size != 8 }!!
        .let { seats ->
            val missing = (0..7).find { seats.none { (col, _) -> col == it } }!!
            seats.first().second * 8 + missing
        }
}

fun main() {
    Puzzle5().runBoth()
}