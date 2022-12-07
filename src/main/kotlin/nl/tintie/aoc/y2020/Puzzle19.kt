package nl.tintie.aoc.y2020

import nl.tintie.aoc.AocPuzzle
import nl.tintie.aoc.MemoizedFunc
import nl.tintie.aoc.splitCollection

object Puzzle19 : AocPuzzle(2020, 19) {
    val sets = input.splitCollection { it == "" }
    val rules = sets.first().fold(mutableMapOf<String, List<List<String>>>()) { acc, rule ->
        val (key, vals) = rule.split(":")
        val values = vals.split("|").map { it.split(" ").filter { it.isNotBlank() }.map { it.replace("\"", "") } }
        acc[key] = values
        acc
    }

    val rulesPt2 = rules.toMutableMap().let {
        it["8"] = listOf(
            listOf("42"),
            listOf("42", "8")
        )
        it["11"] = listOf(
            listOf("42", "31"),
            listOf("42", "11", "31")
        )
        it.toMap()
    }
    val messages = sets[1]
    val maxMessagesSize = messages.maxOf { it.length }

    fun List<String>.multiply(options: List<String>): List<String> {
        return flatMap { initial -> options.map { new -> initial + new } }
    }

    val resolveRule = MemoizedFunc<String, List<String>> { ruleKey ->
        rules[ruleKey]!!.flatMap { option ->
            if (option.size == 1 && (option.contains("a") || option.contains("b"))) {
                listOf(option.joinToString(""))
            } else {
                option.drop(1).fold(recurse(option.first())) { acc, curr ->
                    acc.multiply(recurse(curr))
                }
            }
        }
    }

    override fun part1(): Any? {
        val options = resolveRule("0")
        return messages.count { options.contains(it) }
    }

    fun resolveLoopsPt2(ruleKey: String): List<String> {
        val resolvedOpts = mutableListOf<List<String>>()
        val rule = rulesPt2[ruleKey]!!
        while ((resolvedOpts.lastOrNull()?.lastOrNull()?.length ?: 0) < maxMessagesSize) {
            when {
                resolvedOpts.isEmpty() -> resolvedOpts.add(
                    rule.first().drop(1).fold(resolveRulePt2(rule.first().first())) { acc, curr ->
                        acc.multiply(resolveRulePt2(curr))
                    })
                else -> resolvedOpts.add(
                    rule[1].drop(1).fold(resolveRulePt2(rule.first().first())) { acc, curr ->
                        val resolvedRule = if (curr == ruleKey) resolvedOpts.last() else resolveRulePt2(curr)
                        acc.multiply(resolvedRule).distinct()
                    }
                )
            }
        }
        return resolvedOpts.flatten()
    }

    val resolveRulePt2 = MemoizedFunc<String, List<String>> { ruleKey ->
        if (ruleKey == "8" || ruleKey == "11") {
            resolveLoopsPt2(ruleKey)
        } else {
            rulesPt2[ruleKey]!!.flatMap { option ->
                if (option.size == 1 && (option.contains("a") || option.contains("b"))) {
                    listOf(option.joinToString(""))
                } else {
                    option.drop(1).fold(recurse(option.first())) { acc, curr ->
                        acc.multiply(recurse(curr))
                    }
                }
            }
        }
    }

    override fun part2(): Any? {
        val options = resolveRulePt2("0")
        return messages.count { options.contains(it) }
    }
}

fun main() {
    Puzzle19.runPart2()
}