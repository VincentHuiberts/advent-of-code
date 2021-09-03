package nl.tintie.aoc.y2015

import nl.tintie.aoc.AocPuzzle
import kotlin.math.max
import kotlin.reflect.KProperty1

object Puzzle15 : AocPuzzle(2015, 15) {
    val linePattern =
        """(\w+): capacity (-?\d+), durability (-?\d+), flavor (-?\d+), texture (-?\d+), calories (-?\d+)""".toRegex()

    data class Ingredient(
        val name: String,
        val cap: Int,
        val dur: Int,
        val fla: Int,
        val tex: Int,
        val cal: Int
    )

    val ingredients = input.map { line ->
        val (_, name, cap, dur, fla, tex, cal) = linePattern.matchEntire(line)!!.groupValues
        Ingredient(name, cap.toInt(), dur.toInt(), fla.toInt(), tex.toInt(), cal.toInt())
    }

    fun Set<Map.Entry<Ingredient, Int>>.getSum(field: KProperty1<Ingredient, Int>): Int =
        sumOf { (value, multiplier) -> field.get(value) * multiplier }

    fun Map<Ingredient, Int>.getScore(): Int =
        listOf(Ingredient::cap, Ingredient::dur, Ingredient::fla, Ingredient::tex).map { field ->
            max(entries.getSum(field), 0)
        }.reduce(Int::times)

    fun Map<Ingredient, Int>.getCallories(): Int = entries.getSum(Ingredient::cal)

    fun allRecipes(remainder: Int, ingredients: List<Ingredient>): Sequence<Map<Ingredient, Int>> = sequence {
        (0..remainder).forEach { amount ->
            if (ingredients.size == 1) {
                yield(mapOf(ingredients.single() to amount))
            } else {
                allRecipes(remainder - amount, ingredients.drop(1)).forEach { combination ->
                    yield(mapOf(ingredients.first() to amount) + combination)
                }
            }
        }
    }

    override fun part1() = allRecipes(100, ingredients).maxOf { it.getScore() }
    override fun part2() = allRecipes(100, ingredients).filter { it.getCallories() == 500 }.maxOf { it.getScore() }
}

private operator fun <E> List<E>.component6() = get(5)
private operator fun <E> List<E>.component7() = get(6)

fun main() {
    Puzzle15.runBoth()
}