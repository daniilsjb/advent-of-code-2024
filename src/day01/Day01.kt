package day01

import java.io.File
import kotlin.math.absoluteValue

fun main() {
    val data = parse("src/day01/Day01.txt")

    println("🎄 Day 01 🎄")

    println()

    println("[Part 1]")
    println("Answer: ${part1(data)}")

    println()

    println("[Part 2]")
    println("Answer: ${part2(data)}")
}

private fun parse(path: String): Pair<List<Int>, List<Int>> {
    val xs = mutableListOf<Int>()
    val ys = mutableListOf<Int>()

    File(path).forEachLine { line ->
        val (x, y) = line
            .split("   ")
            .map { it.toInt() }

        xs += x
        ys += y
    }

    return Pair(xs, ys)
}

private fun part1(data: Pair<List<Int>, List<Int>>): Int =
    data.let { (xs, ys) ->
        (xs.sorted() zip ys.sorted())
            .sumOf { (x, y) -> (x - y).absoluteValue }
    }

private fun part2(data: Pair<List<Int>, List<Int>>): Int {
    val (xs, ys) = data
    val cache = ys.groupingBy { it }.eachCount()
    return xs.sumOf { it * (cache[it] ?: 0) }
}
