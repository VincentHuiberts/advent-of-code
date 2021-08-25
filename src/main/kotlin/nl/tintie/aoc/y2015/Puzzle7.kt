package nl.tintie.aoc.y2015

import nl.tintie.aoc.AocPuzzle
import java.lang.Exception

@OptIn(ExperimentalUnsignedTypes::class)
object Puzzle7 : AocPuzzle(2015, 7) {
    val assignPropertyPattern = """^(\w+) -> (\w+)$""".toRegex()
    val andPattern = """^(\w+) AND (\w+) -> (\w+)$""".toRegex()
    val orPattern = """^(\w+) OR (\w+) -> (\w+)$""".toRegex()
    val lshiftPattern = """^(\w+) LSHIFT (\d+) -> (\w+)$""".toRegex()
    val rshiftPattern = """^(\w+) RSHIFT (\d+) -> (\w+)$""".toRegex()
    val notPattern = """^NOT (\w+) -> (\w+)$""".toRegex()

    val types = listOf(
        assignPropertyPattern to InstructionType.ASSIGN_PROPERTY,
        andPattern to InstructionType.AND,
        orPattern to InstructionType.OR,
        lshiftPattern to InstructionType.LSHIFT,
        rshiftPattern to InstructionType.RSHIFT,
        notPattern to InstructionType.NOT
    )

    enum class InstructionType { ASSIGN_PROPERTY, AND, OR, LSHIFT, RSHIFT, NOT }

    val singleInput = listOf(InstructionType.ASSIGN_PROPERTY, InstructionType.NOT)

    data class Instruction(
        val type: InstructionType,
        val match: MatchResult
    ) {
        val inputs = when(type) {
            in singleInput -> listOf(get(1))
            else -> listOf(get(1), get(2))
        }
        val output = when(type) {
            in singleInput -> get(2)
            else -> get(3)
        }

        fun isNumber(group: Int) = get(group).matches("\\d+".toRegex())
        fun get(group: Int) = match.groupValues[group]
        fun getShort(group: Int) = match.groupValues[group].toUShort()
    }

    val instructions = input.map { line ->
        val (pattern, type) = types.find { (pattern, _) -> pattern.matches(line) } ?: throw Exception("No type found: $line")
        Instruction(type, pattern.matchEntire(line)!!)
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun Map<String, UShort>.processInstruction(instruction: Instruction): Map<String, UShort> {
        val wires = toMutableMap()
        fun getValue(group: Int) = takeIf { instruction.isNumber(group) }?.let{ instruction.getShort(group) } ?: wires[instruction.get(group)]!!
        when(instruction.type) {
            InstructionType.ASSIGN_PROPERTY -> wires[instruction.get(2)] = getValue(1)
            InstructionType.AND -> wires[instruction.get(3)] = getValue(1) and getValue(2)
            InstructionType.OR -> wires[instruction.get(3)] = getValue(1) or getValue(2)
            InstructionType.LSHIFT -> wires[instruction.get(3)] = (getValue(1).toUInt() shl (getValue(2).toInt())).toUShort()
            InstructionType.RSHIFT -> wires[instruction.get(3)] = (getValue(1).toUInt() shr (getValue(2).toInt())).toUShort()
            InstructionType.NOT -> wires[instruction.get(2)] = getValue(1).inv()
        }
        return wires.toMap()
    }

    override fun part1(): Any? {
        var leftInstructions = instructions
        var wires = mapOf<String, UShort>()
        while(leftInstructions.isNotEmpty() && !wires.containsKey("a")) {
            val processableInstructions = leftInstructions
                .filter { instr -> instr.inputs.all { it.matches("\\d+".toRegex()) || wires.containsKey(it) } }
            val updatedWires = processableInstructions.fold(wires) { acc, instr ->
                acc.processInstruction(instr)
            }
            wires = updatedWires
        }
        return wires["a"]
    }

    override fun part2(): Any? {
        val others = instructions.filterNot { it.output == "b" }
        var leftInstructions = others + Instruction(
            InstructionType.ASSIGN_PROPERTY,
            assignPropertyPattern.matchEntire("${part1()} -> b")!!
        )
        var wires = mapOf<String, UShort>()
        while(leftInstructions.isNotEmpty() && !wires.containsKey("a")) {
            val processableInstructions = leftInstructions
                .filter { instr -> instr.inputs.all { it.matches("\\d+".toRegex()) || wires.containsKey(it) } }
            val updatedWires = processableInstructions.fold(wires) { acc, instr ->
                acc.processInstruction(instr)
            }
            wires = updatedWires
        }
        return wires["a"]
    }
}

fun main() {
    Puzzle7.runBoth()
}