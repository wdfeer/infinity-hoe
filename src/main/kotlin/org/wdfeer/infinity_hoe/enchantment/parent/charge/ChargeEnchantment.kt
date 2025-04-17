package org.wdfeer.infinity_hoe.enchantment.parent.charge

import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.text.Style
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.event.listener.AppendTooltipListener
import org.wdfeer.infinity_hoe.extension.getEnchantmentLevel
import org.wdfeer.infinity_hoe.sound.SoundLoader

abstract class ChargeEnchantment(rarity: Rarity) : HoeEnchantment(rarity), AppendTooltipListener {
    protected open fun getTooltipStyle(): Style = Style.EMPTY.withColor(getTooltipColor())
    protected open fun getTooltipColor(): Formatting =
        throw NotImplementedError("Either override getTooltipColor or implement custom getTooltipStyle!")

    abstract fun getMaxCharge(level: Int): Int

    protected open fun getChargeDecrement(): Int = 1
    protected open fun chargeToString(charge: Int): String = charge.toString()

    private val nbtKey get() = getPath() + "_charge"
    fun getCharge(hoe: ItemStack): Int = hoe.nbt?.getInt(nbtKey) ?: 0
    fun setCharge(hoe: ItemStack, value: Int) = hoe.orCreateNbt.putInt(nbtKey, value)

    fun setChargeWithSound(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack, value: Int) {
        val sound: SoundEvent? = when {
            value == getMaxCharge(hoe.getEnchantmentLevel(this)) -> SoundLoader.chargedSoundEvent
            value < getChargeDecrement() && getCharge(hoe) >= getChargeDecrement() -> SoundLoader.dischargedSoundEvent
            else -> null
        }
        setCharge(hoe, value)

        if (sound != null)
            world.playSoundFromEntity(null, player, sound, SoundCategory.PLAYERS, 1f, 1f)
    }

    final override fun appendTooltip(stack: ItemStack, tooltip: MutableList<Text>) {
        tooltip.add(
            Text.translatable(
                "tooltip.infinity_hoe.${getPath()}.charge", *getTooltipArgs(stack).toTypedArray()
            ).apply {
                style = if (getCharge(stack) >= getChargeDecrement()) getTooltipStyle() else Style.EMPTY.withColor(
                    0xafafaf
                )
            })
    }

    protected open fun getTooltipArgs(hoe: ItemStack): List<String> = listOf(
        chargeToString(getCharge(hoe)), chargeToString(getMaxCharge(hoe.getEnchantmentLevel(this)))
    )
}