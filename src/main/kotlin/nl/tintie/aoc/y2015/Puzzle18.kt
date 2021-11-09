package nl.tintie.aoc.y2015

import nl.tintie.aoc.AocPuzzle
import nl.tintie.aoc.y2020.Puzzle11.print
import kotlin.math.max
import kotlin.math.min

object Puzzle18 : AocPuzzle(2015, 18) {
    val grid = input.map { it.windowed(1) }

    fun List<List<String>>.countNeighbours(pX: Int, pY: Int, state: String) =
        (max(0, pY - 1)..min(pY + 1, size - 1)).map { y ->
            val row = get(y)
            (max(0, pX - 1)..min(row.size - 1, pX + 1)).count { x ->
                if (x != pX || y != pY) row[x] == state
                else false
            }
        }.sum()


    fun List<List<String>>.step(): List<List<String>> {
        return mapIndexed { y, row ->
            row.mapIndexed { x, cell ->
                val neighbourCount = countNeighbours(x, y, "#")
                when(cell) {
                    "#" -> if(neighbourCount in 2..3) "#" else "."
                    else -> if(neighbourCount == 3) "#" else "."
                }
            }
        }
    }

    fun List<List<String>>.step2(): List<List<String>> {
        return mapIndexed { y, row ->
            row.mapIndexed { x, cell ->
                val neighbourCount = countNeighbours(x, y, "#")
                when {
                    (y == 0 || y == size - 1) && (x == 0 || x == row.size -1) -> "#"
                    cell == "#" -> if(neighbourCount in 2..3) "#" else "."
                    else -> if(neighbourCount == 3) "#" else "."
                }
            }
        }
    }

    fun List<List<String>>.count(char: String) = map { row -> row.count { it == char } }.sum()

    fun List<List<String>>.setCorners() = mapIndexed { y, row ->
        row.mapIndexed{ x, cell ->
            when {
                (y == 0 || y == size - 1) && (x == 0 || x == row.size -1) -> "#"
                else -> cell
            }
        }
    }

    override fun part1() = List(100){}.fold(grid) { acc, _ ->
        acc.step()
    }.count("#")

    override fun part2() = List(100){}.fold(grid.setCorners()) { acc, _ ->
        acc.step2()
    }.count("#")
}

fun main() {
    Puzzle18.runPart2()
}