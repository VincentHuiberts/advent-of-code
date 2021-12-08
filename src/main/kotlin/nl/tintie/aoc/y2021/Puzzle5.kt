package nl.tintie.aoc.y2021

import nl.tintie.aoc.AocPuzzle

object Puzzle5 : AocPuzzle(2021, 5) {
    private data class Line(
        val from: Pair<Int, Int>,
        val to: Pair<Int, Int>
    )

    private fun Line.getPoints(): List<Pair<Int, Int>> {
        val (x1, y1) = from
        val (x2, y2) = to

        val xRange = (if(x1 > x2) x1 downTo x2 else x1..x2).toList()
        val yRange = (if(y1 > y2) y1 downTo y2 else y1..y2).toList()

        return if (xRange.size != yRange.size) {
            xRange.flatMap { x ->
                yRange.map { y ->
                    x to y
                }
            }
        } else {
            xRange.mapIndexed { i, x ->
                x to yRange[i]
            }
        }
    }

    private val lines = input.map {
        val (from, to) = it.split(" -> ").map {
            val (x, y) = it.split(",").map { it.toInt() }
            x to y
        }
        Line(from, to)
    }

    private fun List<Line>.limits(): Pair<Int, Int> {
        val maxX = maxOf { maxOf(it.from.first, it.to.first) }
        val maxY = maxOf { maxOf(it.from.second, it.to.second) }
        return maxX to maxY
    }

    private fun buildGrid(limits: Pair<Int, Int>, initValue: Int = 0): MutableList<MutableList<Int>> {
        val (maxX, maxY) = limits
        return MutableList(maxX + 1) { MutableList(maxY + 1) { initValue } }
    }

    override fun part1(): Any? {
        return lines.filter {
            it.from.first == it.to.first || it.from.second == it.to.second
        }.fold(buildGrid(lines.limits())) { grid, line ->
            line.getPoints().forEach { (x, y) ->
                grid[x][y] += 1
            }
            grid
        }.flatten().count { it > 1 }
    }

    override fun part2(): Any? {
        return lines.fold(buildGrid(lines.limits())) { grid, line ->
            line.getPoints().forEach { (x, y) ->
                grid[x][y] += 1
            }
            grid
        }.flatten().count { it > 1 }
    }
}

fun main() {
    Puzzle5.runPart2()
}