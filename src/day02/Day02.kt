package day02

import java.io.File

fun main() {
    val data = parse("src/day02/Day02.txt")

    println("🎄 Day 02 🎄")

    println()

    println("[Part 1]")
    println("Answer: ${part1(data)}")

    println()

    println("[Part 2]")
    println("Answer: ${part2(data)}")
}

private fun parse(path: String): List<List<Int>> =
    File(path)
        .readLines()
        .map { it.split(" ").map { it.toInt() } }

private fun safe(levels: List<Int>): Boolean =
    levels.zipWithNext { a, b -> b - a }.let {
        it.all { it >= 1 && it <= 3 } || it.all { it >= -3 && it <= -1 }
    }

private fun List<Int>.removed(index: Int): List<Int> =
    this.subList(0, index) + this.subList(index + 1, this.size)

private fun part1(data: List<List<Int>>): Int =
    data.count { levels -> safe(levels) }

private fun part2(data: List<List<Int>>): Int =
    data.count { levels ->
        safe(levels) || (0..levels.lastIndex).any { safe(levels.removed(it)) }
    }
