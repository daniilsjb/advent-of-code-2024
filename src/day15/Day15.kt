package day15

import java.io.File

fun main() {
    val data = parse("src/day15/Day15.txt")

    println("🎄 Day 15 🎄")

    println()

    println("[Part 1]")
    println("Answer: ${part1(data)}")

    println()

    println("[Part 2]")
    println("Answer: ${part2(data)}")
}

private data class Data(
    val grid: List<List<Char>>,
    val moves: List<Char>,
)

private fun parse(path: String): Data {
    val (gridText, movesText) = File(path)
        .readText()
        .trim()
        .split("\r\n\r\n")

    val grid = gridText
        .lines()
        .map { it.toList() }

    val moves = movesText
        .lines()
        .flatMap { it.toList() }

    return Data(grid, moves)
}

private fun part1(data: Data): Int {
    val (grid, moves) = data

    var rx = 0
    var ry = 0

    val walls = mutableSetOf<Pair<Int, Int>>()
    val boxes = mutableListOf<Pair<Int, Int>>()

    val h = grid.size
    val w = grid.first().size

    for (y in 0 until h) {
        for (x in 0 until w) {
            when (grid[y][x]) {
                '#' -> walls += x to y
                'O' -> boxes += x to y
                '@' -> { rx = x; ry = y }
            }
        }
    }

    for (move in moves) {
        val (ox, oy) = when (move) {
            '^' -> +0 to -1
            'v' -> +0 to +1
            '<' -> -1 to +0
            '>' -> +1 to +0
            else -> error("Invalid move: '$move'")
        }

        var x = rx + ox
        var y = ry + oy

        val indices = mutableListOf<Int>()
        while ((x to y) in boxes) {
            indices += boxes.indexOf(x to y)
            x += ox
            y += oy
        }

        if ((x to y) !in walls) {
            rx += ox
            ry += oy

            for (i in indices) {
                val (bx, by) = boxes[i]
                boxes[i] = bx + ox to by + oy
            }
        }
    }

    return boxes.sumOf { (bx, by) -> by * 100 + bx }
}

private fun part2(data: Data): Int {
    val (grid, moves) = data

    var rx = 0
    var ry = 0

    val walls = mutableSetOf<Pair<Int, Int>>()
    val boxes = mutableListOf<Pair<Int, Int>>()

    val h = grid.size
    val w = grid.first().size

    for (y in 0 until h) {
        for (x in 0 until w) {
            when (grid[y][x]) {
                '@' -> { rx = 2 * x; ry = y }
                'O' -> { boxes += (2 * x) to y }
                '#' -> {
                    walls += (2 * x + 0) to y
                    walls += (2 * x + 1) to y
                }
            }
        }
    }

    for (move in moves) {
        val (ox, oy) = when (move) {
            '^' -> +0 to -1
            'v' -> +0 to +1
            '<' -> -1 to +0
            '>' -> +1 to +0
            else -> error("Invalid move: '$move'")
        }

        val visited = mutableSetOf<Pair<Int, Int>>()
        val queue = ArrayDeque<Pair<Int, Int>>()
        queue += (rx + ox) to (ry + oy)

        var moving = true
        val indices = mutableSetOf<Int>()
        while (queue.isNotEmpty()) {
            val (x, y) = queue.removeFirst()
            if ((x to y) in walls) {
                moving = false
                break
            }

            if ((x to y) in boxes) {
                indices += boxes.indexOf(x to y)

                val lhs = x + 0 + ox to y + oy
                val rhs = x + 1 + ox to y + oy

                if (lhs !in visited) {
                    visited += lhs
                    queue += lhs
                }

                if (rhs !in visited) {
                    visited += rhs
                    queue += rhs
                }
            } else if ((x - 1 to y) in boxes) {
                indices += boxes.indexOf(x - 1 to y)

                val lhs = x - 1 + ox to y + oy
                val rhs = x + 0 + ox to y + oy

                if (lhs !in visited) {
                    visited += lhs
                    queue += lhs
                }

                if (rhs !in visited) {
                    visited += rhs
                    queue += rhs
                }
            }
        }

        if (moving) {
            rx += ox
            ry += oy

            for (i in indices) {
                val (bx, by) = boxes[i]
                boxes[i] = bx + ox to by + oy
            }
        }
    }

    return boxes.sumOf { (bx, by) -> by * 100 + bx }
}
