package nl.tintie.aoc.y2020

import nl.tintie.aoc.AocPuzzle

object Puzzle3 : AocPuzzle(2020, 3) {
    private val rows = input.map { it.chunked(1) }

    private fun treesEncoundered(horizontalSpeed: Int, verticalSpeed: Int): Long {
        return rows.foldIndexed(0 to 0L) { i, (x, treesEncountered), row ->
            if (i % verticalSpeed == 0) {
                val correctedX = x % row.size
                (correctedX + horizontalSpeed) to treesEncountered.let { trees ->
                    if (row[correctedX] == "#") trees + 1 else trees
                }
            } else {
                x to treesEncountered
            }
        }.second
    }

    override fun part1() =
        treesEncoundered(3, 1)

    override fun part2() = listOf(
        1 to 1,
        3 to 1,
        5 to 1,
        7 to 1,
        1 to 2
    ).map { (x, y) -> treesEncoundered(x, y) }
        .reduce(Long::times)
}

fun main() {
    Puzzle3.runBoth()
}