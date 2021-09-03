package nl.tintie.aoc.y2015

import kotlinx.serialization.json.*
import nl.tintie.aoc.AocPuzzle

object Puzzle12 : AocPuzzle(2015, 12) {
    val inputJson = Json.parseToJsonElement(input.first())

    fun getAllNumbers(element: JsonElement, filterObject: (JsonObject) -> Boolean = { true }): List<Int> =
        when (element) {
            is JsonPrimitive -> takeIf { !element.isString }?.let { listOf(element.content.toInt()) } ?: listOf()
            is JsonArray -> element.flatMap { getAllNumbers(it, filterObject) }
            is JsonObject -> element.takeIf(filterObject)?.let { it.entries.flatMap { (_, v) -> getAllNumbers(v, filterObject) } }
                ?: listOf()
        }

    override fun part1(): Any? = getAllNumbers(inputJson).sum()
    override fun part2(): Any? = getAllNumbers(inputJson){ json ->
        json.values.none { (it as? JsonPrimitive)?.content == "red" }
    }.sum()
}

fun main() {
    Puzzle12.runPart2()
}