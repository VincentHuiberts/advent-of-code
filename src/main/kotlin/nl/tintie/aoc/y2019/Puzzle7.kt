package nl.tintie.aoc.y2019

import nl.tintie.aoc.AocPuzzle

class Puzzle7 : AocPuzzle(2019, 7) {
    override fun part1(): Any? {
        return getAllPhaseSettingsOptions(listOf(0, 1, 2, 3, 4)).map { phaseSetting ->
            val machines = phaseSetting.map {
                StateMachine(intArrayInput, listOf(it))
            }

            machines.first().input.add(0)

            machines.windowed(2).forEach { (first, second) ->
                first.runTillFinished()
                second.input.add(first.output.last())
            }
            machines.last().also { it.runTillFinished() }.output.last()
        }.maxOrNull()
    }

    private fun getAllPhaseSettingsOptions(options: List<Int>): List<List<Int>> {
        fun getRemainingCombinations(nums: List<Int>): List<List<Int>> {
            return if (nums.size > 1) {
                nums.flatMap { curr ->
                    getRemainingCombinations(nums.filter { it != curr }).map { listOf(curr) + it }
                }
            } else {
                listOf(nums)
            }
        }
        return getRemainingCombinations(options)
    }

    private fun runWithPhaseSetting(phaseSetting: List<Int>): Int {
        val machines = phaseSetting.map {
            StateMachine(intArrayInput, listOf(it))
        }

        machines.windowed(2).forEach { (first, second) ->
            first.runTillFinished()
            second.input.add(first.output.last())
        }
        return machines.last().also { it.runTillFinished() }.output.last()
    }

    override fun part2(): Any? {
        return getAllPhaseSettingsOptions(listOf(5, 6, 7, 8, 9)).map { phaseSetting ->
            val machines = phaseSetting.map {
                StateMachine(intArrayInput, listOf(it))
            }

            machines.first().input.add(0)

            while(!machines.last().finished) {
                machines.windowed(2).forEach { (first, second) ->
                    first.runToNextOutput()
                    second.input.add(first.output.last())
                }
                machines.last().run {
                    runToNextOutput()
                    machines.first().input.add(output.last())
                }
            }
            machines.last().output.last()
        }.maxOrNull()
    }
}

fun main() {
    Puzzle7().runBoth()
}
