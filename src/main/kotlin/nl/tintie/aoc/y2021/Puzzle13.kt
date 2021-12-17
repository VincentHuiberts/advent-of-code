package nl.tintie.aoc.y2021

import nl.tintie.aoc.AocPuzzle
import nl.tintie.aoc.splitCollection

object Puzzle13 : AocPuzzle(2021, 13) {
    val points = input.splitCollection { it.isBlank() }.first()
        .map { line -> val (x, y) = line.split(",").map { it.toInt() }; x to y }
    val folds = input.splitCollection { it.isBlank() }[1]

    val xMax = points.maxOf { (x, _) -> x }
    val yMax = points.maxOf { (_, y) -> y }
    val grid = List(yMax + 1) { y ->
        List(xMax + 1) { x ->
            if (points.any { it == x to y }) "#" else " "
        }
    }

    fun List<List<String>>.processFold(fold: String): List<List<String>> {
        val foldPlace = "\\d+".toRegex().find(fold)!!.groupValues.first().toInt()
        return when {
            fold.startsWith("fold along y=") -> {
                val top = subList(0, foldPlace).reversed()
                val bottom = subList(foldPlace + 1, size)
                top.mapIndexed { i, row ->
                    val bottomRow = bottom.getOrNull(i)
                    row.mapIndexed { ri, v ->
                        if (v == "#" || bottomRow?.getOrNull(ri) == "#") "#" else " "
                    }
                }.reversed()
            }
            else -> {
                val left = map { line ->
                    line.subList(0, foldPlace).reversed()
                }
                val right = map { line ->
                    line.subList(foldPlace + 1, line.size)
                }
                left.mapIndexed { i, row ->
                    val rightRow = right.getOrNull(i)
                    row.mapIndexed { ri, v ->
                        if (v == "#" || rightRow?.getOrNull(ri) == "#") "#" else " "
                    }.reversed()
                }
            }
        }
    }

    fun List<List<String>>.print() = forEach { line ->
        println(line.joinToString("") { it.takeIf { it == "#" } ?: " " })
    }

    override fun part1(): Any? {
        return grid.processFold(folds.first()).flatten().count { it == "#" }
    }

    override fun part2(): Any? {
        return folds.fold(grid) { nGrid, fold ->
            nGrid.processFold(fold)
        }.map { it.joinToString("") }.joinToString("\n")
    }

    override val part2Answer: String?
        get() = """
            #  #   ## ###  #  # #### #  # ###   ##  
            # #     # #  # # #  #    #  # #  # #  # 
            ##      # ###  ##   ###  #  # ###  #    
            # #     # #  # # #  #    #  # #  # # ## 
            # #  #  # #  # # #  #    #  # #  # #  # 
            #  #  ##  ###  #  # ####  ##  ###   ### """.trimIndent()
}

fun main() {
    Puzzle13.runBoth()
}