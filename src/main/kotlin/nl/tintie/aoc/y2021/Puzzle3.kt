package nl.tintie.aoc.y2021

import nl.tintie.aoc.AocPuzzle
import nl.tintie.aoc.countOccurrences
import nl.tintie.aoc.pivot

object Puzzle3 : AocPuzzle(2021, 3) {
    override fun part1(): Any? {
        val minMax = input.pivot { it }.map { it.countOccurrences() }
        val min = minMax.map { it.first().first }.toCharArray().let(::String)
        val max = minMax.map { it.last().first }.toCharArray().let(::String)
        return Integer.parseInt(min, 2) * Integer.parseInt(max, 2)
    }

    override fun part2(): Any? {
        val min =
            input.first().toCharArray().foldIndexed(input to CharArray(input.first().length)) { i, (inp, out), _ ->
                val min = inp.pivot { it }[i].countOccurrences()
                    .takeIf { it.first().second != it.last().second }
                    ?.first()?.first ?: '0'
                out[i] = min
                (inp.takeIf { it.size > 1 }?.filter { it[i] == min } ?: inp) to out
            }.first.single()

        val max =
            input.first().toCharArray().foldIndexed(input to CharArray(input.first().length)) { i, (inp, out), _ ->
                val max = inp.pivot { it }[i].countOccurrences()
                    .takeIf { it.first().second != it.last().second }
                    ?.last()?.first ?: '1'
                out[i] = max
                (inp.takeIf { it.size > 1 }?.filter { it[i] == max } ?: inp) to out
            }.first.single()

        return return Integer.parseInt(min, 2) * Integer.parseInt(max, 2)
    }
}

fun main() {
    Puzzle3.runPart2()
}