package org.wdfeer.infinity_hoe.enchantment.unique.very_rare

import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Formatting
import org.wdfeer.infinity_hoe.enchantment.parent.charge.HarvestChargeEnchantment
import org.wdfeer.infinity_hoe.event.listener.PlayerTicker
import org.wdfeer.infinity_hoe.extension.hasEnchantment
import org.wdfeer.infinity_hoe.extension.inventoryStacks

object SwordConjuring : HarvestChargeEnchantment(Rarity.VERY_RARE), PlayerTicker {
    override fun getMaxCharge(level: Int): Int = 64
    override fun getPowerRange(level: Int): IntRange = 25..60
    override fun getPath(): String = "sword_conjuring"
    override fun getTooltipColor(): Formatting = Formatting.GOLD

    override fun canIteratePlayers(world: ServerWorld): Boolean = true
    override fun tickPlayer(world: ServerWorld, player: ServerPlayerEntity) {
        var hoe: ItemStack? = null
        val swords: MutableList<ItemStack> = mutableListOf()

        for (stack in player.inventoryStacks) {
            if (stack.hasEnchantment(this))
                hoe = stack
            if (stack.item == swordItem && stack.name.string == SWORD_NAME) {
                swords.add(stack)
            }
        }

        hoe ?: return

        // TODO: add swords if enough charge

        processSwords(swords)
    }

    private val swordItem: Item = Items.GOLDEN_SWORD
    private const val SWORD_NAME = "Magic Sword"

    private fun processSwords(swords: List<ItemStack>) {
        // TODO: implement
    }
}