package nl.tintie.aoc.y2016

import nl.tintie.aoc.AocPuzzle
import java.math.BigInteger
import java.security.MessageDigest

object Puzzle5 : AocPuzzle(2016, 5) {
    fun String.md5(md: MessageDigest) = BigInteger(1, md.digest(toByteArray()))

    val boundary = BigInteger("f".repeat(27), 16)

    fun BigInteger.hexString() = toString(16).padStart(32, '0')

    override fun part1(): Any? {
        val md = MessageDigest.getInstance("MD5")
        return (0..Int.MAX_VALUE).asSequence().map {
            "${input.first()}$it".md5(md)
        }.filter { it <= boundary }
            .take(8)
            .map { it.hexString()[5] }
            .toList()
            .let { String(it.toCharArray()) }
    }

    override fun part2(): Any? {
        val md = MessageDigest.getInstance("MD5")
        val password = CharArray(8)

        return (0..Int.MAX_VALUE).asSequence().map {
            "${input.first()}$it".md5(md)
        }.filter { it <= boundary }
            .map { it.hexString().let { it[5] to it[6] } }
            .filter { (i, v) -> i.takeIf { it.isDigit() }?.digitToInt() in 0..7 }
            .onEach { (i, v) -> if (password[i.digitToInt()] == '\u0000') password[i.digitToInt()] = v }
            .takeWhile { password.contains('\u0000') }
            .toList()
            .let { String(password) }
    }
}

fun main() {
    Puzzle5.runBoth()
}