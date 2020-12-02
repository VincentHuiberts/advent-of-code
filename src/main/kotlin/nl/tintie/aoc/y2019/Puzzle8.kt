package nl.tintie.aoc.y2019

import nl.tintie.aoc.AocPuzzle

class Image(input: List<Int>, width: Int, height: Int) {
    val layers = input.chunked(width).chunked(height)
}

class Puzzle8 : AocPuzzle(2019, 8) {
    override fun part1(): Any? {
        val minlayer =
            Image(input[0].chunked(1).map { it.toInt() }, 25, 6).layers.minByOrNull { it.flatten().count { it == 0 } }

        val ones = minlayer?.sumBy { it.count { it == 1 } }
        val twos = minlayer?.sumBy { it.count { it == 2 } }
        return ones!! * twos!!
    }

    override fun part2(): Any? {
        val layers = Image(input[0].chunked(1).map { it.toInt() }, 25, 6).layers

        return layers.reduce { acc, layer ->
            val picture = acc.map { it.toMutableList() }.toMutableList()
            layer.forEachIndexed { y, row ->
                row.forEachIndexed { x, cell ->
                    if (cell != 2 && acc[y][x] == 2) picture[y][x] = cell
                }
            }
            picture.toList()
        }.joinToString("\n") { row ->
            row.joinToString("") {
                if (it == 0) " " else "0"
            }
        }.let { "\n$it" }
    }

    override val part2Answer: String?
        get() = """
 00  0  0 0000  00  000  
0  0 0  0 0    0  0 0  0 
0  0 0000 000  0    000  
0000 0  0 0    0    0  0 
0  0 0  0 0    0  0 0  0 
0  0 0  0 0     00  000  """
}

fun main() {
    Puzzle8().runBoth()
}