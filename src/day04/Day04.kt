package day04

import readInput

private fun String.toRangePair(): Pair<IntRange, IntRange> =
    split(',', limit = 2)
        .map(String::toRange)
        .let { (first, second) -> first to second }

private fun String.toRange(): IntRange =
    split('-', limit = 2)
        .let { (start, end) -> start.toInt()..end.toInt() }

private infix fun IntRange.fullyContains(other: IntRange): Boolean =
    contains(other.first) && contains(other.last)

private infix fun IntRange.overlaps(other: IntRange): Boolean =
    this.contains(other.first) || other.contains(this.first)

fun main() {
    fun part1(input: List<String>) = input
        .map(String::toRangePair)
        .count { (first, second) -> first fullyContains second || second fullyContains first }

    fun part2(input: List<String>) = input
        .map(String::toRangePair)
        .count { (first, second) -> first overlaps second }

    val testInput = readInput("day04/test")
    check(part1(testInput) == 3)

    val input = readInput("day04/input")
    println(part1(input))
    println(part2(input))
}
