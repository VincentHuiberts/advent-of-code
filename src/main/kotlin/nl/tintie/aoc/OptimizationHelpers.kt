package nl.tintie.aoc

class MemoizedFunc<I, O>(val func: MemoizedFunc<I, O>.(I) -> O) {
    private val memoizedCalls = mutableMapOf<I, O>()
    fun recurse(input: I) = invoke(input)
    operator fun invoke(input: I): O = memoizedCalls.getOrPut(input) { func(input) }
}