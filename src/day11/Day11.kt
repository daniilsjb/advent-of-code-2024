package day11

import java.io.File

fun main() {
    val data = parse("src/day11/Day11.txt")

    println("🎄 Day 11 🎄")

    println()

    println("[Part 1]")
    println("Answer: ${part1(data)}")

    println()

    println("[Part 2]")
    println("Answer: ${part2(data)}")
}

private fun parse(path: String): List<Long> =
    File(path).readText().trim().split(" ").map { it.toLong() }

private fun run(stones: List<Long>, times: Int): Long {
    val cache = mutableMapOf<Pair<Long, Int>, Long>()

    fun search(stone: Long, depth: Int = times): Long {
        if (depth <= 0) {
            return 1
        }

        val cached = cache[stone to depth]
        if (cached != null) {
            return cached
        }

        if (stone == 0L) {
            return search(2024L, depth - 2)
                .also { cache[stone to depth] = it }
        }

        val digits = stone.toString()
        if (digits.length % 2 == 0) {
            val lhs = digits.substring(0, digits.length / 2).toLong()
            val rhs = digits.substring(digits.length / 2).toLong()
            return (search(lhs, depth - 1) + search(rhs, depth - 1))
                .also { cache[stone to depth] = it }
        }

        return search(stone * 2024L, depth - 1)
            .also { cache[stone to depth] = it }
    }

    var count = 0L
    for (stone in stones) {
        count += search(stone)
    }

    return count
}

private fun part1(data: List<Long>): Long =
    run(data, 25)

private fun part2(data: List<Long>): Long =
    run(data, 75)
