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

    override val input: List<String>
        get() = """42: 9 14 | 10 1
9: 14 27 | 1 26
10: 23 14 | 28 1
1: "a"
11: 42 31
5: 1 14 | 15 1
19: 14 1 | 14 14
12: 24 14 | 19 1
16: 15 1 | 14 14
31: 14 17 | 1 13
6: 14 14 | 1 14
2: 1 24 | 14 4
0: 8 11
13: 14 3 | 1 12
15: 1 | 14
17: 14 2 | 1 7
23: 25 1 | 22 14
28: 16 1
4: 1 1
20: 14 14 | 1 15
3: 5 14 | 16 1
27: 1 6 | 14 18
14: "b"
21: 14 1 | 1 14
25: 1 1 | 1 14
22: 14 14
8: 42
26: 14 22 | 1 20
18: 15 15
7: 14 5 | 1 21
24: 14 1

abbbbbabbbaaaababbaabbbbabababbbabbbbbbabaaaa
bbabbbbaabaabba
babbbbaabbbbbabbbbbbaabaaabaaa
aaabbbbbbaaaabaababaabababbabaaabbababababaaa
bbbbbbbaaaabbbbaaabbabaaa
bbbababbbbaaaaaaaabbababaaababaabab
ababaaaaaabaaab
ababaaaaabbbaba
baabbaaaabbaaaababbaababb
abbbbabbbbaaaababbbbbbaaaababb
aaaaabbaabaaaaababaa
aaaabbaaaabbaaa
aaaabbaabbaaaaaaabbbabbbaaabbaabaaa
babaaabbbaaabaababbaabababaaab
aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba""".lines()

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