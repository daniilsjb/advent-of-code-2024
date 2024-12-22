package day03

import java.io.File

fun main() {
    val data = parse("src/day03/Day03.txt")

    println("🎄 Day 03 🎄")

    println()

    println("[Part 1]")
    println("Answer: ${part1(data)}")

    println()

    println("[Part 2]")
    println("Answer: ${part2(data)}")
}

private fun parse(path: String): String =
    File(path).readText()

private fun part1(data: String): Int =
    """mul\((\d+),(\d+)\)""".toRegex()
        .findAll(data)
        .map { it.destructured }
        .sumOf { (a, b) -> a.toInt() * b.toInt() }

private fun part2(data: String): Int {
    val matches = """mul\((\d+),(\d+)\)|do\(\)|don't\(\)""".toRegex()
        .findAll(data)

    var total = 0
    var enabled = true
    for (match in matches) {
        when (match.groupValues.first()) {
            "do()" -> enabled = true
            "don't()" -> enabled = false
            else -> {
                val (a, b) = match.destructured
                if (enabled) {
                    total += a.toInt() * b.toInt()
                }
            }
        }
    }

    return total
}
