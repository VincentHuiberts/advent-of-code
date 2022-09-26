package nl.tintie.aoc.y2021

import nl.tintie.aoc.AocPuzzle

object Puzzle15 : AocPuzzle(2021, 15) {
    override val input: List<String>
        get() = """1163751742
1381373672
2136511328
3694931569
7463417111
1319128137
1359912421
3125421639
1293138521
2311944581""".lines()

    val grid = input.map { it.windowed(1).map { it.toInt() } }
    val emptyGrid: List<List<Int>> = List(grid.size) {
        List(grid[it].size) { Int.MAX_VALUE }
    }

    fun <T> List<List<T>>.mutable() = map { it.toMutableList() }.toMutableList()

    fun List<List<Int>>.resolveNext(): List<List<Int>> {
        val visited: List<Pair<Int, Int>> = flatMapIndexed { y, row ->
            row.mapIndexedNotNull { x, value ->
                if (value != Int.MAX_VALUE) x to y else null
            }
        }

    }

    override fun part1(): Any? {
        println(emptyGrid)
        TODO("Not yet implemented")
    }
}

fun main() {
    Puzzle15.runPart1()
}