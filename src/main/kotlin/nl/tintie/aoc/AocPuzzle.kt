package nl.tintie.aoc

import com.github.kittinunf.fuel.httpGet
import java.io.File
import java.util.*
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

abstract class AocPuzzle(val year: Int, val day: Int) {
    val remotePuzzlePageUrl = "$BASE_URL/$year/day/$day"
    val remoteInputUrl = "$remotePuzzlePageUrl/input"
    val localPuzzlePageFile = File("$localCacheDir/$year/$day/puzzle.html")
    val localInputFile = File("$localCacheDir/$year/$day/input.txt")

    val properties: Properties = Properties().apply { load(File("settings.properties").inputStream()) }

    fun fetchInput(): List<String> {
        downloadRemoteFile(remoteInputUrl, localInputFile)
        return localInputFile.readLines()
    }

    fun fetchPuzzlePage(): String {
        downloadRemoteFile(remotePuzzlePageUrl, localPuzzlePageFile)
        return localPuzzlePageFile.readText()
    }

    private fun downloadRemoteFile(url: String, outputFile: File) {
        outputFile.parentFile.mkdirs()
        val (_, response, _) = url.httpGet()
            .header("cookie", "session=${properties.getProperty("session")}")
            .response()

        response.body().writeTo(outputFile.outputStream())
    }

    open val input: List<String> by lazy {
        localInputFile.takeIf { it.exists() }?.readLines() ?: fetchInput()
    }

    val puzzleFile: String by lazy {
        localPuzzlePageFile.takeIf { it.exists() }?.readText() ?: fetchPuzzlePage()
    }

    open val part1Answer: String? by lazy {
        answerPattern.findAll(puzzleFile).firstOrNull()?.groupValues?.get(1).also {
            if (it == null) localPuzzlePageFile.delete() // Answer not yet part of the page
        }
    }

    open val part2Answer: String? by lazy {
        answerPattern.findAll(puzzleFile).drop(1).firstOrNull()?.groupValues?.get(1).also {
            if (it == null) localPuzzlePageFile.delete() // Answer not yet part of the page
        }
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

    private fun assertAnswer(name: String, expected: String?, actual: Any?) =
        assert(expected.toString() == actual.toString()) {
            "Year $year Day $day $name Expected '$expected', but was '$actual'"
        }

    fun validatePart1() = assertAnswer("Part1", part1Answer, part1())
    fun validatePart2() = assertAnswer("Part2", part2Answer, part2())

    companion object {
        const val BASE_URL = "https://adventofcode.com"
        const val localCacheDir = "cache"
        val answerPattern = """<p>Your puzzle answer was <code>(.*?)<\/code>\.""".toRegex()
    }
}
