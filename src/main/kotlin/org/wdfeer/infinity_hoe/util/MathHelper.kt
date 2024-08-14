package org.wdfeer.infinity_hoe.util

object MathHelper {
    private fun triangleCurve(x: Float, xHighest: Float, xLimit: Float): Float = when {
        x < 0 || x > xLimit -> 0f // Out of bounds
        x <= xHighest -> x / xHighest // Linearly increasing to the peak
        else -> (xLimit - x) / (xLimit - xHighest) // Linearly decreasing from the peak
    }

    fun triangleCurve(x: Int, xHighest: Int, xLimit: Int): Float = triangleCurve(x.toFloat(), xHighest.toFloat(), xLimit.toFloat())
}