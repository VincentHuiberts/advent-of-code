package nl.tintie.aoc.y2019

import nl.tintie.aoc.AocPuzzle
import kotlin.math.abs

data class Coordinate(val x: Int, val y: Int) {
    fun distance() = abs(x) + abs(y)
}

class Line(instructions: List<String>) {
    val coordinates = instructions
        .map { it.take(1) to it.drop(1).toInt() }
        .fold(listOf(Coordinate(0, 0))) { acc, (dir, amount) ->
            val position = acc.last()
            acc + when (dir) {
                "U" -> ((position.y + 1)..position.y + amount).map { position.copy(y = it) }
                "R" -> ((position.x + 1)..position.x + amount).map { position.copy(x = it) }
                "D" -> ((position.y - 1) downTo position.y - amount).map { position.copy(y = it) }
                "L" -> ((position.x - 1) downTo position.x - amount).map { position.copy(x = it) }
                else -> throw IllegalArgumentException("$dir not supported")
            }
        }.drop(1)
}

object Puzzle3 : AocPuzzle(2019, 3) {
    override fun part1(): Any? {
        val (line1, line2) = input.map { Line(it.split(",")) }
        return line1.coordinates.intersect(line2.coordinates)
            .map { it.distance() }
            .minOrNull()
    }

    override fun part2(): Any? {
        val (line1, line2) = input
            .map { Line(it.split(",")) }
        return line1.coordinates.intersect(line2.coordinates)
            .map { line1.coordinates.indexOf(it) + line2.coordinates.indexOf(it) + 2}
            .minOrNull()
    }
}

fun main() {
    Puzzle3.runBoth()
}