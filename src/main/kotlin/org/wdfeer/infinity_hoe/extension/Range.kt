package org.wdfeer.infinity_hoe.extension

fun IntRange.letBounds(modifier: (Int) -> Int): IntRange = modifier(first)..modifier(last)