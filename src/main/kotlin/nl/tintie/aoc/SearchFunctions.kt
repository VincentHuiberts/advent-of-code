package nl.tintie.aoc

fun binSearch(initLower: Long, initUpper: Long, matchFun : (Long) -> Long): Long {
    var lower = initLower
    var upper = initUpper
    while (true) {
        val current = (upper - lower) / 2 + lower
        val match = matchFun(current)
        when {
            match == 0L -> return current
            match < 0 -> upper = current
            else -> lower = current
        }
        println(current)
    }
}