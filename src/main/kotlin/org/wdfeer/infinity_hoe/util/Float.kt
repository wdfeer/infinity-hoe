package org.wdfeer.infinity_hoe.util

import kotlin.random.Random

fun Float.randomRound(): Int = this.toInt() + if (Random.roll(this % 1)) 1 else 0