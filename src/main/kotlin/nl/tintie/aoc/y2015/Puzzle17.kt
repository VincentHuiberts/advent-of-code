package nl.tintie.aoc.y2015

import nl.tintie.aoc.AocPuzzle

object Puzzle17 : AocPuzzle(2015, 17) {
    val containers = input.map { it.toInt() }

    fun combinations(leftContainers: List<Int>, left: Int = 150): List<List<Int>> {
        return leftContainers.withIndex().filter { it.value <= left }.flatMap { container ->
            if (container.value == left) listOf(listOf(container.value))
            else combinations(
                leftContainers.drop(container.index + 1),
                left - container.value
            ).map { listOf(container.value) + it }
        }
    }

    override fun part1() = combinations(containers).size
    override fun part2(): Int {
        val combinations = combinations(containers)
        val minSize = combinations.minOfOrNull { it.size }
        return combinations.count { it.size == minSize }
    }
}

fun main() {
    Puzzle17.runBoth()
}