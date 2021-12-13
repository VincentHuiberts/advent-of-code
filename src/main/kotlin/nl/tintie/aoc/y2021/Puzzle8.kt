package nl.tintie.aoc.y2021

import nl.tintie.aoc.AocPuzzle

object Puzzle8 : AocPuzzle(2021, 8) {
    private data class NoteEntry(
        val signalPatterns: List<String>,
        val digitOutputs: List<String>
    )

    private val entries = input.map { line ->
        val (signals, outputs) = line.split(" | ").map { it.split(" ").map { it.trim() } }
        NoteEntry(signals, outputs)
    }

    override fun part1(): Any? {
        val lengths = listOf(2, 3, 4, 7)
        return entries.sumOf { it.digitOutputs.count { lengths.contains(it.length) } }
    }

    fun String.sorted() = String(toSortedSet().toCharArray())

    operator fun String.minus(other: String) =
        String((toCharArray().asList() - other.toCharArray().asList()).toCharArray())

    private fun getConfig(entry: NoteEntry): Map<String, String> {
        val allNums = (entry.digitOutputs + entry.signalPatterns).map { it.sorted() }

        return listOf(1, 7, 4, 8, 6, 9, 0, 3, 5, 2).fold(mapOf<Int, String>()) { config, cur ->
            val newConf: Pair<Int, String> = cur to when (cur) {
                1 -> allNums.find { it.length == 2 }!!
                7 -> allNums.find { it.length == 3 }!!
                4 -> allNums.find { it.length == 4 }!!
                8 -> allNums.find { it.length == 7 }!!
                6 -> allNums.filter { it.length == 6 }.find { (config[1]!! - it).length == 1 }!!
                9 -> allNums.filter { it.length == 6 }.find {
                    it !in config.values && (config[6]!! - it - config[4]!!).length == 1
                }!!
                0 -> allNums.filter { it.length == 6 }.find {
                    it !in config.values
                }!!
                3 -> allNums.filter { it.length == 5 }.find {
                    (it - config[1]!!).length == (it.length - 2)
                }!!
                5 -> allNums.filter { it.length == 5 }.find {
                    it !in config.values && (it - config[4]!!).length == it.length - 3
                }!!
                2 -> allNums.filter { it.length == 5 }.find {
                    it !in config.values
                }!!
                else -> "0"
            }
            config + newConf
        }.entries.associate { (k, v) -> v to k.toString() }
    }

    override fun part2(): Any? {
        return entries.sumOf {
            val config = getConfig(it)
            it.digitOutputs.map { config[it.sorted()] }.joinToString("").toInt()
        }
    }
}

fun main() {
    Puzzle8.runPart2()
}