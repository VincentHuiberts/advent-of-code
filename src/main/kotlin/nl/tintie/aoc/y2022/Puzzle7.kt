package nl.tintie.aoc.y2022

import nl.tintie.aoc.AocPuzzle

object Puzzle7 : AocPuzzle(2022, 7) {
//    override val input = """${'$'} cd /
//${'$'} ls
//dir a
//14848514 b.txt
//8504156 c.dat
//dir d
//${'$'} cd a
//${'$'} ls
//dir e
//29116 f
//2557 g
//62596 h.lst
//${'$'} cd e
//${'$'} ls
//584 i
//${'$'} cd ..
//${'$'} cd ..
//${'$'} cd d
//${'$'} ls
//4060174 j
//8033020 d.log
//5626152 d.ext
//7214296 k""".lines()

    data class PuzzleFile(val name: String, val size: Int)
    class Folder(
        val name: String,
        val parentFolder: Folder? = null,
        var subFolders: List<Folder> = emptyList(),
        var files: List<PuzzleFile> = emptyList()
    ) {
        val totalSize: Long by lazy { subFolders.sumOf { it.totalSize } + files.sumOf { it.size } }

        override fun toString(): String = "Folder $name, size $totalSize"
    }

    fun processCommands(lines: List<String>): Folder {
        val rootFolder = Folder("/")
        var currentFolder = rootFolder
        lines.forEach { line ->
            when {
                line.endsWith(" ..") -> {
                    currentFolder = currentFolder.parentFolder!!
                }
                line.startsWith("\$ cd /") -> {

                }
                line.startsWith("\$ cd ") -> {
                    val subDir = line.split(" ").last()
                    currentFolder = currentFolder.subFolders.find { it.name == subDir }!!
                }
                line == "\$ ls" -> {

                }
                !line.startsWith("dir") -> {
                    val (size, name) = line.split(" ")
                    val file = PuzzleFile(name, size.toInt())
                    currentFolder.files = currentFolder.files + file
                }
                line.startsWith("dir") -> {
                    currentFolder.subFolders = currentFolder.subFolders + Folder(name = line.split(" ")[1], parentFolder = currentFolder)
                }
            }
        }
        return rootFolder
    }

    fun Folder.filter(pred: (Folder) -> Boolean): List<Folder> {
        return subFolders.filter(pred) + subFolders.flatMap { it.filter(pred) }
    }

    override fun part1(): Any? {
        val dirs = processCommands(input)
        return dirs.filter { it.totalSize < 100_000 }.sumOf { it.totalSize }
    }

    override fun part2(): Any? {
        val dirs = processCommands(input)
        val requiredSpace = 30000000
        val driveSize = 70000000
        val unusedSpace = driveSize - dirs.totalSize
        val leftToRemove = requiredSpace - unusedSpace
        return dirs.filter { it.totalSize > leftToRemove }.minBy { it.totalSize }.totalSize
    }
}

fun main() {
    Puzzle7.runPart2()
}