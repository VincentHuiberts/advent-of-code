package nl.tintie.aoc.y2019

import nl.tintie.aoc.AocPuzzle
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2

object Puzzle10 : AocPuzzle(2019, 10) {
    data class Point(
        val char: String,
        val x: Int,
        val y: Int,
        val angle: Double? = null
    )

    val points = input.flatMapIndexed { y, row ->
        row.chunked(1).mapIndexed { x, char -> Point(char, x, y) }
    }

    fun getAstroidsWithAngles(point: Point): List<Point> {
        return points.filter { it != point && it.char == "#" }.map { other ->
            val xDiff = point.x - other.x
            val yDiff = point.y - other.y
            other.copy(angle = atan2(yDiff.toDouble(), xDiff.toDouble()))
        }
    }

    fun numberOfDistinctAngles(point: Point): Int {
        return getAstroidsWithAngles(point).map { it.angle }.distinct().size
    }

    override fun part1(): Any? {
        return points.filter { it.char == "#" }.map(::numberOfDistinctAngles).maxOrNull()
    }

    override fun part2(): Any? {
        val bestPoint = points.filter { it.char == "#" }.maxByOrNull(::numberOfDistinctAngles)!!
        val astroids = getAstroidsWithAngles(bestPoint)
        val grouped = astroids.groupBy {
            (it.angle!! + 1.5 * PI) % (2 * PI)
        }.entries.sortedBy { entry ->
            entry.key
        }

        val target = grouped[199].value.minByOrNull {
            val xDiff = abs(bestPoint.x - it.x)
            val yDiff = abs(bestPoint.y - it.y)
            xDiff + yDiff
        }!!

        return target.x * 100 + target.y
    }
}

fun main() {
    Puzzle10.runBoth()
}