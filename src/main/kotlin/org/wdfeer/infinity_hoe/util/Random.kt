package org.wdfeer.infinity_hoe.util

import kotlin.random.Random

fun Random.roll(chance: Float): Boolean = this.nextFloat() < chance

fun Random.roll(divider: Int): Boolean = this.nextInt(divider) == 0