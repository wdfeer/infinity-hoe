package org.wdfeer.infinity_hoe.util

import net.minecraft.util.math.BlockPos

fun BlockPos.getAdjacent(distance: Int = 1): List<BlockPos> {
    val range = (-distance..distance)
    return range
        .map { x -> range.map { y -> range.map { z -> range.map { this.add(x, y, z) } } } }
        .flatten().flatten().flatten()
}

fun BlockPos.getAdjacentHorizontally(distance: Int = 1): List<BlockPos> {
    return (-distance..distance)
        .map { x -> (-distance..distance).map { z -> this.add(x, 0, z) } }
        .flatten()
}