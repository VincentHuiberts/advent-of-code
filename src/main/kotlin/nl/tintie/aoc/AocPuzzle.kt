package nl.tintie.aoc

import com.github.kittinunf.fuel.httpGet
import java.io.File
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

abstract class AocPuzzle(year: Int, day: Int) {
    val remoteInputUrl = "$BASE_URL/$year/day/$day/input"
    val localInputFile = File("$localInputDir/$year/$day.txt")

    fun fetchRemoteInput(): List<String> {
        localInputFile.parentFile.mkdirs()
        val (_, response, result) = remoteInputUrl.httpGet()
            .header(
                "cookie",
                "session=${System.getenv("SESSION")}"
            )
            .response()

        response.body().writeTo(localInputFile.outputStream())

        return localInputFile.readLines()
    }

    val input: List<String> by lazy {
        localInputFile.takeIf { it.exists() }?.readLines() ?: fetchRemoteInput()
    }

    val intArrayInput: List<Long> by lazy { input.single().split(",").map { it.toLong() } }

    abstract fun part1(): Any?

    open fun part2(): Any? =
        "Not yet implemented"

    @OptIn(ExperimentalTime::class)
    private fun runAndPrint(name: String, func: () -> Any?) = measureTimedValue { func() }.also {
        println("$name took: ${it.duration}")
        println("Answer: ${it.value}")
        println()
    }

    fun runPart1() = runAndPrint("Part1", ::part1)

    fun runPart2() = runAndPrint("Part2", ::part2)

    fun runBoth() {
        runPart1()
        runPart2()
    }

    companion object {
        const val BASE_URL = "https://adventofcode.com"
        const val localInputDir = "input"
    }
}
