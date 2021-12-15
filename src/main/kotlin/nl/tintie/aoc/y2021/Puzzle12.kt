package nl.tintie.aoc.y2021

import nl.tintie.aoc.AocPuzzle

object Puzzle12 : AocPuzzle(2021, 12) {
    data class Node(
        val name: String,
        var connectedNodes: List<Node> = listOf()
    ) {
        val isBig = name.uppercase() == name

        override fun toString(): String =
            "{name: $name, connectedNodes: ${connectedNodes.joinToString(", ") { it.name }}}"

        override fun hashCode(): Int {
            return name.hashCode()
        }
    }

    val caves = input.fold(listOf<Node>()) { nodes, line ->
        val (start, end) = line.split("-")
        val startNode = nodes.find { it.name == start } ?: Node(start)
        val endNode = nodes.find { it.name == end } ?: Node(end)
        val newNodes = nodes.toMutableList() - startNode - endNode
        newNodes +
                startNode.apply { connectedNodes = startNode.connectedNodes + endNode } +
                endNode.apply { connectedNodes = endNode.connectedNodes + startNode }
    }

    fun findAllRoutes(
        nodes: List<Node>,
        currentPath: List<Node>,
        canVisit: List<Node>.(Node) -> Boolean
    ): Set<List<Node>> {
        val currentNode = currentPath.lastOrNull()!!
        return currentNode.connectedNodes.mapNotNull { nextNode ->
            when {
                nextNode.name == "start" -> null
                nextNode.name == "end" -> setOf(currentPath + nextNode)
                currentPath.canVisit(nextNode) -> findAllRoutes(nodes, currentPath + nextNode, canVisit)
                else -> null
            }
        }.flatten().toSet()
    }


    override fun part1(): Any? {
        return findAllRoutes(caves, caves.filter { it.name == "start" }) { it.isBig || it !in this }.size
    }

    fun List<Node>.canVisitPt2(node: Node): Boolean = when {
        node.isBig || node !in this -> true
        else -> {
            groupBy { it }.entries.all { (k, v) ->
                k.isBig || v.size <= 1
            }
        }
    }

    override fun part2(): Any? {
        return findAllRoutes(caves, caves.filter { it.name == "start" }) { canVisitPt2(it) }.size
    }
}

fun main() {
    Puzzle12.runPart2()
}