package nl.tintie.aoc.y2015

import nl.tintie.aoc.AocPuzzle

object Puzzle14 : AocPuzzle(2015, 14) {

    val linePattern = """(\w+) can fly (\d+) km/s for (\d+) seconds, but then must rest for (\d+) seconds\.""".toRegex()

    data class Rule(
        val name: String,
        val speed: Int,
        val durationSeconds: Int,
        val restSeconds: Int
    )

    val rules = input.map { line ->
        val (_, name, speed, time, rest) = linePattern.matchEntire(line)!!.groupValues
        Rule(name, speed.toInt(), time.toInt(), rest.toInt())
    }

    fun Rule.distanceAfter(secs: Int) : Int {
        val period = restSeconds + durationSeconds
        val time = secs / period * durationSeconds + minOf(secs % period, durationSeconds)
        return time * speed
    }

    override fun part1(): Any? = rules.maxOf { it.distanceAfter(2503) }
    override fun part2(): Any? {
        val score = mutableMapOf<String, Int>()
        repeat(2503) { i ->
            val seconds = i + 1
            val distances = rules.associate { it.name to it.distanceAfter(seconds) }
            val maxDistance = distances.values.maxOf { it }
            distances.filterValues { distance -> distance == maxDistance}.keys.forEach { leader ->
                score[leader] = (score[leader] ?: 0) + 1
            }
        }
        return score.values.maxOf { it }
    }
}

fun main() {
    Puzzle14.runBoth()
}