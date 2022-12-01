fun main() {
    fun part1(input: List<String>) = input
        .fold(mutableListOf(mutableListOf<String>())) { acc, value ->
            if (value.isBlank()) acc.also { it.add(mutableListOf()) }
            else acc.also { it.last().add(value) }
        }.maxOf { it.sumOf(String::toInt) }

    fun part2(input: List<String>) = input
        .fold(mutableListOf(mutableListOf<String>())) { acc, value ->
            if (value.isBlank()) acc.also { it.add(mutableListOf()) }
            else acc.also { it.last().add(value) }
        }.map { it.sumOf(String::toInt) }
        .sortedDescending()
        .take(3)
        .sum()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 19)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
