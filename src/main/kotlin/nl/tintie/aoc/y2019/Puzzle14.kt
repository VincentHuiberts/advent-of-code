package nl.tintie.aoc.y2019

import nl.tintie.aoc.AocPuzzle
import nl.tintie.aoc.binSearch
import kotlin.math.min

class Puzzle14 : AocPuzzle(2019, 14) {
    data class Ingredient(
        val type: String,
        val amount: Long
    )

    data class Reaction(
        val input: List<Ingredient>,
        val output: Ingredient
    )

    val reactions = input.map(::mapToReaction)

    fun mapToReaction(line: String): Reaction {
        val (input, output) = line.split("=>")
        val inIngredients = input.split(",").map(::mapToIngredient)
        return Reaction(
            inIngredients, mapToIngredient(output)
        )
    }

    private fun mapToIngredient(ingredient: String): Ingredient {
        val (amount, type) = ingredient.trim().split(" ")
        return Ingredient(type, amount.toLong())
    }

    private fun getReaction(type: String) = reactions.single { it.output.type == type }

    fun getOreCost(ingredient: Ingredient): Long {
        val stock = mutableMapOf<String, Long>()

        fun addToStock(type: String, amount: Long) {
            val current = stock.getOrPut(type) { 0 }
            stock[type] = current + amount
        }

        fun takeFromStock(type: String, max: Long): Long {
            val current = stock.getOrPut(type) { 0 }
            val amount = min(current, max)
            stock[type] = current - amount
            return amount
        }

        fun _getOreCost(_ingredient: Ingredient): List<Ingredient> {
            val nextReaction = getReaction(_ingredient.type)
            val needed = _ingredient.amount - takeFromStock(_ingredient.type, _ingredient.amount)
            var factor = takeIf { needed > 0 }?.let { needed / nextReaction.output.amount } ?: 0L
            val stillNeeded = takeIf { needed > 0 }?.let { needed % nextReaction.output.amount } ?: 0L
            if (stillNeeded > 0) {
                factor++
                addToStock(_ingredient.type, nextReaction.output.amount - stillNeeded)
            }
            return if (nextReaction.input.all { it.type == "ORE" }) {
                nextReaction.input.map { it.copy(amount = it.amount * factor) }
            } else {
                nextReaction.input.map { it.copy(amount = it.amount * factor) }.flatMap { _getOreCost(it) }
            }
        }

        return _getOreCost(ingredient).groupBy { it.type }.get("ORE")!!.fold(0L) { acc, curr -> acc + curr.amount }
    }

    override fun part1(): Any? {
        return getOreCost(Ingredient("FUEL", 1))
    }

    override fun part2(): Any? {
        val amountOfOre = 1_000_000_000_000
        return binSearch(0, amountOfOre) {
            val (lower, upper) = getOreCost(Ingredient("FUEL", it)) to getOreCost(Ingredient("FUEL", it + 1))
            when (amountOfOre) {
                in lower..upper -> 0
                else -> amountOfOre - lower
            }
        }
    }
}

fun main() {
    Puzzle14().runBoth()
}