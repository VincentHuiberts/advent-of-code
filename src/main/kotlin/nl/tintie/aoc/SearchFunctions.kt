package nl.tintie.aoc

fun binSearch(initLower: Long, initUpper: Long, matchFun : (Long) -> Long): Long {
    var lower = initLower
    var upper = initUpper
    var iterations = 0L
    while (true) {
        iterations += 1
        val current = (upper - lower) / 2 + lower
        val match = matchFun(current)
        when {
            match == 0L -> return current
            iterations == initUpper - initLower -> error("Ran $iterations iterations, never found 0")
            match < 0 -> upper = current
            else -> lower = current
        }
    }
}