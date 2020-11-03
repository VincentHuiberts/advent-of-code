package nl.tintie.aoc.y2019

import nl.tintie.aoc.AocPuzzle

data class Edge(val from: String, val to: String)

class Puzzle6: AocPuzzle(2019, 6) {
    val edges = input
        .map { it.split(")") }
        .map { (from, to) -> Edge(from, to) }

    override fun part1(): Any? {
        fun getOrbits(from: String, depth: Int = 1): Int {
            return edges.filter { it.from == from }.map { depth + getOrbits(it.to, depth + 1) }.sum()
        }

        return getOrbits("COM")
    }

    override fun part2(): Any? {
        val to = edges.find { it.to == "SAN" }

        fun getDistance(previous: String?, current: String, distance: Int = 0) : Int {
            val nextRound = edges.filter {edge ->
                (edge.to == current || edge.from == current) &&
                        previous?.let { edge.to != it && edge.from != it} ?: true
            }

            return if(nextRound.any { it == to }) {
                 distance - 1
            } else {
                nextRound.map { getDistance(current, if(it.to == current) it.from else it.to, distance + 1) }.sum()
            }
        }

        return getDistance(null, "YOU")
    }
}

fun main() {
    Puzzle6().runBoth()
}