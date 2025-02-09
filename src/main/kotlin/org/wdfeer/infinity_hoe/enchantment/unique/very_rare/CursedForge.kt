package org.wdfeer.infinity_hoe.enchantment.unique.very_rare

import net.minecraft.enchantment.Enchantments
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.item.ToolItem
import net.minecraft.registry.Registries
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Formatting
import org.wdfeer.infinity_hoe.enchantment.parent.charge.UsableHarvestChargeEnchantment
import org.wdfeer.infinity_hoe.extension.enchantmentMap
import org.wdfeer.infinity_hoe.extension.hasEnchantment

class CursedForge : UsableHarvestChargeEnchantment(Rarity.VERY_RARE) {
    override fun useCharge(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack): Boolean {
        val oldStack = player.handItems.firstOrNull {
            it != hoe && toolUpgrades.containsKey(it.item) && !it.hasEnchantment(Enchantments.VANISHING_CURSE)
        }

        return if (oldStack != null) {
            val oldItem = oldStack.item as ToolItem
            oldStack.count = 0

            val newItem = toolUpgrades[oldItem]
            val newStack = ItemStack(newItem)

            for (e in oldStack.enchantmentMap) newStack.addEnchantment(e.key, e.value)
            newStack.damage = oldStack.damage

            newStack.addEnchantment(Enchantments.VANISHING_CURSE, 1)

            player.inventory.insertStack(newStack)

            true
        } else false
    }

    override fun getMaxCharge(level: Int): Int = 128
    override fun getChargeDecrement(): Int = 128
    override fun getPowerRange(level: Int): IntRange = 18..60
    override fun getPath(): String = "cursed_forge"

    override fun chargeToString(charge: Int): String = "${(charge * 100 / getChargeDecrement())}%"
    override fun getTooltipColor(): Formatting = Formatting.DARK_RED
    override fun getTooltipArgs(hoe: ItemStack): List<String> = listOf(chargeToString(getCharge(hoe)))
}

private val toolUpgrades = mapOf(
    Items.WOODEN_SWORD to Items.STONE_SWORD,
    Items.WOODEN_PICKAXE to Items.STONE_PICKAXE,
    Items.WOODEN_SHOVEL to Items.STONE_SHOVEL,
    Items.WOODEN_AXE to Items.STONE_AXE,
    Items.WOODEN_HOE to Items.STONE_HOE,

    Items.STONE_SWORD to Items.IRON_SWORD,
    Items.STONE_PICKAXE to Items.IRON_PICKAXE,
    Items.STONE_SHOVEL to Items.IRON_SHOVEL,
    Items.STONE_AXE to Items.IRON_AXE,
    Items.STONE_HOE to Items.IRON_HOE,

    Items.IRON_SWORD to Items.DIAMOND_SWORD,
    Items.IRON_PICKAXE to Items.DIAMOND_PICKAXE,
    Items.IRON_SHOVEL to Items.DIAMOND_SHOVEL,
    Items.IRON_AXE to Items.DIAMOND_AXE,
    Items.IRON_HOE to Items.DIAMOND_HOE,

    Items.DIAMOND_SWORD to Items.NETHERITE_SWORD,
    Items.DIAMOND_PICKAXE to Items.NETHERITE_PICKAXE,
    Items.DIAMOND_SHOVEL to Items.NETHERITE_SHOVEL,
    Items.DIAMOND_AXE to Items.NETHERITE_AXE,
    Items.DIAMOND_HOE to Items.NETHERITE_HOE,

    Items.GOLDEN_SWORD to Items.DIAMOND_SWORD,
    Items.GOLDEN_PICKAXE to Items.DIAMOND_PICKAXE,
    Items.GOLDEN_SHOVEL to Items.DIAMOND_SHOVEL,
    Items.GOLDEN_AXE to Items.DIAMOND_AXE,
    Items.GOLDEN_HOE to Items.DIAMOND_HOE
)