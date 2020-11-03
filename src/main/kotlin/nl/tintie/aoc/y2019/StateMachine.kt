package nl.tintie.aoc.y2019

data class Value(val value: Int, val position: Int)

class StateMachine(
    initialProgram: List<Int>,
    val input: Int? = null
) {
    val program = initialProgram.toMutableList()
    var position = 0
    var output = mutableListOf<Int>()

    fun run() {
        while (program[position] != 99) {
            processInstruction()
        }
    }

    private fun getNValues(n: Int, modes: List<String>) = (1..n).mapIndexed { i, offset ->
        val position = when (modes.getOrNull(i)) {
            "1" -> position + offset
            else -> program[position + offset]
        }
        Value(program[position], position)
    }

    private fun processInstruction() {
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
                program[p1.position] = input!!
                position += 2
            }
            4 -> {
                val (p1) = getNValues(1, modes)
                output.add(p1.value)
                position += 2
            }
            5 -> {
                val (p1, p2) = getNValues(2, modes)
                if (p1.value != 0) position = p2.value
                else position += 3
            }
            6 -> {
                val (p1, p2) = getNValues(2, modes)
                if (p1.value == 0) position = p2.value
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
            else -> throw IllegalStateException("Unknown instruction ${program[position]}")
        }
    }
}