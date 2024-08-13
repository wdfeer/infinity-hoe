package org.wdfeer.infinity_hoe.util

object TickDurationHelper {
    fun minutesToTicks(minutes: Int): Int = secondsToTicks(minutes * 60)
    fun secondsToTicks(seconds: Int): Int = seconds * 20

    fun ticksToMinutes(ticks: Int): Float = ticksToSeconds(ticks) / 60f
    fun ticksToSeconds(ticks: Int): Float = ticks / 20f
}