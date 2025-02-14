package org.wdfeer.infinity_hoe.enchantment.unique.very_rare

import net.minecraft.item.ItemStack
import net.minecraft.item.ToolItem
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Formatting
import org.wdfeer.infinity_hoe.enchantment.parent.charge.UsableHarvestChargeEnchantment
import org.wdfeer.infinity_hoe.enchantment.unique.rare.AnimalBlessing
import org.wdfeer.infinity_hoe.enchantment.unique.rare.CursedForge.toolUpgrades
import org.wdfeer.infinity_hoe.enchantment.unique.uncommon.GrowthAcceleration
import org.wdfeer.infinity_hoe.extension.enchantmentMap

object BlessedForge : UsableHarvestChargeEnchantment(Rarity.VERY_RARE) {
    override fun useCharge(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack): Boolean {
        val oldStack = player.handItems.firstOrNull {
            it != hoe && toolUpgrades.containsKey(it.item)
        }

        return if (oldStack != null) {
            val oldItem = oldStack.item as ToolItem

            val newItem = toolUpgrades[oldItem]
            val newStack = ItemStack(newItem)

            val oldEnchants = oldStack.enchantmentMap
            for (e in oldEnchants) newStack.addEnchantment(e.key, e.value)
            newStack.damage = oldStack.damage

            val blessing = listOf(
                GrowthAcceleration,
                AnimalBlessing,
                MiracleBlessing
            ).filter { !oldEnchants.containsKey(it) }.randomOrNull() ?: return false
            newStack.addEnchantment(blessing, 3)

            oldStack.count = 0
            player.inventory.insertStack(newStack)

            true
        } else false
    }

    override fun getMaxCharge(level: Int): Int = getChargeDecrement()
    override fun getChargeDecrement(): Int = 4096
    override fun getPowerRange(level: Int): IntRange = 23..60
    override fun getPath(): String = "blessed_forge"

    override fun chargeToString(charge: Int): String = "${(charge * 100 / getChargeDecrement())}%"
    override fun getTooltipColor(): Formatting = Formatting.YELLOW
    override fun getTooltipArgs(hoe: ItemStack): List<String> = listOf(chargeToString(getCharge(hoe)))
}

