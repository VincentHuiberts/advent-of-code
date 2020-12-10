package nl.tintie.aoc.y2020

import nl.tintie.aoc.AocPuzzle

object Puzzle8 : AocPuzzle(2020, 8) {
    val lines = input.map { it.split(" ") }.map { (op, n) -> op to n.toInt() }

    class Console(val gameInput: List<Pair<String, Int>>) {
        var i = 0
        var acc = 0
        val executed = mutableListOf<Int>()

        val finished
            get() = i >= gameInput.size

        fun run1Loop() {
            while (!executed.contains(i) && !finished) {
                executed.add(i)
                val (opr, n) = gameInput[i]
                when (opr) {
                    "nop" -> i++
                    "acc" -> {
                        acc += n; i++
                    }
                    "jmp" -> i += n
                    else -> error("Unknown op $opr")
                }
            }
        }
    }

    override fun part1(): Any? {
        return Console(lines).run {
            run1Loop()
            acc
        }
    }

    override fun part2(): Any? {
        val possibleFixIdxs = Console(lines).run {
            run1Loop()
            executed.reversed().filter { i -> listOf("jmp", "nop").contains(gameInput[i].first) }
        }

        return possibleFixIdxs.asSequence().map { fixIdx ->
            val updatedLines = lines.toMutableList().run {
                set(fixIdx, get(fixIdx).let { (opr, n) ->
                    (if(opr == "jmp") "nop" else "jmp") to n
                })
                toList()
            }
            Console(updatedLines)
        }.find { console ->
            console.run1Loop()
            console.finished
        }?.acc
    }
}

fun main() {
    Puzzle8.runBoth()
}