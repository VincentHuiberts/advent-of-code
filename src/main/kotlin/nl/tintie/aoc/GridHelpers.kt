package nl.tintie.aoc

import kotlinx.serialization.Serializable

interface Grid<E> {
    val minX: Int
    val maxX: Int
    val minY: Int
    val maxY: Int

    /**
     * Get element at [x, y].
     */
    fun getAt(position: Pair<Int, Int>): E?

    /**
     * Set element at [x, y].
     */
    fun setAt(position: Pair<Int, Int>, value: E)

    fun findValue(expectedValue: E): Pair<Int, Int>?

    /**
     * Create a nested list with all values in the grid.
     */
    fun get2dList(): List<List<E>>

    /**
     * Print the grid.
     */
    fun print() = get2dList().forEach { row ->
        println(row.joinToString(""))
    }

    fun copy(): Grid<E>
}

@Serializable
class DynamicGrid<E>(
    private val emptyVal: E,
    private val grid: MutableMap<Int, MutableMap<Int, E>> = mutableMapOf()
) : Grid<E> {
    override val minX: Int
        get() = grid.values.minOfOrNull { row -> row.keys.minOrNull() ?: Int.MAX_VALUE }!!
    override val maxX: Int
        get() = grid.values.maxOfOrNull { row -> row.keys.maxOrNull() ?: Int.MIN_VALUE }!!
    override val minY: Int
        get() = grid.keys.minOrNull()!!
    override val maxY: Int
        get() = grid.keys.maxOrNull()!!

    override fun getAt(position: Pair<Int, Int>) = grid[position.second]?.get(position.first)

    override fun setAt(position: Pair<Int, Int>, value: E) =
        grid.getOrPut(position.second) { mutableMapOf() }.set(position.first, value)

    /**
     * Return true if `expectedValue` is in the grid somewhere.
     */
    fun containsValue(expectedValue: E) = grid.any { (_, row) -> row.any { (_, value) -> value == expectedValue } }

    /**
     * Gets the coordinate of the first encountered element equal to `expectedValue`.
     */
    override fun findValue(expectedValue: E) = grid.flatMap { (y, row) ->
        row.map { (x, value) -> Triple(x, y, value) }
    }.find { (_, _, value) -> value == expectedValue }?.let { (x, y, _) -> x to y }

    /**
     * Create a nested list with all values in the grid, non-existing values will be replaced by `emptyVal`
     */
    override fun get2dList(): List<List<E>> {
        val printableGrid = MutableList(maxY - minY + 1) { MutableList(maxX - minX + 1) { emptyVal } }
        grid.entries.forEach { (y, row) ->
            row.forEach { (x, value) ->
                printableGrid[y - minY][x - minX] = value
            }
        }
        return printableGrid.map { it.toList() }.toList()
    }

    override fun copy(): Grid<E> =
        DynamicGrid(emptyVal, grid.mapValues { entry -> entry.value.toMutableMap() }.toMutableMap())
}
//
//class FixedSizeGrid<E>(
//    inputLines: List<List<E>>
//) : Grid<E> {
//    private val lines = inputLines.map { it.toMutableList() }.toMutableList()
//
//    override val minX: Int
//        get() = 0
//    override val minY: Int
//        get() = 0
//    override val maxX: Int
//        get() = lines.maxOf { line -> line.size }
//    override val maxY: Int
//        get() = lines.size
//
//    override fun getAt(position: Pair<Int, Int>) = lines.getOrNull(position.second)?.getOrNull(position.first)
//
//    override fun setAt(position: Pair<Int, Int>, value: E) {
//        lines.getOrNull(position.second)?.set(position.first, value)
//    }
//
//    override fun findValue(expectedValue: E): Pair<Int, Int>? =
//        lines.flatMapIndexed { y, line ->
//            line.mapIndexedNotNull { x, value ->
//                if(value == expectedValue) x to y
//            }
//        }.singleOrNull()
//
//
//    override fun get2dList(): List<List<E>> = lines.map { it.toList() }.toList()
//    override fun copy(): Grid<E> = FixedSizeGrid(get2dList())
//}