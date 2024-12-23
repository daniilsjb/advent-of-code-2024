package day09

import java.io.File

fun main() {
    val data = parse("src/day09/Day09.txt")

    println("🎄 Day 09 🎄")

    println()

    println("[Part 1]")
    println("Answer: ${part1(data)}")

    println()

    println("[Part 2]")
    println("Answer: ${part2(data)}")
}

private fun parse(path: String): String =
    File(path).readText().trim()

private fun part1(data: String): Long {
    val memory = data.mapIndexed { index, symbol ->
        val identifier = if (index and 1 == 0) index shr 1 else null
        (1..symbol.digitToInt()).map { identifier }
    }.flatten().toMutableList()

    var lhs = 0
    var rhs = memory.lastIndex

    while (lhs < rhs) {
        while (memory[lhs] != null) lhs += 1
        while (memory[rhs] == null) rhs -= 1

        while (memory[lhs] == null && memory[rhs] != null) {
            memory[lhs] = memory[rhs].also { memory[rhs] = memory[lhs] }
            lhs += 1
            rhs -= 1
        }
    }

    return memory.foldIndexed(0L) { index, acc, value ->
        acc + index * (value ?: 0)
    }
}

private fun part2(data: String): Long {
    val blocks = mutableListOf<Pair<Int, Int?>>()

    for ((index, symbol) in data.withIndex()) {
        val length = symbol.digitToInt()
        val identifier = if (index and 1 == 0) index shr 1 else null
        blocks += length to identifier
    }

    var rhs = blocks.lastIndex
    while (rhs >= 0) {
        var lhs = 0
        while (blocks[rhs].second == null) rhs -= 1

        while (lhs < rhs) {
            if (blocks[lhs].second == null && blocks[lhs].first >= blocks[rhs].first) {
                break
            }
            lhs += 1
        }

        if (lhs >= rhs) {
            rhs -= 1
            continue
        }


        val (lengthL, _) = blocks[lhs]
        val (lengthR, identifierR) = blocks[rhs]

        if (lengthR <= lengthL) {
            blocks[lhs] = lengthR to identifierR
            blocks[rhs] = lengthR to null

            val remainder = lengthL - lengthR
            if (remainder != 0) {
                blocks.add(lhs + 1, remainder to null)
            }

            lhs += 1
        }

        rhs -= 1
    }

    return blocks
        .filter { (length, id) -> length > 0 || id != null }
        .flatMap { (len, id) -> (1..len).map { id } }
        .foldIndexed(0L) { index, acc, value ->
            acc + index * (value ?: 0)
        }
}
