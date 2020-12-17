package nl.tintie.aoc.y2020

import nl.tintie.aoc.AocPuzzle

object Puzzle17 : AocPuzzle(2020, 17) {
    class Space3d(
        val default: String
    ) {
        val space = mutableMapOf<Int, MutableMap<Int, MutableMap<Int, String>>>()

        fun Map<Int, Map<Int, Map<Int, String>>>.getAtOrDefault(point: Triple<Int, Int, Int>): String {
            val (x, y, z) = point
            return getOrDefault(z, mutableMapOf())
                .getOrDefault(y, mutableMapOf())
                .getOrDefault(x, default)
        }

        fun setAt(point: Triple<Int, Int, Int>, value: String) {
            val (x, y, z) = point
            space.getOrPut(z) {
                mutableMapOf()
            }.getOrPut(y) {
                mutableMapOf()
            }[x] = value
        }

        fun cycle() {
            val startingState = space.mapValues { (_, xp) -> xp.mapValues { (_, yp) -> yp.toMap() } }
            val minZ = space.keys.minOrNull()!!
            val maxZ = space.keys.maxOrNull()!!
            val minY = space.values.minOf { it.keys.minOrNull()!! }
            val maxY = space.values.maxOf { it.keys.maxOrNull()!! }
            val minX = space.values.minOf { it.values.minOf { it.keys.minOrNull()!! } }
            val maxX = space.values.maxOf { it.values.maxOf { it.keys.maxOrNull()!! } }
            (minX - 1..maxX + 1).forEach { x ->
                (minY - 1..maxY + 1).forEach { y ->
                    (minZ - 1..maxZ + 1).forEach { z ->
                        val point = Triple(x, y, z)
                        val currentState = startingState.getAtOrDefault(point)
                        val surrounding = startingState.getSurroundingValues(point)
                        val newState = if (currentState == "#" && surrounding.count { it == "#" } in 2..3) {
                            "#"
                        } else if (currentState == "." && surrounding.count { it == "#" } == 3) {
                            "#"
                        } else "."
                        setAt(point, newState)
                    }
                }
            }
        }

        fun Map<Int, Map<Int, Map<Int, String>>>.getSurroundingValues(point: Triple<Int, Int, Int>): List<String> {
            val (cx, cy, cz) = point
            return (cx - 1..cx + 1).flatMap { x ->
                (cy - 1..cy + 1).flatMap { y ->
                    (cz - 1..cz + 1).mapNotNull { z ->
                        val currentPoint = Triple(x, y, z)
                        if (currentPoint != point) getAtOrDefault(currentPoint) else null
                    }
                }
            }
        }

        fun countElement(element: String) =
            space.values.sumBy { x -> x.values.sumBy { y -> y.values.count { z -> z == element } } }
    }

    override fun part1(): Any? {
        val space = Space3d(".")
        input.forEachIndexed { y, line ->
            line.chunked(1).forEachIndexed { x, v ->
                space.setAt(Triple(y, x, 0), v)
            }
        }
        repeat(6) {
            space.cycle()
        }

        return space.countElement("#")
    }

    class Space4d(
        val default: String
    ) {
        val space = mutableMapOf<Int, MutableMap<Int, MutableMap<Int, MutableMap<Int, String>>>>()

        fun Map<Int, Map<Int, Map<Int, Map<Int, String>>>>.getAtOrDefault(x: Int, y: Int, z: Int, w: Int): String {
            return getOrDefault(w, mutableMapOf())
                .getOrDefault(z, mutableMapOf())
                .getOrDefault(y, mutableMapOf())
                .getOrDefault(x, default)
        }

        fun setAt(x: Int, y: Int, z: Int, w: Int, value: String) {
            space.getOrPut(w) {
                mutableMapOf()
            }.getOrPut(z) {
                mutableMapOf()
            }.getOrPut(y) {
                mutableMapOf()
            }[x] = value
        }

        fun cycle() {
            val startingState =
                space.mapValues { (_, xp) -> xp.mapValues { (_, yp) -> yp.mapValues { it.value.toMap() } } }
            val minW = space.keys.minOrNull()!!
            val maxW = space.keys.maxOrNull()!!
            val minZ = space.values.minOf { it.keys.minOrNull()!! }
            val maxZ = space.values.maxOf { it.keys.maxOrNull()!! }
            val minY = space.values.minOf { it.values.minOf { it.keys.minOrNull()!! } }
            val maxY = space.values.maxOf { it.values.maxOf { it.keys.maxOrNull()!! } }
            val minX = space.values.minOf { it.values.minOf { it.values.minOf { it.keys.minOrNull()!! } } }
            val maxX = space.values.maxOf { it.values.maxOf { it.values.maxOf { it.keys.maxOrNull()!! } } }

            (minX - 1..maxX + 1).forEach { x ->
                (minY - 1..maxY + 1).forEach { y ->
                    (minZ - 1..maxZ + 1).forEach { z ->
                        (minW - 1..maxW + 1).forEach { w ->
                            val currentState = startingState.getAtOrDefault(x, y, z, w)
                            val surrounding = startingState.getSurroundingValues(x, y, z, w)
                            val newState = if (currentState == "#" && surrounding.count { it == "#" } in 2..3) {
                                "#"
                            } else if (currentState == "." && surrounding.count { it == "#" } == 3) {
                                "#"
                            } else "."

                            setAt(x, y, z, w, newState)
                        }
                    }
                }
            }
        }

        fun Map<Int, Map<Int, Map<Int, Map<Int, String>>>>.getSurroundingValues(
            cx: Int,
            cy: Int,
            cz: Int,
            cw: Int
        ): List<String> {
            return (cx - 1..cx + 1).flatMap { x ->
                (cy - 1..cy + 1).flatMap { y ->
                    (cz - 1..cz + 1).flatMap { z ->
                        (cw - 1..cw + 1).mapNotNull { w ->
                            if (x == cx && y == cy && z == cz && w == cw) null else getAtOrDefault(x, y, z, w)
                        }
                    }
                }
            }
        }

        fun countElement(element: String) =
            space.values.sumBy { x -> x.values.sumBy { y -> y.values.sumBy { z -> z.values.count { it == element } } } }
    }

    override fun part2(): Any? {
        val space = Space4d(".")
        input.forEachIndexed { y, line ->
            line.chunked(1).forEachIndexed { x, v ->
                space.setAt(y, x, 0, 0, v)
            }
        }
        repeat(6) {
            space.cycle()
        }

        return space.countElement("#")
    }
}

fun main() {
    Puzzle17.runPart2()
}