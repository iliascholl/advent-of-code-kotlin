package day05

import day05.Instruction.Companion.toInstruction
import readInput

private typealias Stack = MutableMap.MutableEntry<Int, ArrayDeque<Char>>
private typealias Stacks = MutableMap<Int, ArrayDeque<Char>>

private data class Instruction(
    val amount: Int,
    val from: Int,
    val to: Int,
) {
    companion object {
        private operator fun MatchResult?.get(key: String): Int = this
            ?.groups
            ?.get(key)
            ?.value
            ?.toInt()
            ?: throw IllegalArgumentException("Cannot parse '$this'")

        fun String.toInstruction() =
            Regex("^move (?<amount>\\d+) from (?<from>\\d+) to (?<to>\\d+)$")
                .matchEntire(this)
                .let {
                    Instruction(
                        amount = it["amount"],
                        from = it["from"],
                        to = it["to"],
                    )
                }
    }
}

private fun List<String>.readInitialState(): Pair<Stacks, List<Instruction>> {
    val separator = indexOfFirst(String::isBlank)
    val stacks = subList(0, separator)
    val instructions = subList(separator + 1, size)
    return stacks.readStacks() to instructions.readInstructions()
}

private fun List<String>.readInstructions() = map { instruction ->
    instruction.toInstruction()
}

private fun List<String>.readStacks(): Stacks = ArrayDeque(reversed()).let { deque ->
    val columns = deque.removeFirst()
        .withIndex()
        .filter { it.value.isDigit() }
        .map { IndexedValue(it.index, it.value.digitToInt()) }
    val stacks = mutableMapOf<Int, ArrayDeque<Char>>()

    columns.forEach { stacks[it.value] = ArrayDeque() }

    deque.forEach { line ->
        columns.forEach { (index, value) ->
            line[index]
                .takeIf(Char::isLetter)
                ?.let { stacks[value]?.addFirst(line[index]) }
        }
    }

    return stacks
}

private fun processInstruction(stacks: Stacks, instruction: Instruction) = stacks.also {
    val (amount, from, to) = instruction
    repeat(amount) {
        stacks[from]?.removeFirst()
            ?.let { stacks[to]?.addFirst(it) }
    }
}

private fun processInstructionMultiContainer(stacks: Stacks, instruction: Instruction) = stacks.also {
    val (amount, from, to) = instruction
    List(amount) {
        stacks[from]?.removeFirst()
    }.reversed().forEach {
        it?.let { stacks[to]?.addFirst(it) }
    }
}

private fun Stacks.output() =
    entries.sortedBy(Stack::key).joinToString("") { it.value.removeFirst().toString() }

fun main() {
    fun part1(input: List<String>) = input
        .readInitialState()
        .let { (stacks, instructions) -> instructions.fold(stacks, ::processInstruction) }
        .output()

    fun part2(input: List<String>) = input
        .readInitialState()
        .let { (stacks, instructions) -> instructions.fold(stacks, ::processInstructionMultiContainer) }
        .output()

    check(part1(readInput("day05/test")) == "CMZ")
    println(part1(readInput("day05/input")))

    check(part2(readInput("day05/test")) == "MCD")
    println(part2(readInput("day05/input")))
}
