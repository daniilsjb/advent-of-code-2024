package day05

import java.io.File

fun main() {
    val data = parse("src/day05/Day05.txt")

    println("🎄 Day 05 🎄")

    println()

    println("[Part 1]")
    println("Answer: ${part1(data)}")

    println()

    println("[Part 2]")
    println("Answer: ${part2(data)}")
}

data class Rule(
    val predecessor: Int,
    val successor: Int,
)

data class Manual(
    val rules: Set<Rule>,
    val updates: List<List<Int>>,
)

private fun parse(path: String): Manual {
    val (rulesText, updatesText) = File(path)
        .readText()
        .trim()
        .split("""\s*\n\s*\n""".toRegex())

    val rules = rulesText.lines().map {
        it.split("|")
            .map { it.toInt() }
            .let { (predecessor, successor) -> Rule(predecessor, successor) }
    }.toSet()

    val updates = updatesText.lines().map {
        it.split(",").map { it.toInt() }
    }

    return Manual(rules, updates)
}

private fun ordered(update: List<Int>, rules: Set<Rule>): Boolean {
    val indices = update.withIndex()
        .associate { (index, value) -> value to index }

    return rules.all { (predecessor, successor) ->
        val predecessorIndex = indices[predecessor] ?: return@all true
        val successorIndex = indices[successor] ?: return@all true
        predecessorIndex <= successorIndex
    }
}

private fun part1(data: Manual): Int =
    data.updates
        .filter { ordered(it, data.rules) }
        .sumOf { it[it.size / 2] }

private fun part2(data: Manual): Int {
    var total = 0

    for (update in data.updates) {
        if (ordered(update, data.rules)) continue
        val numbers = update.toMutableList()
        for (i in numbers.indices) {
            var j = i + 1
            while (j <= numbers.lastIndex) {
                val lhs = numbers[i]
                val rhs = numbers[j]

                if (Rule(rhs, lhs) in data.rules) {
                    numbers[j] = numbers[i]
                        .also { numbers[i] = numbers[j] }
                } else {
                    j += 1
                }
            }
        }

        total += numbers[numbers.size / 2]
    }

    return total
}
