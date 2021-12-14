package nl.tintie.aoc.y2021

import nl.tintie.aoc.AocPuzzle

object Puzzle9 : AocPuzzle(2021, 9) {
    val heightMap = input.map { it.windowed(1).map { it.toInt() } }

    val maxX = heightMap.first().size - 1
    val maxY = heightMap.size - 1

    fun List<List<Int>>.getAdjacentPoints(point: Pair<Int, Int>): List<Pair<Int, Int>> {
        val (x, y) = point

        val n = takeIf { y in (1 .. maxY) }?.let { x to (y-1) }
        val e = takeIf { x in (0 until maxX) }?.let { (x + 1) to y }
        val s = takeIf { y in (0 until maxY) }?.let { x to (y + 1) }
        val w = takeIf { x in (1 .. maxX) }?.let { (x - 1) to y }

        return listOfNotNull(n, e, s, w)
    }

    override fun part1(): Any? {
        return (0..maxX).sumOf { x ->
            (0..maxY).sumOf { y ->
                val currentVal = heightMap[y][x]
                val adjacent = heightMap.getAdjacentPoints(x to y).map { (nx, ny) -> heightMap[ny][nx] }
                takeIf { adjacent.all { it > currentVal } }?.let { currentVal + 1 } ?: 0
            }
        }
    }

    fun getBasin(points: Set<Pair<Int, Int>>, visited: Set<Pair<Int, Int>> = setOf()): Set<Pair<Int, Int>> {
        val newPoints = (points).fold(visited) { ignore, point ->
            val (x, y) = point
            val currentVal = heightMap[y][x]
            val newPoints = heightMap.getAdjacentPoints(point).filter { it !in ignore }.filter { (nx, ny) ->
                heightMap[ny][nx] in (currentVal + 1)..8
            }
            ignore + newPoints + point
        }
        return if (newPoints == visited) visited else getBasin(newPoints - visited, newPoints)
    }

    override fun part2(): Any? {
        val lows = (0..maxX).flatMap { x ->
            (0..maxY).mapNotNull { y ->
                val currentVal = heightMap[y][x]
                val adjacent = heightMap.getAdjacentPoints(x to y).map { (nx, ny) -> heightMap[ny][nx] }
                takeIf { adjacent.all { it > currentVal } }?.let { x to y }
            }
        }

        return lows.map { getBasin(setOf(it)).size }.sortedDescending().take(3).reduce(Int::times)
    }
}

fun main() {
    Puzzle9.runPart2()
}