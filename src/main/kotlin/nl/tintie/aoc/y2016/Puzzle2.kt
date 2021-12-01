package nl.tintie.aoc.y2016

import nl.tintie.aoc.AocPuzzle

object Puzzle2 : AocPuzzle(2016, 2) {
    private val instructions = input.map { it.windowed(1) }

    private val keypad = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9")
    )

    private val keypadPt2 = listOf(
        listOf("", "", "1", "", ""),
        listOf("", "2", "3", "4", ""),
        listOf("5", "6", "7", "8", "9"),
        listOf("", "A", "B", "C", ""),
        listOf("", "", "D", "", "")
    )

    private fun Pair<Int, Int>.move(dir: String, keypad: List<List<String>>): Pair<Int, Int> {
        val (x, y) = this
        return when (dir) {
            "U" -> x to maxOf(0, y - 1)
            "D" -> x to minOf(keypad.size - 1, y + 1)
            "L" -> maxOf(0, x - 1) to y
            "R" -> minOf(keypad.size - 1, x + 1) to y
            else -> throw RuntimeException("Unknown dir $dir")
        }.takeIf { keypad.value(it) != ""} ?: this
    }

    private fun List<List<String>>.value(place: Pair<Int, Int>) = this[place.second][place.first]

//    override val input: List<String>
//        get() = """ULL
//RRDDD
//LURDL
//UUUUD""".lines()

    override fun part1(): Any? {
        return instructions.fold(listOf<String>() to (1 to 1)) { acc: Pair<List<String>, Pair<Int, Int>>, instr ->
            val (code, location) = acc
            val endLoc = instr.fold(location) { loc: Pair<Int, Int>, dir ->
                loc.move(dir, keypad)
            }
            (code + keypad.value(endLoc)) to endLoc
        }.first.joinToString("")
    }

    override fun part2(): Any? {
        return instructions.fold(listOf<String>() to (2 to 2)) { acc: Pair<List<String>, Pair<Int, Int>>, instr ->
            val (code, location) = acc
            val endLoc = instr.fold(location) { loc: Pair<Int, Int>, dir ->
                loc.move(dir, keypadPt2)
            }
            (code + keypadPt2.value(endLoc)) to endLoc
        }.first.joinToString("")
    }
}

fun main() {
    Puzzle2.runPart2()
}