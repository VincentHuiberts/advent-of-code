package nl.tintie.aoc.y2019

import nl.tintie.aoc.AocPuzzle



class Puzzle11 : AocPuzzle(2019, 11) {
    data class Point(val x: Int, val y: Int, var value: String = ".")

    val directions = listOf("N", "E", "S", "W")

    fun rightDir(currentDir: String): String {
        val newIdx = (directions.indexOf(currentDir) + 1).let{
            when {
                it < 0 -> it + directions.size
                it >= 4 -> it - directions.size
                else -> it
            }
        }
        return directions[newIdx]
    }

    fun leftDir(currentDir: String): String {
        val newIdx = (directions.indexOf(currentDir) - 1).let{
            when {
                it < 0 -> it + directions.size
                it >= 4 -> it - directions.size
                else -> it
            }
        }
        return directions[newIdx]
    }

    fun MutableList<Point>.findOrAdd(x: Int, y:Int) : Point =
        find { it.x == x && it.y == y } ?: Point(x, y).also { add(it) }

    fun runRobot(startPoint: Point): List<Point> {
        val robot = IntComputer(intArrayInput)
        val points = mutableListOf(startPoint)
        var dir = directions.first()
        var currentPoint = points.first()
        while (!robot.finished) {
            robot.input += when(currentPoint.value) {
                "." -> 0
                else -> 1
            }
            currentPoint.value = when(robot.runToNextOutput()) {
                0L -> "."
                else -> "#"
            }
            dir = when(robot.runToNextOutput()) {
                0L -> leftDir(dir)
                else -> rightDir(dir)
            }
            val (nextX, nextY) = when(dir) {
                "N" -> currentPoint.x to currentPoint.y + 1
                "E" -> currentPoint.x + 1 to currentPoint.y
                "S" -> currentPoint.x to currentPoint.y - 1
                "W" -> currentPoint.x - 1 to currentPoint.y
                else -> throw IllegalArgumentException(dir)
            }
            currentPoint = points.findOrAdd(nextX, nextY)
        }
        return points
    }

    override fun part1(): Any? {
        return runRobot(Point(0,0)).size
    }

    override fun part2(): Any? {
        val points = runRobot(Point(0,0, "#"))
        val (minX, maxX) = points.map { it.x }.run { minOrNull()!! to maxOrNull()!! }
        val (minY, maxY) = points.map { it.y }.run { minOrNull()!! to maxOrNull()!! }
        val grid = List(maxY - minY + 1){
            MutableList(maxX - minX + 1) {" "}
        }
        points.filter { it.value == "#" }.forEach { (x, y) ->
            grid[y - minY][x - minX] = "#"
        }
        return "\n" + grid.reversed().joinToString("\n") { row -> row.joinToString("") }
    }
}

fun main() {
    Puzzle11().runBoth()
}