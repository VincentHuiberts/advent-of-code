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

fun <T> combinations(options: List<T>, size: Int): List<List<T>> {
    return if(size > 1) {
        options.flatMap { opt -> combinations(options, size - 1).map { listOf(opt) + it } }
    } else {
        options.map { listOf(it) }
    }
}

/**
 * Splits a list of type `T` into multiple sublists of type `T` based on `isDelimiter`.
 * The delimiter is not included in the result.
 */
fun <T> List<T>.splitCollection(isDelimiter: (T) -> Boolean): List<List<T>> =
    fold(listOf(listOf())) { acc, element ->
        if(isDelimiter(element)) {
            acc.plusElement(listOf())
        } else {
            acc.dropLast(1).plusElement (acc.last() + element)
        }
    }

fun <T> List<T>.getRepeating(i : Int) = get(i % size)