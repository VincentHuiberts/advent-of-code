package nl.tintie.aoc.y2020

import nl.tintie.aoc.AocPuzzle

class Puzzle7 : AocPuzzle(2020, 7) {
    data class Bag(
        val type: String
    )

    data class Rule(
        val bag: Bag,
        val content: List<Pair<Int, Bag>>
    )

    val rules = input.mapNotNull { line ->
        val (bagLine, contentLine) = line.split(" bags contain ")
        val bag = Bag(bagLine)
        val content = contentLine.takeIf { it != "no other bags." }?.split(", ")?.map { bagString ->
            val (amountStr, shade, color, _) = bagString.split(" ")
            amountStr.toInt() to Bag("$shade $color")
        }
        content?.let { Rule(bag = bag, content = content) }
    }

    fun getBagsContaining(type: String): List<Bag> {
        return rules.filter {
            it.content.any { (_, bag) ->
                bag.type == type
            }
        }
            .map { it.bag }
            .takeIf { it.isNotEmpty() }
            ?.flatMap { getBagsContaining(it.type) + it }
            ?: listOf()
    }

    override fun part1(): Any? {
        return getBagsContaining("shiny gold").distinct().size
    }

    private fun bagsContained(type: String): Int =
        rules.find { it.bag.type == type }
            ?.let { rule -> 1 + rule.content.sumBy { bag -> bag.first * bagsContained(bag.second.type) } }
            ?: 1

    override fun part2(): Any? {
        return bagsContained("shiny gold") - 1
    }
}

fun main() {
    Puzzle7().runBoth()
}