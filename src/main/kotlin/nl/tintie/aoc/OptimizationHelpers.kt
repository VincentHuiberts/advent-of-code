package nl.tintie.aoc

/**
 * Create an object with given [func] and apply memoization to it.
 */
class MemoizedFunc<I, O>(val func: MemoizedFunc<I, O>.(I) -> O) {
    private val memoizedCalls = mutableMapOf<I, O>()

    /**
     * Separate function to call the provided function for readability.
     */
    fun recurse(input: I) = invoke(input)
    operator fun invoke(input: I): O = memoizedCalls.getOrPut(input) { func(input) }
}