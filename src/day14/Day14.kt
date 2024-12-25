package day14

import java.io.File

fun main() {
    val data = parse("src/day14/Day14.txt")

    println("🎄 Day 14 🎄")

    println()

    println("[Part 1]")
    println("Answer: ${part1(data)}")

    println()

    println("[Part 2]")
    println("Answer: ${part2(data)}")
}

private data class Robot(
    val px: Int,
    val py: Int,
    val vx: Int,
    val vy: Int,
)

private fun parse(path: String): List<Robot> =
    File(path)
        .readLines()
        .map { it.toRobot() }

private fun String.toRobot(): Robot {
    val (px, py, vx, vy) = this.split(" ")
        .flatMap { it.substring(2).split(",") }
        .map { it.toInt() }

    return Robot(px, py, vx, vy)
}

private fun part1(data: List<Robot>): Int {
    var w = 101
    var h = 103

    val robots = data.toMutableList()
    repeat(100) {
        for ((i, robot) in robots.withIndex()) {
            var px = robot.px + robot.vx
            var py = robot.py + robot.vy

            if (px < 0) px += w
            if (py < 0) py += h

            if (px >= w) px -= w
            if (py >= h) py -= h

            robots[i] = robot.copy(
                px = px,
                py = py,
            )
        }
    }

    val qx = w / 2
    val qy = h / 2

    var q1 = 0
    var q2 = 0
    var q3 = 0
    var q4 = 0

    for ((px, py) in robots) {
        when {
            px < qx && py < qy -> q1 += 1
            px > qx && py < qy -> q2 += 1
            px < qx && py > qy -> q3 += 1
            px > qx && py > qy -> q4 += 1
        }
    }

    return q1 * q2 * q3 * q4
}

private fun part2(data: List<Robot>): Int {
    var w = 101
    var h = 103

    var seconds = 0
    val offsets = listOf(
        Pair(+0, -1),
        Pair(+0, +1),
        Pair(-1, +0),
        Pair(+1, +0),
        Pair(-1, -1),
        Pair(-1, +1),
        Pair(+1, -1),
        Pair(+1, +1),
    )

    val robots = data.toMutableList()
    while (true) {
        for ((i, robot) in robots.withIndex()) {
            var px = robot.px + robot.vx
            var py = robot.py + robot.vy

            if (px < 0) px += w
            if (py < 0) py += h

            if (px >= w) px -= w
            if (py >= h) py -= h

            robots[i] = robot.copy(
                px = px,
                py = py,
            )
        }

        seconds += 1

        var counter = 0
        for ((x, y) in robots) {
            counter += offsets.count { (ox, oy) ->
                robots.count { it.px == x + ox && it.py == y + oy } > 0
            }
        }

        // Terminate the program once you see the Christmas tree :)
        if (counter >= 470) {
            println("Seconds: $seconds")
            for (y in 0 until h) {
                for (x in 0 until w) {
                    val count = robots.count { it.px == x && it.py == y }
                    if (count != 0) {
                        print(count)
                    } else {
                        print('.')
                    }
                }
                println()
            }

            println()
            println()

            Thread.sleep(1000)
        }
    }

    error("Please, terminate this function manually.")
}
