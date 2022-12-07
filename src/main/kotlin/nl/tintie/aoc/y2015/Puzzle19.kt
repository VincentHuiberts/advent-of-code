package nl.tintie.aoc.y2015

import nl.tintie.aoc.AocPuzzle

object Puzzle19 : AocPuzzle(2015, 19) {
    data class Replacement(val input: String, val output: String)

    val replacements = input.takeWhile { it.isNotBlank() }.map {
        val (input, output) = it.split(" => ")
        Replacement(input, output)
    }

    val molecule = input.dropWhile { it.isNotBlank() }.drop(1).first()

    override fun part1(): Any? =
        replacements.fold(setOf<String>()) { acc, cur ->
            val pattern = cur.input.toRegex()
            acc + pattern.findAll(molecule).map { res ->
                molecule.replaceRange(res.range, cur.output)
            }.toSet()
        }.size

    override fun part2(): Any? {
        val orderedReplacements = replacements.sortedWith(ReplaceComparator)
        return orderedReplacements
    }

    object ReplaceComparator: Comparator<Replacement> {
        override fun compare(o1: Replacement, o2: Replacement): Int {
            val sameSize = o2.output.length == o1.output.length
            return when(sameSize) {
                true -> o2.output.compareTo(o1.output)
                else -> o2.output.length - o1.output.length
            }
        }
    }
}

fun main() {
    Puzzle19.runPart2()
}