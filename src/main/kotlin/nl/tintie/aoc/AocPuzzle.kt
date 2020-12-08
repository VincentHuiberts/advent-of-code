package nl.tintie.aoc

import com.github.kittinunf.fuel.core.isSuccessful
import com.github.kittinunf.fuel.httpGet
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.util.*
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

abstract class AocPuzzle(val year: Int, val day: Int) {
    val remotePuzzlePageUrl = "$BASE_URL/$year/day/$day"
    val remoteInputUrl = "$remotePuzzlePageUrl/input"
    val puzzleCacheDir = "$localCacheDir/$year/$day"
    val localPuzzlePageFile = File("$puzzleCacheDir/puzzle.html")
    val localInputFile = File("$puzzleCacheDir/input.txt")

    val properties: Properties = Properties().apply { load(File("settings.properties").inputStream()) }

    fun fetchInput(): List<String> {
        downloadRemoteFile(remoteInputUrl, localInputFile)
        return localInputFile.readLines()
    }

    fun fetchPuzzlePage(): String {
        downloadRemoteFile(remotePuzzlePageUrl, localPuzzlePageFile)
        return localPuzzlePageFile.readText()
    }

    inline fun <reified T> cache(name: String, value: T) {
        File("$puzzleCacheDir/cache-$name.json").writeText(Json.encodeToString(value))
    }

    inline fun <reified T> cachedOrPut(name: String, orElse: () -> T) : T {
        return File("$puzzleCacheDir/cache-$name.json")
            .takeIf { it.exists() }
            ?.let { Json.decodeFromString<T>(it.readText()) }
            ?: orElse().also { cache(name, it) }
    }

    private fun downloadRemoteFile(url: String, outputFile: File) {
        outputFile.parentFile.mkdirs()
        val (_, response, result) = url.httpGet()
            .header("cookie", "session=${properties.getProperty("session")}")
            .response()
        if(response.isSuccessful) response.body().writeTo(outputFile.outputStream())
        else throw result.component2()!!
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
