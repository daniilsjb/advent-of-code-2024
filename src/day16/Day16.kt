package day16

import java.io.File
import java.util.PriorityQueue

fun main() {
    val data = parse("src/day16/Day16.txt")

    println("🎄 Day 16 🎄")

    println()

    println("[Part 1]")
    println("Answer: ${part1(data)}")

    println()

    println("[Part 2]")
    println("Answer: ${part2(data)}")
}

private data class Grid(
    val startX: Int,
    val startY: Int,
    val layout: List<List<Char>>,
)

private fun parse(path: String): Grid {
    val layout = File(path)
        .readLines()
        .map { it.toList() }

    for ((y, row) in layout.withIndex()) {
        for ((x, col) in row.withIndex()) {
            if (col == 'S') {
                return Grid(x, y, layout)
            }
        }
    }

    error("Starting position not found.")
}

private data class Node(
    val px: Int,
    val py: Int,
    val vx: Int,
    val vy: Int,
    val cost: Int,
) : Comparable<Node> {
    override fun compareTo(other: Node): Int =
        cost.compareTo(other.cost)
}

private typealias Vector = Pair<Int, Int>

private fun part1(data: Grid): Int {
    val (sx, sy, layout) = data

    val nodes = mutableSetOf<Pair<Vector, Vector>>()
    val queue = PriorityQueue<Node>()

    queue += Node(sx, sy, 1, 0, 0)
    while (queue.isNotEmpty()) {
        val (px, py, vx, vy, cost) = queue.remove()
        if (layout[py][px] == 'E') {
            return cost
        }

        nodes += (px to py) to (vx to vy)
        val neighbors = listOf(
            Node(px + vx, py + vy, +vx, +vy, cost + 1),
            Node(px - vx, py - vy, -vx, -vy, cost + 2001),
            Node(px - vy, py + vx, -vy, +vx, cost + 1001),
            Node(px + vy, py - vx, +vy, -vx, cost + 1001),
        )

        for (neighbor in neighbors) {
            val (npx, npy, nvx, nvy) = neighbor
            if (layout[npy][npx] != '#') {
                val np = npx to npy
                val nv = nvx to nvy
                if (np to nv !in nodes) {
                    queue += neighbor
                }
            }
        }
    }

    error("Couldn't find the shortest path.")
}

private fun part2(data: Grid): Int {
    TODO()
}
