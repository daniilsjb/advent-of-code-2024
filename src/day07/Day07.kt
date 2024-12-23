package day07

import java.io.File

fun main() {
    val data = parse("src/day07/Day07.txt")

    println("🎄 Day 07 🎄")

    println()

    println("[Part 1]")
    println("Answer: ${part1(data)}")

    println()

    println("[Part 2]")
    println("Answer: ${part2(data)}")
}

private data class Equation(
    val target: Long,
    val values: List<Long>,
)

private fun parse(path: String): List<Equation> =
    File(path).readLines().map { it.toEquation() }

private fun String.toEquation(): Equation {
    val (target, remainder) = this.split(":")
    val values = remainder.trim().split(" ").map { it.toLong() }
    return Equation(target.toLong(), values)
}

private infix fun Long.c(other: Long): Long =
    (this.toString() + other.toString()).toLong()

private fun part1(data: List<Equation>): Long {
    fun List<Long>.satisfies(target: Long): Boolean {
        if (this.size == 1) {
            return this[0] == target
        }

        val a = this[0]
        val b = this[1]

        val tail = this.drop(2)

        return (
            (listOf(a + b) + tail).satisfies(target) ||
            (listOf(a * b) + tail).satisfies(target)
        )
    }

    return data
        .filter { (target, values) -> values.satisfies(target) }
        .sumOf { it.target }
}

private fun part2(data: List<Equation>): Long {
    fun List<Long>.satisfies(target: Long): Boolean {
        if (this.size == 1) {
            return this[0] == target
        }

        val x = this[0]
        val y = this[1]

        val tail = this.drop(2)

        return (
            (listOf(x + y) + tail).satisfies(target) ||
            (listOf(x * y) + tail).satisfies(target) ||
            (listOf(x c y) + tail).satisfies(target)
        )
    }

    return data
        .filter { (target, values) -> values.satisfies(target) }
        .sumOf { it.target }
}
