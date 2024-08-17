package org.wdfeer.infinity_hoe.util

object TickDurationHelper {
    fun minutesToTicks(minutes: Int): Int = secondsToTicks(minutes * 60)
    fun secondsToTicks(seconds: Int): Int = seconds * 20
}