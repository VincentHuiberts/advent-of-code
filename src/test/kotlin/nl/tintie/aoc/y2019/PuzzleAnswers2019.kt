package nl.tintie.aoc.y2019

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class PuzzleAnswers2019 {
    @TestFactory
    fun `answer should match the correct one`() =
        listOf(
            Puzzle1(),
            Puzzle2(),
            Puzzle3(),
            Puzzle4(),
            Puzzle5(),
            Puzzle6(),
            Puzzle7(),
            Puzzle8(),
            Puzzle9(),
            Puzzle10(),
            Puzzle11(),
            Puzzle12(),
            Puzzle13()
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