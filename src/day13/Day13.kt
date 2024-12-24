package day13

import java.io.File

fun main() {
    val data = parse("src/day13/Day13.txt")

    println("🎄 Day 13 🎄")

    println()

    println("[Part 1]")
    println("Answer: ${part1(data)}")

    println()

    println("[Part 2]")
    println("Answer: ${part2(data)}")
}

private data class Machine(
    val xa: Long, val ya: Long,
    val xb: Long, val yb: Long,
    val xt: Long, val yt: Long,
)

private val PATTERN_BUTTON = """Button .: X\+(\d+), Y\+(\d+)""".toRegex()
private val PATTERN_TARGET = """Prize: X=(\d+), Y=(\d+)""".toRegex()

private fun parse(path: String): List<Machine> {
    val blocks = File(path)
        .readText()
        .trim()
        .split("\r\n\r\n")

    return blocks.map { block ->
        val (a, b, t) = block.lines()
        val (xa, ya) = PATTERN_BUTTON.find(a)!!.destructured
        val (xb, yb) = PATTERN_BUTTON.find(b)!!.destructured
        val (xt, yt) = PATTERN_TARGET.find(t)!!.destructured
        Machine(
            xa = xa.toLong(), ya = ya.toLong(),
            xb = xb.toLong(), yb = yb.toLong(),
            xt = xt.toLong(), yt = yt.toLong(),
        )
    }
}

private fun part1(data: List<Machine>): Long {
    var total = 0L
    for ((xa, ya, xb, yb, xt, yt) in data) {
        val b = (ya * xt - xa * yt) / (ya * xb - xa * yb)
        val a = (xt - xb * b) / xa

        if (a <= 100 && b <= 100) {
            if (xa * a + xb * b == xt) {
                if (ya * a + yb * b == yt) {
                    total += 3 * a + b
                }
            }
        }
    }

    return total
}

private fun part2(data: List<Machine>): Long {
    var total = 0L
    for ((xa, ya, xb, yb, _xt, _yt) in data) {
        val xt = _xt + 10000000000000L
        val yt = _yt + 10000000000000L

        val b = (ya * xt - xa * yt) / (ya * xb - xa * yb)
        val a = (xt - xb * b) / xa

        if (xa * a + xb * b == xt) {
            if (ya * a + yb * b == yt) {
                total += 3 * a + b
            }
        }
    }

    return total
}
