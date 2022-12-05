package nl.tintie.aoc.y2022

import nl.tintie.aoc.AocPuzzle
import nl.tintie.aoc.splitCollection

object Puzzle5 : AocPuzzle(2022, 5) {
    val initialState get() = input.splitCollection { it.isEmpty() }.first().reversed().let { lines ->
        val stacks = lines.first().toList().withIndex().mapNotNull {
            it.value.digitToIntOrNull()?.let { stackNo -> IndexedValue(it.index, stackNo) }
        }
        stacks.associate { (i, v) ->
            v to lines.drop(1).mapNotNull { line ->
                line.get(i).takeIf { it.isLetter() }
            }.toMutableList()
        }.toMutableMap()
    }

    val inputInstructions = input.splitCollection { it.isEmpty() }[1]

    fun processInstruction(state: MutableMap<Int, MutableList<Char>>, line: String) {
        val (amount, from, to) = line.split(" ").mapNotNull { it.toIntOrNull() }
        val crates = state[from]!!.takeLast(amount).reversed()
        state.set(from, state[from]!!.dropLast(amount).toMutableList())
        state[to]!!.addAll(crates)
    }

    override fun part1(): Any? {
        val state = initialState
        inputInstructions.forEach{
            processInstruction(state, it)
        }
        return state.values.map { it.last() }.joinToString("")
    }

    fun processInstruction2(state: MutableMap<Int, MutableList<Char>>, line: String) {
        val (amount, from, to) = line.split(" ").mapNotNull { it.toIntOrNull() }
        val crates = state[from]!!.takeLast(amount)
        state.set(from, state[from]!!.dropLast(amount).toMutableList())
        state[to]!!.addAll(crates)
    }

    override fun part2(): Any? {
        val state = initialState
        inputInstructions.forEach{
            processInstruction2(state, it)
        }
        return state.values.map { it.last() }.joinToString("")
    }
}

fun main() {
    Puzzle5.runBoth()
}