package nl.tintie.aoc.y2021

import nl.tintie.aoc.AocPuzzle
import nl.tintie.aoc.splitCollection

object Puzzle4 : AocPuzzle(2021, 4) {
    data class Board(
        val rows: List<List<Int>>
    ) {
        val cols = rows.foldIndexed(List(rows.first().size) { mutableListOf<Int>() }) { i, cols, row ->
            cols.apply {
                row.forEachIndexed { i, num -> cols[i].add(num) }
            }
        }
    }

    val nums = input.first().split(",").map { it.toInt() }

    val digits = """\d+""".toRegex()

    val boards = input.drop(2).splitCollection { it.isNullOrBlank() }.map { lines ->
        Board(lines.map { line ->
            digits.findAll(line).map { it.value.toInt() }.toList()
        })
    }

    fun Board.winning(nums: List<Int>): Boolean {
        val rowWin = rows.any { row -> row.all { it in nums } }
        val colWin = cols.any { col -> col.all { it in nums } }
        return rowWin || colWin
    }

    fun List<Board>.findWinningBoard(allNums: List<Int>): Pair<Board, List<Int>>? {
        allNums.drop(5).fold(allNums.take(5)) { nums, cur ->
            find { it.winning(nums) }?.let {
                return it to nums
            }
            nums + cur
        }
        return null
    }

    fun Board.getScore(nums: List<Int>): Int {
        val total = rows.flatMap { row -> row.filter { it !in nums } }.sum()
        return nums.last() * total
    }

    override fun part1(): Any? {
        val (winner, nums) = boards.findWinningBoard(nums)!!
        return winner.getScore(nums)
    }

    fun List<Board>.findLosingBoard(allNums: List<Int>): Pair<Board, List<Int>>? {
        allNums.drop(5).fold(allNums.take(5)) { nums, cur ->
            filter { !it.winning(nums) }.takeIf { it.size == 1 }?.single()?.let {
                return it to nums
            }
            nums + cur
        }
        return null
    }

    override fun part2(): Any? {
        val (board, _) = boards.findLosingBoard(nums)!!
        val (losser, usedNums) = listOf(board).findWinningBoard(nums)!!
        return losser.getScore(usedNums)
    }
}

fun main() {
    Puzzle4.runBoth()
}