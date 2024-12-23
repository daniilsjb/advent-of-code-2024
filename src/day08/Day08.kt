package day08

import java.io.File

fun main() {
    val data = parse("src/day08/Day08.txt")

    println("🎄 Day 08 🎄")

    println()

    println("[Part 1]")
    println("Answer: ${part1(data)}")

    println()

    println("[Part 2]")
    println("Answer: ${part2(data)}")
}

private data class Point(
    val x: Int,
    val y: Int,
)

private operator fun Point.plus(other: Point): Point =
    Point(x + other.x, y + other.y)

private operator fun Point.minus(other: Point): Point =
    Point(x - other.x, y - other.y)

private data class Data(
    val w: Int,
    val h: Int,
    val antennas: Map<Char, List<Point>>,
)

private fun parse(path: String): Data {
    val grid = File(path).readLines().map { it.toList() }

    val h = grid.size
    val w = grid.first().size

    val antennas = mutableMapOf<Char, List<Point>>()
    for (y in 0 until h) {
        for (x in 0 until w) {
            val symbol = grid[y][x]
            if (symbol != '.') {
                antennas.merge(symbol, listOf(Point(x, y))) { acc, it -> acc + it }
            }
        }
    }

    return Data(w, h, antennas)
}

private fun part1(data: Data): Int {
    val (w, h, antennas) = data

    fun inside(point: Point): Boolean =
        point.x >= 0 && point.y >= 0 && point.x < w && point.y < h

    val nodes = mutableSetOf<Point>()
    for (points in antennas.values) {
        for (i in 0..points.lastIndex) {
            for (j in (i + 1)..points.lastIndex) {
                val pointA = points[i]
                val pointB = points[j]

                val difference = pointB - pointA

                val nodeA = pointA - difference
                val nodeB = pointB + difference

                if (inside(nodeA)) nodes += nodeA
                if (inside(nodeB)) nodes += nodeB
            }
        }
    }

    return nodes.size
}

private fun part2(data: Data): Int {
    val (w, h, antennas) = data

    fun inside(point: Point): Boolean =
        point.x >= 0 && point.y >= 0 && point.x < w && point.y < h

    val nodes = mutableSetOf<Point>()
    for (points in antennas.values) {
        for (i in 0..points.lastIndex) {
            for (j in (i + 1)..points.lastIndex) {
                val pointA = points[i]
                val pointB = points[j]

                nodes += pointA
                nodes += pointB

                val difference = pointB - pointA

                var nodeA = pointA - difference
                while (inside(nodeA)) {
                    nodes += nodeA
                    nodeA -= difference
                }

                var nodeB = pointA + difference
                while (inside(nodeB)) {
                    nodes += nodeB
                    nodeB += difference
                }
            }
        }
    }

    return nodes.size
}
