package nl.tintie.aoc.y2016

import nl.tintie.aoc.AocPuzzle

object Puzzle4 : AocPuzzle(2016, 4) {
    val partsPattern = """[a-z0-9]+""".toRegex()

    data class Room(
        val nameParts: List<String>,
        val sectorId: Int,
        val hash: String
    )

    val rooms = input.map { line ->
        val parts = partsPattern.findAll(line).map { it.value }.toList()
        Room(parts.dropLast(2), parts[parts.size - 2].toInt(), parts.last())
    }

    fun Room.isReal(): Boolean {
        val groups = nameParts.flatMap { it.windowed(1) }.groupBy { it }.mapValues { it.value.size }
        val topChars = groups.entries.sortedWith { o1, o2 ->
            if (o1.value == o2.value) o1.key.compareTo(o2.key) else o2.value - o1.value
        }.take(5).map { it.key }
        return hash.windowed(1).containsAll(topChars)
    }

    fun Room.decrypt(): List<String> {
        fun decrypted(value: Char): Char =
            'a' + (value + sectorId - 'a') % 26
        return nameParts.map { it.toCharArray().map(::decrypted).toCharArray().let(::String) }
    }

    override fun part1(): Any? {
        return rooms.filter { it.isReal() }.sumOf { it.sectorId }
    }

    override fun part2(): Any? {
        return rooms.filter { it.isReal() }.find { it.decrypt() == listOf("northpole", "object", "storage") }?.sectorId
    }
}

fun main() {
    Puzzle4.runPart2()
}