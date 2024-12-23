package day10

import java.io.File

fun main() {
    val data = parse("src/day10/Day10.txt")

    println("🎄 Day 10 🎄")

    println()

    println("[Part 1]")
    println("Answer: ${part1(data)}")

    println()

    println("[Part 2]")
    println("Answer: ${part2(data)}")
}

private fun parse(path: String): List<List<Char>> =
    File(path)
        .readLines()
        .map { it.toList() }

private fun part1(data: List<List<Char>>): Int {
    val h = data.size
    val w = data.first().size

    val trailheads = mutableSetOf<Pair<Int, Int>>()
    for (y in 0 until h) {
        for (x in 0 until w) {
            if (data[y][x] == '0') {
                trailheads += x to y
            }
        }
    }

    var score = 0
    for ((tx, ty) in trailheads) {
        val nodes = mutableSetOf<Pair<Int, Int>>()
        val queue = ArrayDeque<Pair<Int, Int>>().apply { add(tx to ty) }

        while (queue.isNotEmpty()) {
            val next = queue.removeFirst()

            val (nx, ny) = next
            if (data[ny][nx] == '9') {
                nodes += next
            }

            val neighbors = listOf(
                Pair(nx + 1, ny),
                Pair(nx - 1, ny),
                Pair(nx, ny + 1),
                Pair(nx, ny - 1),
            ).filter { (px, py) ->
                px >= 0 && px < w && py >= 0 && py < h
            }.filter { (px, py) ->
                val src = data[ny][nx].digitToInt()
                val dst = data[py][px].digitToInt()
                dst == src + 1
            }

            for (neighbor in neighbors) {
                queue += neighbor
            }
        }

        score += nodes.size
    }

    return score
}

private fun part2(data: List<List<Char>>): Int {
    val h = data.size
    val w = data.first().size

    val trailheads = mutableSetOf<Pair<Int, Int>>()
    for (y in 0 until h) {
        for (x in 0 until w) {
            if (data[y][x] == '0') {
                trailheads += x to y
            }
        }
    }

    fun search(source: Pair<Int, Int>): Int {
        val (sx, sy) = source
        if (data[sy][sx] == '9') {
            return 1
        }

        val neighbors = listOf(
            Pair(sx + 1, sy),
            Pair(sx - 1, sy),
            Pair(sx, sy + 1),
            Pair(sx, sy - 1),
        ).filter { (px, py) ->
            px >= 0 && px < w && py >= 0 && py < h
        }.filter { (px, py) ->
            val src = data[sy][sx].digitToInt()
            val dst = data[py][px].digitToInt()
            dst == src + 1
        }

        return neighbors.sumOf { search(it) }
    }

    var rating = 0
    for (trailhead in trailheads) {
        rating += search(trailhead)
    }

    return rating
}
