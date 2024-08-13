package org.wdfeer.infinity_hoe.enchantment.unique.uncommon

import net.minecraft.block.BlockState
import net.minecraft.enchantment.Enchantments
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.enchantment.double_harvest.DoubleHarvest
import org.wdfeer.infinity_hoe.event.listener.HarvestListener
import org.wdfeer.infinity_hoe.util.getEnchantmentLevel
import org.wdfeer.infinity_hoe.enchantment.status.getStatusPotency
import org.wdfeer.infinity_hoe.util.roll
import kotlin.random.Random

class MinerHarvest : HoeEnchantment(Rarity.UNCOMMON), HarvestListener {
    override fun getPowerRange(level: Int): IntRange = 10..50

    override fun getPath(): String = "miner_harvest"

    override fun onCropBroken(
        world: ServerWorld,
        player: ServerPlayerEntity,
        hoe: ItemStack,
        pos: BlockPos,
        state: BlockState,
        mature: Boolean
    ) {
        if (!mature) return

        val chance: Float = 0.04f + 0.02f * getEffectCount(player, hoe)

        if (Random.roll(chance)) DoubleHarvest.drop(world, state, pos)
    }

    private fun getEffectCount(player: ServerPlayerEntity, hoe: ItemStack): Int = listOf(
        hoe.getEnchantmentLevel(Enchantments.EFFICIENCY),
        hoe.getEnchantmentLevel(Enchantments.FORTUNE),
        player.getStatusPotency(StatusEffects.HASTE),
        player.getStatusPotency(StatusEffects.LUCK)
    ).let { it.sum() + it.size }
}