package nl.tintie.aoc.y2015

import nl.tintie.aoc.AocPuzzle
import kotlin.math.max
import kotlin.reflect.KProperty1

object Puzzle16 : AocPuzzle(2015, 16) {
    class Aunt(
        val num: Int,
        val references: Map<String, Int>
    )

    val linePattern = """Sue (\d+): (.+)""".toRegex()

    val aunts = input.map { line ->
        val (_,  aunt, references) = linePattern.matchEntire(line)!!.groupValues
        Aunt(aunt.toInt(), references.split(", ").refencesAsMap())
    }

    val references = """children: 3
cats: 7
samoyeds: 2
pomeranians: 3
akitas: 0
vizslas: 0
goldfish: 5
trees: 3
cars: 2
perfumes: 1""".lines().refencesAsMap()

    private fun List<String>.refencesAsMap() = associate { val (k, v) = it.split(": "); k to v.toInt() }

    override fun part1() = aunts.singleOrNull { aunt ->
        aunt.references.entries.all { (key, value) -> references[key] == value }
    }?.num

    override fun part2()= aunts.singleOrNull { aunt ->
        aunt.references.entries.all { (key, v1) ->
            val v2 = references[key]!!
            when(key) {
                "cats", "trees" -> v1 > v2
                "pomeranians", "goldfish" -> v1 < v2
                else -> v1 == v2
            }
        }
    }?.num
}

fun main() {
    Puzzle16.runBoth()
}