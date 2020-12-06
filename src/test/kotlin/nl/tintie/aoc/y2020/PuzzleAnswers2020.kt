package nl.tintie.aoc.y2020

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class PuzzleAnswers2020 {
    @TestFactory
    fun `answer should match the initially provided one`() =
        listOf(
            Puzzle1(),
            Puzzle2(),
            Puzzle3(),
            Puzzle4(),
            Puzzle5(),
            Puzzle6()
        ).flatMap { puzzle ->
            listOf(
                DynamicTest.dynamicTest("Puzzle year: ${puzzle.year}, day: ${puzzle.day}, part 1") {
                    puzzle.validatePart1()
                },
                DynamicTest.dynamicTest("Puzzle year: ${puzzle.year}, day: ${puzzle.day}, part 2") {
                    puzzle.validatePart1()
                }
            )
        }
}