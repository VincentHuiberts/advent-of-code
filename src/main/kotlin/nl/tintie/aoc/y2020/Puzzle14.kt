package nl.tintie.aoc.y2020

import nl.tintie.aoc.AocPuzzle
import nl.tintie.aoc.combinations

object Puzzle14 : AocPuzzle(2020, 14) {
    val SET_MEM_PATTERN = """mem\[(\d+)\] = (\d+)""".toRegex()

    fun Long.toBinStr() = toString(2).padStart(36, '0')
    fun List<Char>.toLong() = joinToString("").toLong(2)

    class Accumulator constructor(
        var mask: String? = null,
        val memory: MutableMap<Long, Long> = mutableMapOf()
    ) {
        fun getMaskedVal(value: Long): Long {
            val bits = value.toBinStr()
            return mask!!.toCharArray().mapIndexed { i, char ->
                if (char == 'X') bits[i] else char
            }.toLong()
        }
    }

    override fun part1(): Any? {
        return input.fold(Accumulator()) { acc, line ->
            if (line.startsWith("mask = ")) {
                acc.mask = line.substring(7)
            } else {
                val (address, num) = SET_MEM_PATTERN.find(line)!!.groupValues.drop(1).map(String::toLong)
                acc.memory[address] = acc.getMaskedVal(num)
            }
            acc
        }.memory.values.sum()
    }

    override fun part2(): Any? {
        return input.fold(Accumulator()) { acc, line ->
            if (line.startsWith("mask = ")) {
                acc.mask = line.substring(7)
            } else {
                val xIdx = acc.mask!!.mapIndexedNotNull { index, c -> if (c == 'X') index else null }
                val xOpts = combinations(listOf('0', '1'), xIdx.size)
                val (address, num) = SET_MEM_PATTERN.find(line)!!.groupValues.drop(1).map(String::toLong)
                val addressBin = address.toBinStr()

                val maskedAddress = acc.mask!!.toCharArray().mapIndexed { i, char ->
                    if (char == '0') addressBin[i] else char
                }

                xOpts.forEach { opt ->
                    val updatedAddress = opt.foldIndexed(maskedAddress.toCharArray()) { i, updatedAddress, value ->
                        updatedAddress[xIdx[i]] = value
                        updatedAddress
                    }.toList()


                }
            }
            acc
        }.memory.values.sum()
    }
}

fun main() {
    Puzzle14.runBoth()
}