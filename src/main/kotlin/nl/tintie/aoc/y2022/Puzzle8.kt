package nl.tintie.aoc.y2022

import nl.tintie.aoc.AocPuzzle

object Puzzle8 : AocPuzzle(2022, 8) {
//    override val testInputCodeBlockIndex = 0

    private enum class Direction { U, D, L, R }

    val grid = input.map { line -> line.map { it.digitToInt() }.toIntArray() }.toTypedArray()

    private fun countVisible(grid: Array<IntArray>): Int {
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

    private fun Array<IntArray>.hasShorter(x: Int, y: Int): Boolean = Direction.values().any { dir ->
        hasShorter(x, y, dir)
    }

    private fun Array<IntArray>.hasShorter(x: Int, y: Int, direction: Direction): Boolean {
        val treeSize = get(y)[x]
        return when (direction) {
            Direction.U -> {
                (y downTo 0).drop(1).all { y2 ->
                    get(y2)[x] < treeSize
                }
            }
            Direction.R -> {
                (x..first().lastIndex).drop(1).all { x2 ->
                    get(y)[x2] < treeSize
                }
            }
            Direction.D -> {
                (y..lastIndex).drop(1).all { y2 ->
                    get(y2)[x] < treeSize
                }
            }
            Direction.L -> {
                (x downTo 0).drop(1).all { x2 ->
                    get(y)[x2] < treeSize
                }
            }
        }
    }

    override fun part1(): Any? {
        return countVisible(grid)
    }

    fun Array<IntArray>.scenicScore(x: Int, y: Int): Int = Direction.values().map { dir ->
        countVisible(x, y, dir)
    }.reduce(Int::times)

    private fun Array<IntArray>.countVisible(x: Int, y: Int, direction: Direction): Int {
        val treeSize = get(y)[x]
        return when (direction) {
            Direction.U -> {
                (y downTo 0).drop(1).takeWhile { y2 ->
                    get(y2)[x] < treeSize
                }.let {
                    if (it.lastOrNull()?.let { it != 0 } == true) {
                        it.size + 1
                    } else {
                        it.size
                    }
                }
            }
            Direction.R -> {
                (x..first().lastIndex).drop(1).takeWhile { x2 ->
                    get(y)[x2] < treeSize
                }.let {
                    if (it.lastOrNull()?.let { it != grid.first().lastIndex } == true) {
                        it.size + 1
                    } else {
                        it.size
                    }
                }
            }
            Direction.D -> {
                (y..lastIndex).drop(1).takeWhile { y2 ->
                    get(y2)[x] < treeSize
                }.let {
                    if (it.lastOrNull()?.let { it != grid.lastIndex } == true) {
                        it.size + 1
                    } else {
                        it.size
                    }
                }
            }
            Direction.L -> {
                (x downTo 0).drop(1).takeWhile { x2 ->
                    get(y)[x2] < treeSize
                }.let {
                    if (it.lastOrNull()?.let { it != 0 } == true) {
                        it.size + 1
                    } else {
                        it.size
                    }
                }
            }
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
    Puzzle8.runBoth()
}