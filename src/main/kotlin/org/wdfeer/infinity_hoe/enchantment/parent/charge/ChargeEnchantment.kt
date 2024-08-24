package org.wdfeer.infinity_hoe.enchantment.parent.charge

import net.minecraft.item.ItemStack
import net.minecraft.text.Style
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.event.listener.AppendTooltipListener
import org.wdfeer.infinity_hoe.extension.getEnchantmentLevel

abstract class ChargeEnchantment(rarity: Rarity) : HoeEnchantment(rarity), AppendTooltipListener {
    protected abstract fun getTooltipColor(): Formatting
    protected abstract fun getMaxCharge(level: Int): Int

    protected open fun getChargeDecrement(): Int = 1
    protected open fun chargeToString(charge: Int): String = charge.toString()

    private val nbtKey get() = getPath() + "_charge"
    protected fun getCharge(hoe: ItemStack): Int = hoe.nbt?.getInt(nbtKey) ?: 0
    protected fun setCharge(hoe: ItemStack, value: Int) { hoe.orCreateNbt.putInt(nbtKey, value) }

    final override fun appendTooltip(stack: ItemStack, tooltip: MutableList<Text>) {
        val charge = getCharge(stack)
        val maxCharge = getMaxCharge(stack.getEnchantmentLevel(this))

        tooltip.add(Text.translatable("tooltip.infinity_hoe.${getPath()}.charge", chargeToString(charge), chargeToString(maxCharge)).apply {
            style = if (charge >= getChargeDecrement()) Style.EMPTY.withColor(getTooltipColor()) else Style.EMPTY.withColor(0xafafaf)
        })
    }
}