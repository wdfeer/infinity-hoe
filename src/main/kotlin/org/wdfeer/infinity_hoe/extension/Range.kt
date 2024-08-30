package org.wdfeer.infinity_hoe.extension

fun IntRange.letBounds(modifier: (Int) -> Int): IntRange = modifier(first)..modifier(last)
fun IntRange.incrementBounds(amount: Int): IntRange = letBounds { it + amount }