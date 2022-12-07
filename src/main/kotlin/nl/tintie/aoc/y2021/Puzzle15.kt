package nl.tintie.aoc.y2021

import nl.tintie.aoc.AocPuzzle

typealias Node = Pair<Int, Int>
typealias NodeList = List<Node>
typealias Grid = List<List<Node>>

object Puzzle15 : AocPuzzle(2021, 15) {
    val grid = input.map { line -> line.toList().map { c -> c.digitToInt() } }

    val allNodes = grid.flatMapIndexed { y, line ->
        line.mapIndexed { x, _ ->
            x to y
        }
    }

    val xMin = 0
    val xMax = grid.first().size
    val yMin = 0
    val yMax = grid.size

    private fun getConnectedNodes(node: Node): NodeList {
        val (nx, ny) = node
        return listOf(
            maxOf(nx - 1, xMin) to ny,
            minOf(nx + 1, xMax) to ny,
            nx to maxOf(ny - 1, yMin),
            nx to minOf(ny + 1, yMax)
        ).distinct() - node
    }

//    private fun resolveNext(grid: Grid, visited: NodeList): Pair<Grid, NodeList> {
//
//    }

    override fun part1(): Any? {
        println(getConnectedNodes(0 to 0))
        return null
    }
}

fun main() {
    Puzzle15.runPart1()
}