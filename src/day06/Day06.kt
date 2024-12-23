package day06

import java.io.File

fun main() {
    val data = parse("src/day06/Day06.txt")

    println("🎄 Day 06 🎄")

    println()

    println("[Part 1]")
    println("Answer: ${part1(data)}")

    println()

    println("[Part 2]")
    println("Answer: ${part2(data)}")
}

private data class Data(
    val w: Int,
    val h: Int,
    val x: Int,
    val y: Int,
    val map: List<Char>,
)

private fun parse(path: String): Data {
    val rows = File(path)
        .readLines()
        .map { it.toList() }

    val h = rows.size
    val w = rows.first().size

    for (y in 0 until h) {
        for (x in 0 until w) {
            if (rows[y][x] == '^') {
                return Data(w, h, x, y, rows.flatten())
            }
        }
    }

    error("Guard is missing from the data.")
}

private fun trajectory(data: Data): Pair<Set<Pair<Int, Int>>, Boolean> {
    val (w, h, x, y, map) = data

    fun inside(x: Int, y: Int) =
        x >= 0 && x < w && y >= 0 && y < h

    var (px, py) = x to y
    var (dx, dy) = 0 to -1

    val visited = mutableSetOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
    var looping = false

    while (inside(px, py)) {
        val node = (px to py) to (dx to dy)
        if (node in visited) {
            looping = true
            break
        }

        visited += node

        val tx = px + dx
        val ty = py + dy
        if (inside(tx, ty) && map[ty * w + tx] == '#') {
            when {
                dy == -1 -> { dx = +1; dy = +0 }
                dx == +1 -> { dx = +0; dy = +1 }
                dy == +1 -> { dx = -1; dy = +0 }
                dx == -1 -> { dx = +0; dy = -1 }
            }
        } else {
            px = tx
            py = ty
        }
    }

    val points = visited
        .map { (point, _) -> point }
        .toSet()

    return points to looping
}

private fun part1(data: Data): Int =
    trajectory(data).let { (points, _) -> points.size }

private fun part2(data: Data): Int {
    val (points, _) = trajectory(data)
    return points.count { (px, py) ->
        val map = data.map
            .toMutableList()
            .apply { this[py * data.w + px] = '#' }

        val (_, loop) = trajectory(data.copy(map = map))
        loop
    }
}
