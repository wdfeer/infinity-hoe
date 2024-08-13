package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.entity.ExperienceOrbEntity
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.event.listener.HarvestListener
import org.wdfeer.infinity_hoe.util.getEnchantmentLevel
import org.wdfeer.infinity_hoe.util.randomRound
import kotlin.math.floor

class CropExperience : HoeEnchantment(Rarity.RARE), HarvestListener {
    override fun getPath(): String = "crop_experience"

    override val maxLvl: Int
        get() = 5

    override fun getPowerRange(level: Int): IntRange = 12 + level * 4..20 + level * 4

    override fun onCropBroken(
        world: ServerWorld,
        player: ServerPlayerEntity,
        hoe: ItemStack,
        pos: BlockPos,
        mature: Boolean
    ) {
        if (mature) {
            val amount = (XP_PER_LEVEL * hoe.getEnchantmentLevel(this)).randomRound()
            if (amount > 0)
                ExperienceOrbEntity.spawn(
                    world,
                    pos.toCenterPos().let { Vec3d(it.x, floor(it.y), it.z) },
                    amount
                )
        }
    }

    companion object {
        const val XP_PER_LEVEL: Float = 0.5f
    }
}