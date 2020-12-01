package nl.tintie.aoc.y2019

import nl.tintie.aoc.AocPuzzle
import java.lang.Math.ceil

class Puzzle14 : AocPuzzle(2019, 14) {
    data class Ingredient (
        val type: String,
        val amount: Int
    )

    data class Reaction (
        val input: List<Ingredient>,
        val output: Ingredient
    )

    val reactions = """10 ORE => 10 A
1 ORE => 1 B
7 A, 1 B => 1 C
7 A, 1 C => 1 D
7 A, 1 D => 1 E
7 A, 1 E => 1 FUEL""".lines().map(::mapToReaction)

    fun mapToReaction(line: String): Reaction {
        val (input, output) = line.split("=>")
        val inIngredients = input.split(",").map(::mapToIngredient)
        return Reaction(
            inIngredients, mapToIngredient(output)
        )
    }

    private fun mapToIngredient(ingredient: String): Ingredient {
        val (amount, type) = ingredient.trim().split(" ")
        return Ingredient(type, amount.toInt())
    }

    fun getCost(ingredient: Ingredient): List<Ingredient> {
        val nextReaction = reactions.single { it.output.type == ingredient.type }
        var factor = ingredient.amount / nextReaction.output.amount
        if (ingredient.amount % nextReaction.output.amount > 0) factor++
        return nextReaction.input.map { it.copy(amount = it.amount * factor) }
    }

    override fun part1(): Any? {
//        val reactions = input.map(::mapToReaction)
        var cost = reactions.single { it.output.type == "FUEL" }.input
//        val stockpile = mutableListOf<Ingredient>()
        while (!cost.all { it.type == "ORE" }) {
            val (ore, other) = cost.partition { it.type == "ORE" }
            val groupedIngredients = other.groupBy { it.type }.values.map { Ingredient(it.first().type, it.sumBy { it.amount }) }
            cost = ore + groupedIngredients.flatMap { getCost(it) }
        }
        return cost.sumBy { it.amount }
    }
}

fun main() {
    Puzzle14().runPart1()
}