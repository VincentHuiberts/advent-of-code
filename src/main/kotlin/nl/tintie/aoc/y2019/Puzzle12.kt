package nl.tintie.aoc.y2019

import nl.tintie.aoc.AocPuzzle
import java.lang.Math.abs
import java.lang.Math.max
import kotlin.reflect.KProperty1

object Puzzle12 : AocPuzzle(2019, 12) {
    data class Moon(
        val x: Int,
        val y: Int,
        val z: Int,
        val vX: Int = 0,
        val vY: Int = 0,
        val vZ: Int = 0
    ) {
        val potantialEnergy by lazy { abs(x) + abs(y) + abs(z) }
        val kineticEnergy by lazy { abs(vX) + abs(vY) + abs(vZ) }
    }

    val pattern = """<x=(-?\d+), y=(-?\d+), z=(-?\d+)>""".toRegex()

    fun List<Moon>.updatedMoonState(moon: Moon): Moon = moon.let {
        updateAxisState(it, Moon::x, Moon::vX, Moon::xCopy)
    }.let {
        updateAxisState(it, Moon::y, Moon::vY, Moon::yCopy)
    }.let {
        updateAxisState(it, Moon::z, Moon::vZ, Moon::zCopy)
    }

    override fun part1(): Any? {
        val start = input.map {
            val (x, y, z) = pattern.find(it)!!.groupValues.drop(1).map { pos -> pos.toInt() }
            Moon(x, y, z)
        }

        val updatedMoons = (1..1000).fold(start) { prevState, _ ->
            prevState.map { prevMoon ->
                prevState.updatedMoonState(prevMoon)
            }
        }

        return updatedMoons.sumBy { it.kineticEnergy * it.potantialEnergy }
    }

    class IterationState(
        val start: List<Moon>,
        val posField: KProperty1<Moon, Int>,
        val vField: KProperty1<Moon, Int>,
        val copyFun: Moon.(Int, Int) -> Moon
    ) {
        var currentState = start.map { it.copy() }
        var count = 0L

        fun stateMatchesStart(): Boolean {
            return currentState.all { vField.get(it) == 0 } &&
                    currentState.map { posField.get(it) to vField.get(it) } == start.map {
                posField.get(it) to vField.get(it)
            }
        }

        fun nextMatchingCount(): Long {
            do {
                count++
                val prevState = currentState.map { it.copy() }
                currentState = prevState.map { prevMoon ->
                    prevState.updateAxisState(prevMoon, posField, vField, copyFun)
                }
            } while (!stateMatchesStart())
            return count
        }

        fun countStateMatchesStart(toCount: Long): Boolean {
            (count until toCount).forEach {
                val prevState = currentState.map { it.copy() }
                currentState = prevState.map { prevMoon ->
                    prevState.updateAxisState(prevMoon, posField, vField, copyFun)
                }
            }
            count = toCount
            return stateMatchesStart()
        }
    }

    override fun part2(): Any? {
        val start = input.map {
            val (x, y, z) = pattern.find(it)!!.groupValues.drop(1).map { pos -> pos.toInt() }
            Moon(x, y, z)
        }

        val xIteration = IterationState(start, Moon::x, Moon::vX, Moon::xCopy)
        val yIteration = IterationState(start, Moon::y, Moon::vY, Moon::yCopy)
        val zIteration = IterationState(start, Moon::z, Moon::vZ, Moon::zCopy)

        val xIncrement = xIteration.nextMatchingCount()
        val yIncrement = yIteration.nextMatchingCount()
        val zIncrement = zIteration.nextMatchingCount()

        val max = max(max(xIncrement, yIncrement), zIncrement)

        var current = max
        while (true) {
            if(current % xIncrement == 0L && current % yIncrement == 0L && current % zIncrement == 0L) {
                return current
            }
            current += max
        }
    }
}

fun List<Puzzle12.Moon>.updateAxisState(
    moon: Puzzle12.Moon,
    posField: KProperty1<Puzzle12.Moon, Int>,
    vField: KProperty1<Puzzle12.Moon, Int>,
    copyFun: Puzzle12.Moon.(Int, Int) -> Puzzle12.Moon
): Puzzle12.Moon {
    val newVelocity = filter { it !== moon }
        .sumOf { otherMoon -> velocityDiff(posField.get(moon), posField.get(otherMoon)) } + vField(moon)
    val newPos = posField.get(moon) + newVelocity
    return moon.copyFun(newPos, newVelocity)
}

fun velocityDiff(current: Int, other: Int) = when {
    other > current -> 1
    other < current -> -1
    else -> 0
}

fun Puzzle12.Moon.xCopy(pos: Int, v: Int) = copy(x = pos, vX = v)
fun Puzzle12.Moon.yCopy(pos: Int, v: Int) = copy(y = pos, vY = v)
fun Puzzle12.Moon.zCopy(pos: Int, v: Int) = copy(z = pos, vZ = v)

fun main() {
    Puzzle12.runPart2()
}