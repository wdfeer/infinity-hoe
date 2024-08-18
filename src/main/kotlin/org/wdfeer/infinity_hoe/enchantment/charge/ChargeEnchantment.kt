package org.wdfeer.infinity_hoe.enchantment.charge

import net.minecraft.enchantment.Enchantment
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.Style
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.event.listener.AirUseListener
import org.wdfeer.infinity_hoe.event.listener.AppendTooltipListener
import org.wdfeer.infinity_hoe.event.listener.HarvestListener
import org.wdfeer.infinity_hoe.extension.getEnchantmentLevel

abstract class ChargeEnchantment(rarity: Rarity) : HoeEnchantment(rarity), HarvestListener, AirUseListener, AppendTooltipListener {
    protected abstract fun useCharge(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack): Boolean
    protected abstract fun getTooltipFormatting(): Formatting

    private val nbtKey get() =  getPath() + "_charge"
    protected open fun getMaxCharge(level: Int) = 50 * level

    final override fun onCropBroken(
        world: ServerWorld,
        player: ServerPlayerEntity,
        hoe: ItemStack,
        pos: BlockPos,
        mature: Boolean
    ) {
        if (!mature) return

        val nbt = hoe.orCreateNbt
        val charge = nbt.getInt(nbtKey)
        val level = hoe.getEnchantmentLevel(this)

        if (nbt.contains(nbtKey) && charge < getMaxCharge(level)) {
            nbt.putInt(nbtKey, charge + 1)
        } else
            nbt.putInt(nbtKey, 1)
    }

    final override fun onUseInAir(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack) {
        val nbt = hoe.nbt ?: return
        val charge = nbt.getInt(nbtKey)
        if (!nbt.contains(nbtKey) || charge <= 0) return

        if (useCharge(world, player, hoe)) nbt.putInt(nbtKey, charge - 1)
    }

    final override fun appendTooltip(stack: ItemStack, tooltip: MutableList<Text>) {
        val nbt = stack.nbt
        val charge = if (nbt?.contains(nbtKey) == true) nbt.getInt(nbtKey) else 0
        val maxCharge = getMaxCharge(stack.getEnchantmentLevel(this))

        tooltip.add(Text.translatable("tooltip.infinity_hoe.${getPath()}.charge", charge, maxCharge).apply {
            style = Style.EMPTY.withColor(if (charge > 0) getTooltipFormatting() else Formatting.GRAY)
        })
    }

    override fun canAccept(other: Enchantment?): Boolean = other !is ChargeEnchantment
}