package nl.tintie.aoc

fun AocPuzzle.assertAnswer(name: String, expected: String?, actual: Any?) =
    assert(expected.toString() == actual.toString()) {
        "Year $year Day $day $name Expected '$expected', but was '$actual'"
    }

fun AocPuzzle.validatePart1() = assertAnswer("Part1", part1Answer, part1())
fun AocPuzzle.validatePart2() = assertAnswer("Part2", part2Answer, part2())