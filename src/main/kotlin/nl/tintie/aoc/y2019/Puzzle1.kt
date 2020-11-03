package nl.tintie.aoc.y2019

import nl.tintie.aoc.AocPuzzle
import kotlin.math.max

class Puzzle1 : AocPuzzle(2019, 1) {
    override fun part1() = input
        .map { it.toInt() }
        .map { it / 3 - 2 }
        .sum()

    fun calculateFuelForFuel(mass: Int): Int {
        val fuelNeeded = max(0, mass / 3 - 2)
        return if (fuelNeeded > 0) {
            fuelNeeded + calculateFuelForFuel(fuelNeeded)
        } else {
            fuelNeeded
        }
    }

    override fun part2(): Any? {
        return input
            .map { it.toInt() }
            .map(::calculateFuelForFuel)
            .sum()
    }
}

fun main() {
    Puzzle1().runBoth()
}