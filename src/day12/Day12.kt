package day12

import java.io.File

fun main() {
    val data = parse("src/day12/Day12.txt")

    println("🎄 Day 12 🎄")

    println()

    println("[Part 1]")
    println("Answer: ${part1(data)}")

    println()

    println("[Part 2]")
    println("Answer: ${part2(data)}")
}

private fun parse(path: String): List<List<Char>> =
    File(path).readLines().map { it.toList() }

private fun part1(data: List<List<Char>>): Long {
    val h = data.size
    val w = data.first().size

    val groups = mutableMapOf<Char, ArrayDeque<Pair<Int, Int>>>()
    for (y in 0 until h) {
        for (x in 0 until w) {
            groups
                .getOrPut(data[y][x]) { ArrayDeque() }
                .also { it += x to y }
        }
    }

    val regions = mutableListOf<List<Pair<Int, Int>>>()
    for ((_, group) in groups) {
        while (group.isNotEmpty()) {
            val nodes = mutableSetOf<Pair<Int, Int>>()
            val queue = ArrayDeque<Pair<Int, Int>>()
                .apply { add(group.removeFirst()) }

            while (queue.isNotEmpty()) {
                val next = queue.removeFirst()
                val (nx, ny) = next

                val neighbors = listOf(
                    Pair(nx + 1, ny),
                    Pair(nx - 1, ny),
                    Pair(nx, ny + 1),
                    Pair(nx, ny - 1),
                ).filter { (px, py) ->
                    px >= 0 && px < w && py >= 0 && py < h
                }.filter { neighbor ->
                    neighbor in group && neighbor !in nodes
                }

                for (neighbor in neighbors) {
                    queue += neighbor
                    group -= neighbor
                }

                nodes += next
            }

            regions += nodes.toList()
        }
    }

    var total = 0L
    for (region in regions) {
        val area = region.size
        var perimeter = 0

        val visited = mutableSetOf<Pair<Int, Int>>()
        val queue = ArrayDeque<Pair<Int, Int>>()
            .apply { add(region.first()) }

        while (queue.isNotEmpty()) {
            val next = queue.removeFirst()
            val (nx, ny) = next
            visited += next

            val neighbors = listOf(
                Pair(nx + 1, ny),
                Pair(nx - 1, ny),
                Pair(nx, ny + 1),
                Pair(nx, ny - 1),
            )

            for (neighbor in neighbors) {
                if (neighbor !in region) {
                    perimeter += 1
                } else if (neighbor !in visited) {
                    queue += neighbor
                    visited += neighbor
                }
            }
        }

        total += area * perimeter
    }

    return total
}

private fun part2(data: List<List<Char>>): Long {
    val h = data.size
    val w = data.first().size

    val groups = mutableMapOf<Char, ArrayDeque<Pair<Int, Int>>>()
    for (y in 0 until h) {
        for (x in 0 until w) {
            groups
                .getOrPut(data[y][x]) { ArrayDeque() }
                .also { it += x to y }
        }
    }

    val regions = mutableListOf<List<Pair<Int, Int>>>()
    for ((_, group) in groups) {
        while (group.isNotEmpty()) {
            val nodes = mutableSetOf<Pair<Int, Int>>()
            val queue = ArrayDeque<Pair<Int, Int>>()
                .apply { add(group.removeFirst()) }

            while (queue.isNotEmpty()) {
                val next = queue.removeFirst()
                val (nx, ny) = next

                val neighbors = listOf(
                    Pair(nx + 1, ny),
                    Pair(nx - 1, ny),
                    Pair(nx, ny + 1),
                    Pair(nx, ny - 1),
                ).filter { (px, py) ->
                    px >= 0 && px < w && py >= 0 && py < h
                }.filter { neighbor ->
                    neighbor in group && neighbor !in nodes
                }

                for (neighbor in neighbors) {
                    queue += neighbor
                    group -= neighbor
                }

                nodes += next
            }

            regions += nodes.toList()
        }
    }

    var total = 0L
    for (region in regions) {
        val visited = mutableSetOf<Pair<Int, Int>>()
        val borders = mutableSetOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
        val queue = ArrayDeque<Pair<Int, Int>>()
            .apply { add(region.first()) }

        while (queue.isNotEmpty()) {
            val next = queue.removeFirst()
            val (nx, ny) = next
            visited += next

            val offsets = listOf(
                Pair(+1, +0),
                Pair(-1, +0),
                Pair(+0, +1),
                Pair(+0, -1),
            )

            for (offset in offsets) {
                val (ox, oy) = offset
                val neighbor = Pair(nx + ox, ny + oy)
                if (neighbor !in region) {
                    borders += neighbor to offset
                } else if (neighbor !in visited) {
                    queue += neighbor
                    visited += neighbor
                }
            }
        }

        var area = region.size
        var lines = 0

        while (borders.isNotEmpty()) {
            lines += 1

            val borderQueue = ArrayDeque<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
            borderQueue += borders.first()

            while (borderQueue.isNotEmpty()) {
                val next = borderQueue.removeFirst()
                borders -= next

                val (point, offset) = next
                val (bx, by) = point

                val offsets = listOf(
                    Pair(+1, +0),
                    Pair(-1, +0),
                    Pair(+0, +1),
                    Pair(+0, -1),
                )

                for (o in offsets) {
                    val (ox, oy) = o
                    val neighbor = Pair(bx + ox, by + oy)
                    val border = Pair(neighbor, offset)
                    if (border in borders) {
                        borderQueue += border
                    }
                }
            }
        }

        total += area * lines
    }

    return total
}
