package day04

import java.io.File

fun main() {
    val data = parse("src/day04/Day04.txt")

    println("🎄 Day 04 🎄")

    println()

    println("[Part 1]")
    println("Answer: ${part1(data)}")

    println()

    println("[Part 2]")
    println("Answer: ${part2(data)}")
}

private fun parse(path: String): List<String> =
    File(path).readLines()

private fun part1(data: List<String>): Int {
    val directions = listOf(
        // Horizontal
        listOf(0 to +1, 0 to +2, 0 to +3),
        listOf(0 to -1, 0 to -2, 0 to -3),

        // Vertical
        listOf(+1 to 0, +2 to 0, +3 to 0),
        listOf(-1 to 0, -2 to 0, -3 to 0),

        // Diagonal (Up)
        listOf(-1 to -1, -2 to -2, -3 to -3),
        listOf(+1 to -1, +2 to -2, +3 to -3),

        // Diagonal (Down)
        listOf(-1 to +1, -2 to +2, -3 to +3),
        listOf(+1 to +1, +2 to +2, +3 to +3),

    )

    var total = 0
    for (y in data.indices) {
        for (x in data[y].indices) {
            if (data[y][x] != 'X') continue

            for (offsets in directions) {
                val remainder = offsets.mapNotNull { (dx, dy) ->
                    data.getOrNull(y + dy)?.getOrNull(x + dx)
                }.joinToString(separator = "")

                if (remainder == "MAS") {
                    total += 1
                }
            }
        }
    }
    return total
}

private fun part2(data: List<String>): Int {
    val directions = listOf(
        listOf(-1 to -1, 0 to 0, +1 to +1),
        listOf(-1 to +1, 0 to 0, +1 to -1),
    )

    var total = 0
    for (y in data.indices) {
        for (x in data[y].indices) {
            if (data[y][x] != 'A') continue

            val found = directions.map { offsets ->
                offsets.mapNotNull { (dx, dy) ->
                    data.getOrNull(y + dy)?.getOrNull(x + dx)
                }.joinToString(separator = "")
            }.all { it == "MAS" || it == "SAM" }

            if (found) {
                total += 1
            }
        }
    }
    return total
}
