package nl.tintie.aoc.y2022

import nl.tintie.aoc.AocPuzzle

object Puzzle7 : AocPuzzle(2022, 7) {
    private data class PuzzleFile(val name: String, val size: Int)
    private class Folder(
        val name: String,
        val parentFolder: Folder? = null,
        val subFolders: MutableList<Folder> = mutableListOf(),
        var files: MutableList<PuzzleFile> = mutableListOf()
    ) {
        val totalSize: Long by lazy { subFolders.sumOf { it.totalSize } + files.sumOf { it.size } }

        override fun toString(): String = "Folder $name, size $totalSize"
    }

    private fun processCommands(lines: List<String>): Folder {
        val rootFolder = Folder("/")
        var currentFolder = rootFolder
        lines.forEach { line ->
            when {
                line.startsWith("\$ cd /") -> Unit
                line == "\$ ls" -> Unit

                line.endsWith(" ..") -> {
                    currentFolder = currentFolder.parentFolder!!
                }
                line.startsWith("\$ cd ") -> {
                    val subDir = line.split(" ").last()
                    currentFolder = currentFolder.subFolders.find { it.name == subDir }!!
                }
                line.startsWith("dir") -> {
                    currentFolder.subFolders += Folder(name = line.split(" ")[1], parentFolder = currentFolder)
                }
                else -> {
                    val (size, name) = line.split(" ")
                    val file = PuzzleFile(name, size.toInt())
                    currentFolder.files += file
                }
            }
        }
        return rootFolder
    }

    private fun Folder.filter(pred: (Folder) -> Boolean): List<Folder> {
        return subFolders.filter(pred) + subFolders.flatMap { it.filter(pred) }
    }

    override fun part1(): Any? {
        val dirs = processCommands(input)
        return dirs.filter { it.totalSize < 100_000 }.sumOf { it.totalSize }
    }

    override fun part2(): Any? {
        val dirs = processCommands(input)
        val requiredSpace = 30_000_000
        val driveSize = 70_000_000
        val leftToRemove = requiredSpace - (driveSize - dirs.totalSize)
        return dirs.filter { it.totalSize > leftToRemove }.minBy { it.totalSize }.totalSize
    }
}

fun main() {
    Puzzle7.runBoth()
}