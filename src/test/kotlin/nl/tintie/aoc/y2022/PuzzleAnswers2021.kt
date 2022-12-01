package nl.tintie.aoc.y2022

import nl.tintie.aoc.validatePart1
import nl.tintie.aoc.validatePart2
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class PuzzleAnswers2022 {
    @TestFactory
    fun `answer should match the initially provided one`() =
        listOf(
            Puzzle1
        ).flatMap { puzzle ->
            listOf(
                DynamicTest.dynamicTest("Puzzle year: ${puzzle.year}, day: ${puzzle.day}, part 1") {
                    puzzle.validatePart1()
                },
                DynamicTest.dynamicTest("Puzzle year: ${puzzle.year}, day: ${puzzle.day}, part 2") {
                    puzzle.validatePart2()
                }
            )
        }
}