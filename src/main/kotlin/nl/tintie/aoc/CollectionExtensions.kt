package nl.tintie.aoc

/**
 * Creates a sequence of lists with size `n` with all possible combinations.
 */
fun <T> Sequence<T>.combinations(n: Int): Sequence<List<T>> {
    return if (n <= 1) {
        map { listOf(it) }
    } else {
        flatMapIndexed { i, num1 -> drop(i + 1).combinations(n - 1).map { sub -> listOf(num1) + sub } }
    }
}
