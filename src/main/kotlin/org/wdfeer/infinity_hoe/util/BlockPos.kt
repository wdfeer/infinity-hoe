package org.wdfeer.infinity_hoe.util

import net.minecraft.util.math.BlockPos

fun BlockPos.getAdjacent(range: Int = 1): List<BlockPos> {
    val list: MutableList<BlockPos> = mutableListOf()

    (-range..range).forEach {
        x -> (-range..range)
            .forEach { z -> list.add(this.add(x, 0, z)) }
    }

    return list
}