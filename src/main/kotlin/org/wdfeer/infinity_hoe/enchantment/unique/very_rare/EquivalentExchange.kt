package org.wdfeer.infinity_hoe.enchantment.unique.very_rare

import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Formatting
import org.wdfeer.infinity_hoe.enchantment.parent.charge.ChargeEnchantment
import org.wdfeer.infinity_hoe.enchantment.parent.charge.HarvestChargeEnchantment
import org.wdfeer.infinity_hoe.event.listener.HoldTicker
import org.wdfeer.infinity_hoe.extension.inventoryStacks

object EquivalentExchange : HarvestChargeEnchantment(Rarity.VERY_RARE), HoldTicker {
    override fun getMaxCharge(level: Int): Int = 1000
    override fun getPowerRange(level: Int): IntRange = 20..60
    override fun getPath(): String = "equivalent_exchange"
    override fun getTooltipColor(): Formatting = Formatting.YELLOW

    // tick with an interval for performance
    override fun canIteratePlayers(world: ServerWorld): Boolean = world.time.toInt() % 40 == 0

    override fun holdTick(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack) {
        if (getCharge(hoe) <= minCost) return

        for (stack in player.inventoryStacks) {
            val transformation = transformations[stack.item] ?: continue
            if (transformation.cost > getCharge(hoe)) continue

            if (stack.count >= transformation.inputCount) {
                stack.decrement(transformation.inputCount)
                player.inventory.insertStack(ItemStack(transformation.output, transformation.outputCount))
                setCharge(hoe, getCharge(hoe) - transformation.cost)

                if (getCharge(hoe) < minCost) break
            }
        }
    }

    private data class Transformation(val inputCount: Int, val output: Item, val outputCount: Int, val cost: Int)

    private val transformations: Map<Item, Transformation> = mapOf(
        Items.GRAVEL to Transformation(4, Items.SAND, 4, 3),
        Items.ARROW to Transformation(2, Items.SPECTRAL_ARROW, 2, 5),
        Items.POISONOUS_POTATO to Transformation(1, Items.POTATO, 1, 6),
        Items.ROTTEN_FLESH to Transformation(1, Items.LEATHER, 1, 6),
        Items.STRING to Transformation(1, Items.COBWEB, 1, 8),
        Items.COPPER_ORE to Transformation(8, Items.GOLD_ORE, 1, 15),
        Items.COPPER_INGOT to Transformation(8, Items.GOLD_INGOT, 1, 20),
        Items.END_STONE to Transformation(16, Items.ENDER_PEARL, 1, 20),
        Items.CHARCOAL to Transformation(8, Items.COAL, 8, 30),
        Items.BLAZE_POWDER to Transformation(1, Items.BLAZE_ROD, 1, 80),
        Items.COAL to Transformation(64, Items.DIAMOND, 1, 100),
        Items.IRON_BLOCK to Transformation(5, Items.IRON_INGOT, 64, 200),
        Items.GOLDEN_APPLE to Transformation(1, Items.ENCHANTED_GOLDEN_APPLE, 1, 777)
    )
    private val minCost: Int = transformations.minOf { it.value.cost }
}