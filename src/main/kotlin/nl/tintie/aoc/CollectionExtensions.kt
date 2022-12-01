package nl.tintie.aoc

fun <T> Sequence<T>.combinations(n: Int): Sequence<List<T>> {
    return if (n <= 1) {
        map { listOf(it) }
    } else {
        flatMapIndexed { i, num1 -> drop(i + 1).combinations(n - 1).map { sub -> listOf(num1) + sub } }
    }
}

fun <T> allArrangements(options: List<T>, size: Int = options.size, reuseElements: Boolean = false): List<List<T>> {
    return if (size > 1) {
        options.flatMap { opt ->
            allArrangements(if (reuseElements) options else options - opt, size - 1, reuseElements).map {
                listOf(opt) + it
            }
        }
    } else {
        options.map { listOf(it) }
    }
}

fun <T> List<String>.pivot(transform: (Char) -> T): List<List<T>> = fold(listOf<List<Char>>()) { acc, cur ->
    cur.toCharArray().mapIndexed { i, c ->
        acc.getOrElse(i) { listOf() } + c
    }
}.map { row -> row.map(transform) }

/**
 * Count the number of occurrences for each element in the [List]. Sorted by least occurring to most.
 */
fun <T> List<T>.countOccurrences() = groupBy { it }
    .mapValues { it.value.size }
    .entries.sortedBy { (_, size) -> size }
    .map { it.toPair() }

/**
 * Splits a list of type [T] into multiple sublists of type [T] based on [isDelimiter].
 * The delimiter is not included in the result.
 */
fun <T> Collection<T>.splitCollection(isDelimiter: (T) -> Boolean): List<List<T>> =
    fold(listOf(listOf())) { acc, element ->
        if (isDelimiter(element)) {
            acc.plusElement(listOf())
        } else {
            acc.dropLast(1).plusElement(acc.last() + element)
        }
    }

/**
 * Splits a [Collection] into multiple [List]s of type [T] returned as a [Sequence] based on [isDelimiter].
 */
fun <T> Collection<T>.splitToSequence(isDelimiter: (T) -> Boolean): Sequence<List<T>> = sequence {
    fold(mutableListOf<T>()) { acc, e ->
        if(isDelimiter(e)) {
            yield(acc)
            mutableListOf()
        } else {
            acc.apply { add(e) }
        }
    }
}

fun <T> List<T>.getRepeating(i: Int) = get((i + size) % size)