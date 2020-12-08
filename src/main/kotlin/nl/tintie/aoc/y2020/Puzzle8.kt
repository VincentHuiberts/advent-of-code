package nl.tintie.aoc.y2020

import nl.tintie.aoc.AocPuzzle

class Puzzle8: AocPuzzle(2020, 8) {
    val lines = input.map { it.split(" ") }.map { (op, n) -> op to n.toInt() }

    class Console(val gameInput: List<Pair<String, Int>>) {
        var i = 0
        var acc = 0
        val executed = mutableListOf<Int>()

        val finished
            get() =  i >= gameInput.size

        fun run1Loop() {
            while(!executed.contains(i) && !finished) {
                executed.add(i)
                val (opr, n) = gameInput[i]
                when (opr) {
                    "nop" -> i++
                    "acc" -> {acc += n; i++}
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
        val console = Console(lines)
        console.run1Loop()
        var found = false
        var fixAttempt = 1
        var accVal = 0
        while(!found) {
            val updatedLines = lines.toMutableList().run {
                val fixIdx = console.executed
                    .reversed().filter { lines[it].first == "nop" || lines[it].first == "jmp" }[fixAttempt - 1]
                val (opr, n) = get(fixIdx)
                val newOpr = if(opr == "jmp") "nop" else "jmp"
                set(fixIdx, newOpr to n)
                toList()
            }
            Console(updatedLines).run {
                run1Loop()
                if(finished) {
                    found = true
                    accVal = acc
                }
            }
            fixAttempt++
        }
        return accVal
    }
}

fun main() {
    Puzzle8().runBoth()
}