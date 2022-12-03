@file:Suppress("ConstPropertyName")

package day03

import readInput

private const val codeOffset_A = 'A'.code - 27
private const val codeOffset_a = 'a'.code - 1

private val Char.priority
    get() = (code - codeOffset_A) % (codeOffset_a - codeOffset_A)

private fun String.splitCompartments() = substring(0, length / 2).toSet() to substring(length / 2, length).toSet()

fun main() {
    fun part1(input: List<String>) = input
        .map(String::splitCompartments)
        .map { (first, second) -> first intersect second }
        .sumOf { it.first().priority }

    fun part2(input: List<String>) = input
        .withIndex().groupBy { it.index / 3 }
        .map { (_, value) -> value.map { it.value.toSet() } }
        .sumOf { it.reduce(Set<Char>::intersect).first().priority }

    val testInput = readInput("day03/test")
    check(part1(testInput) == 1 + 26 + 27 + 52)

    val input = readInput("day03/input")
    println(part1(input))
    println(part2(input))
}
