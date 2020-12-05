package nl.tintie.aoc.y2019

import nl.tintie.aoc.AocPuzzle
import java.util.*

typealias MutableMapGrid = MutableMap<Int, MutableMap<Int, String>>

class Puzzle15 : AocPuzzle(2019, 15) {
    fun MutableMapGrid.getAt(position: Pair<Int, Int>) =
        this[position.second]?.get(position.first)

    fun MutableMapGrid.setAt(position: Pair<Int, Int>, value: String) =
        getOrPut(position.second) { mutableMapOf() }.set(position.first, value)

    fun MutableMapGrid.dirVisitable(position: Pair<Int, Int>, dir: Int) =
        getAt(position.positionInDir(dir)).let { it == null || it == " " }

    fun MutableMapGrid.printGrid() {
        val (minY, maxY) = keys.minOrNull()!! to keys.maxOrNull()!!
        val (minX, maxX) = values.minOfOrNull { row -> row.keys.minOrNull() ?: Int.MAX_VALUE }!! to
                values.maxOfOrNull { row -> row.keys.maxOrNull() ?: Int.MIN_VALUE }!!

        val printableGrid = MutableList(maxY - minY + 1) { MutableList(maxX - minX + 1) { " " } }
        entries.forEach { (y, row) ->
            row.forEach { (x, value) ->
                printableGrid[y - minY][x - minX] = value
            }
        }
        printableGrid.forEach { row ->
            println(row.joinToString(""))
        }
    }

    fun Pair<Int, Int>.positionInDir(dir: Int) =
        when (dir) {
            1 -> first to second + 1
            2 -> first to second - 1
            3 -> first + 1 to second
            4 -> first - 1 to second
            else -> error("Unknown dir $dir")
        }

    private fun populateGrid(): MutableMap<Int, MutableMap<Int, String>> {
        val random = Random()

        val machine = IntComputer(intArrayInput)
        val grid = mutableMapOf<Int, MutableMap<Int, String>>()
        var position = 0 to 0

        var tankFound = false

        fun move(dir: Int): Boolean {
            machine.input.add(dir.toLong())
            val output = machine.runToNextOutput().toInt()
            val (obj, willMove) = when (output) {
                0 -> "#" to false
                1 -> " " to true
                2 -> "X" to true.also { tankFound = true }
                else -> error("Unknown output: $output")
            }
            grid.setAt(position.positionInDir(dir), obj)
            position = if (willMove) position.positionInDir(dir) else position
            return willMove
        }
        var iterations = 0
        val minIterations = 1_000_000
        grid.setAt(0 to 0, "O")
        while (iterations <= minIterations || !tankFound) {
            iterations++
            val dir = random.nextInt(4) + 1
            if (grid.dirVisitable(position, dir)) {
                move(dir)
            }
        }
        return grid
    }


    override fun part1(): Any? {
        val grid = cachedOrPut("grid", ::populateGrid)

        var endPointDistance = 0
        var currentPoints = listOf(0 to 0)
        var currentDistance = 0
        while (endPointDistance == 0) {
            val newPoints = mutableListOf<Pair<Int, Int>>()
            currentPoints.forEach { currentPosition ->
                (1..4).forEach {
                    val nextPos = currentPosition.positionInDir(it)
                    if (grid.dirVisitable(currentPosition, it)) {
                        newPoints.add(nextPos)
                        grid.setAt(nextPos, (currentDistance + 1).toString())
                    } else if (grid.getAt(nextPos) == "X") {
                        endPointDistance = currentDistance + 1
                    }
                }
            }
            currentDistance++
            currentPoints = newPoints
        }

        return endPointDistance
    }

    override fun part2(): Any? {
        val grid = cachedOrPut("grid", ::populateGrid)
        val oxSys = grid.flatMap { (y, row) ->
            row.map { (x, value) -> Triple(x, y, value) }
        }.find { (_, _, value) -> value == "X" }!!
            .let { (x, y, _) -> x to y }

        var moves = 0
        var currentPoints = listOf(oxSys)
        while(!grid.none{it.value.any { it.value == " " }}) {
            val newPoints = mutableListOf<Pair<Int, Int>>()
            currentPoints.forEach { currentPosition ->
                (1..4).forEach {
                    val nextPos = currentPosition.positionInDir(it)
                    if (grid.dirVisitable(currentPosition, it)) {
                        newPoints.add(nextPos)
                        grid.setAt(nextPos, "O")
                    }
                }
            }
            moves++
            currentPoints = newPoints
        }
        return moves
    }
}

fun main() {
    Puzzle15().runPart2()
}