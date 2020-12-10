package nl.tintie.aoc.y2019

import nl.tintie.aoc.AocPuzzle

object Puzzle13 : AocPuzzle(2019, 13) {
    data class Tile(
        val x: Long, val y: Long, val type: Long
    )

    override fun part1(): Any? = IntComputer(intArrayInput).run {
        runTillFinished()
        output.chunked(3).map { (x, y, type) ->
            Tile(x, y, type)
        }
    }.count { it.type == 2L }

    override fun part2(): Any? = IntComputer(intArrayInput).run {
        var latestScore : Long? = null
        program[0] = 2
        val objects = mutableListOf<Tile>()
        var paddle: Tile? = null
        while(!finished) {
            val (x, y, type) = listOf(runToNextOutput(), runToNextOutput(), runToNextOutput())
            if(x == -1L && y == 0L) {
                latestScore = type
            }
            else objects += Tile(x, y, type)
            val ball = objects.find { it.type == 4L }
            objects.find { it.type == 3L }?.let {
                paddle = it
            }
            if (paddle != null && ball != null) {
                input += when {
                    paddle!!.x < ball.x -> 1
                    paddle!!.x > ball.x -> -1
                    else -> 0
                }
                objects.removeAll(listOf(ball, paddle))
                paddle = paddle!!.copy(x = paddle!!.x + input.last())
            }
        }
        latestScore
    }


}

fun main() {
    Puzzle13.runPart2()
}