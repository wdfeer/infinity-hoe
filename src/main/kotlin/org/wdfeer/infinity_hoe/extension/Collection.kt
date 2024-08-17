package org.wdfeer.infinity_hoe.extension

fun <T> Collection<T>.randoms(count: Int): List<T> {
    return this.shuffled().take(count)
}
