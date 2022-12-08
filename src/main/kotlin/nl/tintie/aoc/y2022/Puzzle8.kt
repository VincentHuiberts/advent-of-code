package nl.tintie.aoc.y2022

import nl.tintie.aoc.AocPuzzle
import org.junit.jupiter.api.fail

object Puzzle8 : AocPuzzle(2022, 8) {
//    override val testInputCodeBlockIndex = 0

    val grid = input.map { line -> line.map { it.digitToInt() } }

    fun countVisible(grid: List<List<Int>>): Int {
        return grid.withIndex().sumOf { (y, line) ->
            line.withIndex().count { (x, _) ->
                when {
                    x == 0 || y == 0 || x == line.lastIndex || y == grid.lastIndex -> true
                    else -> {
                        grid.hasShorter(x, y)
                    }
                }
            }
        }
    }

    fun List<List<Int>>.hasShorter(x: Int, y: Int): Boolean = listOf("N", "E", "S", "W").any { dir ->
        hasShorter(x, y, dir)
    }

    fun List<List<Int>>.hasShorter(x: Int, y: Int, direction: String): Boolean {
        val treeSize = get(y)[x]
        return when (direction) {
            "N" -> {
                (y downTo 0).drop(1).all { y2 ->
                    get(y2)[x] < treeSize
                }
            }
            "E" -> {
                (x..first().lastIndex).drop(1).all { x2 ->
                    get(y)[x2] < treeSize
                }
            }
            "S" -> {
                (y..lastIndex).drop(1).all { y2 ->
                    get(y2)[x] < treeSize
                }
            }
            "W" -> {
                (x downTo 0).drop(1).all { x2 ->
                    get(y)[x2] < treeSize
                }
            }
            else -> fail("wrong")
        }
    }

    override fun part1(): Any? {
        return countVisible(grid)
    }

    fun List<List<Int>>.scenicScore(x: Int, y: Int): Int = listOf("N", "E", "S", "W").map { dir ->
        countVisible(x, y, dir)
    }.reduce(Int::times)

    fun List<List<Int>>.countVisible(x: Int, y: Int, direction: String): Int {
        val treeSize = get(y)[x]
        return when (direction) {
            "N" -> {
                (y downTo 0).drop(1).takeWhile { y2 ->
                    get(y2)[x] < treeSize
                }.let {
                    if ((it.lastOrNull() ?:0) != 0) {
                        it.size + 1
                    } else {
                        it.size
                    }
                }
            }
            "E" -> {
                (x..first().lastIndex).drop(1).takeWhile { x2 ->
                    get(y)[x2] < treeSize
                }.let {
                    if ((it.lastOrNull() ?: grid.first().lastIndex) != grid.first().lastIndex) {
                        it.size + 1
                    } else {
                        it.size
                    }
                }
            }
            "S" -> {
                (y..lastIndex).drop(1).takeWhile { y2 ->
                    get(y2)[x] < treeSize
                }.let {
                    if ((it.lastOrNull() ?: grid.lastIndex) != grid.lastIndex) {
                        it.size + 1
                    } else {
                        it.size
                    }
                }
            }
            "W" -> {
                (x downTo 0).drop(1).takeWhile { x2 ->
                    get(y)[x2] < treeSize
                }.let {
                    if ((it.lastOrNull() ?: 0) != 0) {
                        it.size + 1
                    } else {
                        it.size
                    }
                }
            }
            else -> fail("wrong")
        }
    }

    override fun part2(): Any? {
        return grid.withIndex().maxOf { (y, line) ->
            line.withIndex().maxOf { (x, _) ->
                grid.scenicScore(x, y)
            }
        }
    }
}

fun main() {
    Puzzle8.runPart2()
}