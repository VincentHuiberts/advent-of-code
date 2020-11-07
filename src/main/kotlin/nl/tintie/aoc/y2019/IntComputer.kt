package nl.tintie.aoc.y2019

data class Value(val value: Long, val position: Int)

class IntComputer   (
    initialProgram: List<Long>,
    initialInput: List<Int> = listOf()
) {
    val program = initialProgram.map { it.toLong() }.toMutableList()
    var position = 0

    val input = initialInput.map { it.toLong() }.toMutableList()
    var inputPosition = 0

    var output = mutableListOf<Long>()
    var finished = false

    var relativeBase = 0L

    fun runTillFinished() {
        while (program[position] != 99L) {
            processNextInstruction()
        }
        finished = true
    }

    fun runToNextOutput(): Long {
        val originalOutputSize = output.size
        while (originalOutputSize == output.size && program[position] != 99L) {
            processNextInstruction()
        }
        if(program[position] == 99L) finished = true
        return output.last()
    }

    private fun getInput(): Long {
        return input[inputPosition++].toLong()
    }

    private fun getNValues(n: Int, modes: List<String>) = (1..n).mapIndexed { i, offset ->
        val position = when (modes.getOrNull(i)) {
            "1" -> position + offset
            "2" -> program[position + offset] + relativeBase
            else -> program[position + offset]
        }.toInt()
        if(position.toLong() > program.size - 1L) {
            val diff = position.toLong() - program.size + 1L
            repeat(diff.toInt()) { program.add(0) }
        }
        Value(program[position], position)
    }

    private fun processNextInstruction() {
        val (instruction, modes) =
            program[position].toString().reversed().let { it.take(2).reversed().toInt() to it.drop(2).chunked(1) }
        when (instruction) {
            1 -> {
                val (p1, p2, p3) = getNValues(3, modes)
                program[p3.position] = p1.value + p2.value
                position += 4
            }
            2 -> {
                val (p1, p2, p3) = getNValues(3, modes)
                program[p3.position] = p1.value * p2.value
                position += 4
            }
            3 -> {
                val (p1) = getNValues(1, modes)
                program[p1.position] = getInput()
                position += 2
            }
            4 -> {
                val (p1) = getNValues(1, modes)
                output.add(p1.value)
                position += 2
            }
            5 -> {
                val (p1, p2) = getNValues(2, modes)
                if (p1.value != 0L) position = p2.value.toInt()
                else position += 3
            }
            6 -> {
                val (p1, p2) = getNValues(2, modes)
                if (p1.value == 0L) position = p2.value.toInt()
                else position += 3
            }
            7 -> {
                val (p1, p2, p3) = getNValues(3, modes)
                program[p3.position] = if (p1.value < p2.value) 1 else 0
                position += 4
            }
            8 -> {
                val (p1, p2, p3) = getNValues(3, modes)
                program[p3.position] = if (p1.value == p2.value) 1 else 0
                position += 4
            }
            9 -> {
                val (p1) = getNValues(1, modes)
                relativeBase += p1.value
                position += 2
            }
            else -> throw IllegalStateException("Unknown instruction ${program[position]}")
        }
    }
}