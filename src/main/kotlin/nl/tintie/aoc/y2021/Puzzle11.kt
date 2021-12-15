package nl.tintie.aoc.y2021

import nl.tintie.aoc.AocPuzzle

object Puzzle11 : AocPuzzle(2021, 11) {
    val grid = input.map { line -> line.windowed(1).map { it.toInt() } }

    val xMax = grid.first().size - 1
    val yMax = grid.size - 1

    val gridPoints = (0..yMax).flatMap { y ->
        (0..xMax).map { x ->
            x to y
        }
    }

    fun Pair<Int, Int>.surroundingPoints(): List<Pair<Int, Int>> {
        val (x0, y0) = this
        return (maxOf(y0 - 1, 0)..minOf(y0 + 1, yMax)).flatMap { y ->
            (maxOf(x0 - 1, 0)..minOf(x0 + 1, xMax)).map { x ->
                x to y
            }
        } - this
    }

    fun List<List<Int>>.step() = incrementAll().processFlashes().resetFlashes()

    fun List<List<Int>>.incrementAll() = map { line ->
        line.map { it + 1 }
    }

    fun List<List<Int>>.processFlashes(processed: Set<Pair<Int, Int>> = setOf()): List<List<Int>> {
        val mutableGrid = map { it.toMutableList() }
        val nextFlash = (gridPoints - processed).find { (x, y) -> this[y][x] > 9 }
        nextFlash?.surroundingPoints()?.forEach { (x, y) -> mutableGrid[y][x] += 1 }
        return if (nextFlash != null)
            mutableGrid.processFlashes(processed + nextFlash)
        else mutableGrid
    }

    fun List<List<Int>>.resetFlashes(): Pair<Int, List<List<Int>>> {
        val mutableGrid = map { it.toMutableList() }
        return gridPoints.filter { (x, y) ->
            mutableGrid[y][x] > 9
        }.onEach { (x, y) ->
            mutableGrid[y][x] = 0
        }.size to mutableGrid
    }

    override fun part1(): Any? {
        return (0 until 100).fold(grid to 0) { (grd, flashed), _ ->
            val (flashes, nextGrid) = grd.step()
            nextGrid to (flashed + flashes)
        }.second
    }

    tailrec fun findSyncedStep(grid: List<List<Int>>, step: Int = 0): Int {
        val (_, newGrid) = grid.step()
        return if (newGrid.all { line -> line.all { it == 0 } }) step + 1 else findSyncedStep(newGrid, step + 1)
    }

    override fun part2(): Any? {
        return findSyncedStep(grid)
    }
}

fun main() {
    Puzzle11.runPart2()
}