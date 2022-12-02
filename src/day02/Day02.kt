package day02

import readInput

fun main() {
    val left = "ABC"
    val right = "XYZ"
    val choices = left.length

    fun part1(input: List<String>) = input.map { strategy ->
        strategy.split(" ").let {
            left.indexOf(it.first()) to right.indexOf(it.last())
        }
    }.sumOf { (first, second) ->
        when (Math.floorMod(first - second, choices)) {
            0 -> 3
            1 -> 0
            2 -> 6
            else -> throw IllegalStateException()
        } + second + 1
    }

    fun part2(input: List<String>) = input.map { strategy ->
        strategy.split(" ").let {
            left.indexOf(it.first()) to right.indexOf(it.last())
        }
    }.sumOf { (first, second) ->
        second * 3 + Math.floorMod(first + second - 1, 3) + 1
    }

    val testInput = readInput("day02/test")
    check(part1(testInput) == 3 * 1 + 3 * 2 + 3 * 3 + 3 * 3 + 3 * 6)

    val input = readInput("day02/input")
    println(part1(input))
    println(part2(input))
}
