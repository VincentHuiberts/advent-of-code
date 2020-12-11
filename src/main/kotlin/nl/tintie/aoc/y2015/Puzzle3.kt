package nl.tintie.aoc.y2015

import nl.tintie.aoc.AocPuzzle

object Puzzle3 : AocPuzzle(2015, 3) {
    val directions = input.first().chunked(1)

    private fun List<String>.housesVisisted() =
        fold(listOf(0 to 0)) { acc, dir ->
            val (x, y) = acc.last()
            val newPos = when (dir) {
                "^" -> x to (y - 1)
                ">" -> (x + 1) to y
                "v" -> x to (y + 1)
                "<" -> (x - 1) to y
                else -> error("Unknown dir $dir")
            }
            acc + newPos
        }.distinct()


    override fun part1(): Any? {
        return directions.housesVisisted().size
    }

    override fun part2(): Any? {
        return (directions.filterIndexed { i, _ -> i % 2 == 0 }.housesVisisted() +
                directions.filterIndexed { i, _ -> i % 2 == 1 }.housesVisisted())
            .distinct().size
    }
}

fun main() {
    Puzzle3.runPart2()
}