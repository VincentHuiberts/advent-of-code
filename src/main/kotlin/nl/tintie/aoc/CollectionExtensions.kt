package nl.tintie.aoc

/**
 * Creates a sequence of lists with size `n` with all possible combinations.
 */
fun <T> Sequence<T>.combinations(n: Int): Sequence<List<T>> {
    return if(n <= 1) {
        map { listOf(it) }
    } else {
        flatMapIndexed { i, num1 -> drop(i + 1).combinations(n - 1).map { sub -> listOf(num1) + sub } }
    }
}

/**
 * Creates a sequence of lists with size `n` with all possible combinations. The filters will be applied
 * to the corresponding entry of that sublist.
 */
fun <T> Sequence<T>.combinationsFiltered(n: Int, filters: List<(T) -> Boolean>): Sequence<List<T>> {
    val currentCriteria = filters.getOrNull(0) ?: { true }
    val currentFiltered = filter { currentCriteria(it) }
    return if(n <= 1) {
        currentFiltered.map { listOf(it) }
    } else {
        currentFiltered.flatMapIndexed { i, num1 ->
            drop(i + 1).combinationsFiltered(n - 1, filters.drop(1)).map { sub -> listOf(num1) + sub }
        }
    }
}