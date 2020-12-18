package nl.tintie.aoc.y2020

import nl.tintie.aoc.AocPuzzle

object Puzzle18 : AocPuzzle(2020, 18) {
    val nestedExprPattern = """\(([\d\s+*]+)\)""".toRegex()
    val plusSum = """(\d+) \+ (\d+)""".toRegex()
    val timesSum = """(\d+) \* (\d+)""".toRegex()

    class SimpleExpression(
        val expression: String
    ) {
        val nums = expression.split("([+*])".toRegex()).map { it.trim().toLong() }
        val operators = expression.split("\\d+".toRegex()).filter { it.isNotBlank() }.map { it.trim() }

        fun calculate(): Long {
            return nums.drop(1).foldIndexed(nums.first().toLong()) { i, acc, num ->
                when (operators[i]) {
                    "*" -> acc * num
                    "+" -> acc + num
                    else -> error("Unknown operator ${operators[i]}")
                }
            }
        }

        fun calculatePt2(collapsedExpression: String = expression): Long {
            return if(plusSum.containsMatchIn(collapsedExpression)) {
                calculatePt2(plusSum.replace(collapsedExpression) {
                    val (n1, n2) = it.groupValues.drop(1).map { it.toLong() }
                    (n1 + n2).toString()
                })
            } else if(timesSum.containsMatchIn(collapsedExpression)) {
                calculatePt2(timesSum.replace(collapsedExpression) {
                    val (n1, n2) = it.groupValues.drop(1).map { it.toLong() }
                    (n1 * n2).toString()
                })
            } else {
                collapsedExpression.trim().toLong()
            }
        }
    }

    fun resolveExpression(line: String, calculateFun: SimpleExpression.() -> Long): Long {
        val simpleLine = if (nestedExprPattern.containsMatchIn(line)) {
            resolveExpression(nestedExprPattern.replace(line) {
                resolveExpression(it.groupValues[1], calculateFun).toString()
            }, calculateFun).toString()
        } else line
        return SimpleExpression(simpleLine).calculateFun()
    }

    override fun part1(): Any? {
        return input.map{ resolveExpression(it, SimpleExpression::calculate)}.reduce(Long::plus)
    }

    override fun part2(): Any? {
        return input.map{ resolveExpression(it, SimpleExpression::calculatePt2)}.reduce(Long::plus)
    }
}

fun main() {
    Puzzle18.runBoth()
}