package org.wdfeer.infinity_hoe.enchantment

import net.minecraft.block.BlockState
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.HoeItem
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos

abstract class HoeEnchantment(rarity: Rarity) : Enchantment(rarity, EnchantmentTarget.DIGGER, arrayOf(EquipmentSlot.MAINHAND)), Identifiable {
    open val maxLevel: Int get() = 1
    abstract fun getPowerRange(level: Int): IntRange


    final override fun getMaxLevel(): Int = maxLevel
    final override fun getMinPower(level: Int): Int = getPowerRange(level).first
    final override fun getMaxPower(level: Int): Int = getPowerRange(level).last


    override fun isAcceptableItem(stack: ItemStack?): Boolean {
        return stack?.item is HoeItem
    }

    protected open fun onCropBroken(
        world: ServerWorld,
        player: ServerPlayerEntity,
        hoe: ItemStack,
        pos: BlockPos,
        mature: Boolean
    ) = Unit

    open fun onCropBroken(
        world: ServerWorld,
        player: ServerPlayerEntity,
        hoe: ItemStack,
        pos: BlockPos,
        state: BlockState,
        mature: Boolean
    ) = onCropBroken(world, player, hoe, pos, mature)

    open fun onTill(
        world: ServerWorld,
        player: ServerPlayerEntity,
        hoe: ItemStack,
        pos: BlockPos
    ) = Unit
}