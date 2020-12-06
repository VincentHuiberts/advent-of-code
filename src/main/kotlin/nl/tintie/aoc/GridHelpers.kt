package nl.tintie.aoc

import kotlinx.serialization.Serializable

@Serializable
class DynamicGrid<E> {
    private val grid = mutableMapOf<Int, MutableMap<Int, E>>()

    /**
     * Get element at [x, y].
     */
    fun getAt(position: Pair<Int, Int>) = grid[position.second]?.get(position.first)

    /**
     * Set element at [x, y].
     */
    fun setAt(position: Pair<Int, Int>, value: E) =
        grid.getOrPut(position.second) { mutableMapOf() }.set(position.first, value)

    /**
     * Return true if `expectedValue` is in the grid somewhere.
     */
    fun containsValue(expectedValue: E) = grid.any { (_, row) -> row.any { (_, value) -> value == expectedValue } }

    /**
     * Gets the coordinate of the first encountered element equal to `expectedValue`.
     */
    fun findValue(expectedValue: E) = grid.flatMap { (y, row) ->
        row.map { (x, value) -> Triple(x, y, value) }
    }.find { (_, _, value) -> value == expectedValue }?.let { (x, y, _) -> x to y }

    /**
     * Create a nested list with all values in the grid, non-existing values will be replaced by `emptyVal`
     */
    fun to2dList(emptyVal: E): List<List<E>> {
        val (minY, maxY) = grid.keys.minOrNull()!! to grid.keys.maxOrNull()!!
        val (minX, maxX) = grid.values.minOfOrNull { row -> row.keys.minOrNull() ?: Int.MAX_VALUE }!! to
                grid.values.maxOfOrNull { row -> row.keys.maxOrNull() ?: Int.MIN_VALUE }!!

        val printableGrid = MutableList(maxY - minY + 1) { MutableList(maxX - minX + 1) { emptyVal } }
        grid.entries.forEach { (y, row) ->
            row.forEach { (x, value) ->
                printableGrid[y - minY][x - minX] = value
            }
        }
        return printableGrid.map { it.toList() }.toList()
    }

    /**
     * Print the grid (using emptyVal as placeholder for unknown fields)
     */
    fun print(emptyVal: E) {
        to2dList(emptyVal).forEach { row ->
            println(row.joinToString(""))
        }
    }
}