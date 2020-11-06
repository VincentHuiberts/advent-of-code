package nl.tintie.aoc.y2019

import org.amshove.kluent.`should be equal to`
import org.junit.Test

class PuzzleAnswers{
    @Test
    fun `should provide the right answers`() {
        Puzzle1().run {
            part1() `should be equal to` 3464735
            part2() `should be equal to` 5194211
        }
        Puzzle2().run {
            part1() `should be equal to` 4484226L
            part2() `should be equal to` 5696L
        }
    }
}