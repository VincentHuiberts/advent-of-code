package nl.tintie.aoc.y2015

import nl.tintie.aoc.AocPuzzle
import nl.tintie.aoc.allArrangements

object Puzzle9 : AocPuzzle(2015, 9) {
    val routePattern = """(\w+) to (\w+) = (\d+)""".toRegex()

    data class Route(
        val places: List<String>,
        val distance: Int
    )

    val routes = input.map { line ->
        val (_, from, to, distance) = routePattern.matchEntire(line)!!.groupValues
        Route(listOf(from, to), distance.toInt())
    }

    val places = routes.flatMap { it.places }.distinct()


    fun getRouteDistance(route: List<String>): Int? = route.windowed(size = 2, step = 1, partialWindows = false)
        .map { (place1, place2) ->
            getDistance(place1, place2)
        }.takeIf { !it.contains(null) }?.sumOf { it!! }

    fun getDistance(place1: String, place2: String): Int? = routes
        .find { it.places.containsAll(listOf(place1, place2)) }?.distance

    override fun part1(): Any? {
        return allArrangements(places).mapNotNull(::getRouteDistance).minOrNull()
    }

    override fun part2(): Any? {
        return allArrangements(places).mapNotNull(::getRouteDistance).maxOrNull()
    }
}

fun main() {
    Puzzle9.runBoth()
}