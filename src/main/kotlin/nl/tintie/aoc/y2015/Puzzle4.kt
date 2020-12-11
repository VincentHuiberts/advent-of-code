package nl.tintie.aoc.y2015

import nl.tintie.aoc.AocPuzzle
import java.security.MessageDigest

object Puzzle4 : AocPuzzle(2015, 4) {
    val prefix = input.first()

    fun List<Byte>.toHex() = joinToString("") { "%02x".format(it) }

    override fun part1(): Any? {
        var found: Int? = null
        var num = 0
        val md = MessageDigest.getInstance("MD5")
        val zero: Byte = 0
        while(found == null) {
            md.update("$prefix$num".toByteArray())
            val digest = md.digest()
            found = if(digest.take(3).toHex().startsWith("00000")) num else null
            num++
        }
        return found
    }

    override fun part2(): Any? {
        var found: Int? = null
        var num = 0
        val md = MessageDigest.getInstance("MD5")
        while(found == null) {
            md.update("$prefix$num".toByteArray())
            found = if(md.digest().take(3).all { it.toInt() == 0 }) num else null
            num++
        }
        return found
    }
}

fun main() {
    Puzzle4.runBoth()
}