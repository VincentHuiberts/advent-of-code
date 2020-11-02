package nl.tintie.aoc.y2019

class StateMachine(initialProgram: List<Int>) {
    val program = initialProgram.toMutableList()
    var position = 0

    fun run() {
        while(program[position] != 99) {
            processInstruction()
        }
    }

    private fun processInstruction() {
        when (program[position]) {
            1 -> {
                val (p1, p2, p3) = listOf(1, 2, 3).map { offset -> program[position + offset] }
                program[p3] = program[p1] + program[p2]
                position += 4
            }
            2 -> {
                val (p1, p2, p3) = listOf(1, 2, 3).map { offset -> program[position + offset] }
                program[p3] = program[p1] * program[p2]
                position += 4
            }
        }
    }
}