package nl.tintie.aoc.y2015

import nl.tintie.aoc.validatePart1
import nl.tintie.aoc.validatePart2
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class PuzzleAnswers2015 {
    @TestFactory
    fun `answer should match the initially provided one`() =
        listOf(
            Puzzle1,
            Puzzle2,
            Puzzle3,
            Puzzle4,
            Puzzle5,
            Puzzle6,
            Puzzle7,
            Puzzle8,
            Puzzle9,
            Puzzle10,
            Puzzle11,
            Puzzle12,
            Puzzle13,
            Puzzle14,
            Puzzle15,
            Puzzle16,
            Puzzle17,
            Puzzle18
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