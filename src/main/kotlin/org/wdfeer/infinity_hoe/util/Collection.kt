package org.wdfeer.infinity_hoe.util

fun <T> Collection<T>.randoms(count: Int): List<T> {
    return this.shuffled().take(count)
}
