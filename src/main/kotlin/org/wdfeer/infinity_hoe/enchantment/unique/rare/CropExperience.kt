package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.entity.ExperienceOrbEntity
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.util.getEnchantmentLevel
import kotlin.math.floor

class CropExperience : HoeEnchantment(Rarity.RARE) {
    override fun getPath(): String = "crop_experience"

    override fun getMaxLevel(): Int = 5

    override fun getMinPower(level: Int): Int = 15 + level * 3

    override fun getMaxPower(level: Int): Int = 21 + level * 3

    override fun onCropBroken(
        world: ServerWorld,
        player: ServerPlayerEntity,
        hoe: ItemStack,
        pos: BlockPos,
        mature: Boolean
    ) {
        if (mature) ExperienceOrbEntity.spawn(
            world,
            pos.toCenterPos().let { Vec3d(it.x, floor(it.y), it.z) },
            XP_PER_LEVEL * hoe.getEnchantmentLevel(this)
        )
    }

    companion object {
        const val XP_PER_LEVEL: Int = 1
    }
}