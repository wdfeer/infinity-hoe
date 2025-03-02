package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.enchantment.Enchantments
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.item.ToolItem
import net.minecraft.registry.Registries
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Formatting
import net.minecraft.util.Identifier
import org.wdfeer.infinity_hoe.enchantment.parent.charge.UsableHarvestChargeEnchantment
import org.wdfeer.infinity_hoe.extension.enchantmentMap
import org.wdfeer.infinity_hoe.extension.hasEnchantment

object CursedForge : UsableHarvestChargeEnchantment(Rarity.VERY_RARE) {
    override fun useCharge(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack): Boolean {
        val oldStack = (
                player.handItems + player.inventory.getStack(40) // workaround for two-handed weapons from SimplySwords
                ).firstOrNull {
                it.item != Items.AIR && it != hoe && toolUpgrades.containsKey(it.item) && !it.hasEnchantment(
                    Enchantments.VANISHING_CURSE
                )
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

    override fun getMaxCharge(level: Int): Int = getChargeDecrement()
    override fun getChargeDecrement(): Int = 256
    override fun getPowerRange(level: Int): IntRange = 17..60
    override fun getPath(): String = "cursed_forge"

    override fun chargeToString(charge: Int): String = "${(charge * 100 / getChargeDecrement())}%"
    override fun getTooltipColor(): Formatting = Formatting.DARK_RED
    override fun getTooltipArgs(hoe: ItemStack): List<String> = listOf(chargeToString(getCharge(hoe)))

    val toolUpgrades: Map<Item, Item> by lazy {
        val toolUpgradesIds: Array<Pair<String, String>> = arrayOf(
            // Vanilla
            "minecraft:wooden_sword" to "minecraft:stone_sword",
            "minecraft:wooden_pickaxe" to "minecraft:stone_pickaxe",
            "minecraft:wooden_shovel" to "minecraft:stone_shovel",
            "minecraft:wooden_axe" to "minecraft:stone_axe",
            "minecraft:wooden_hoe" to "minecraft:stone_hoe",
            "minecraft:stone_sword" to "minecraft:iron_sword",
            "minecraft:stone_pickaxe" to "minecraft:iron_pickaxe",
            "minecraft:stone_shovel" to "minecraft:iron_shovel",
            "minecraft:stone_axe" to "minecraft:iron_axe",
            "minecraft:stone_hoe" to "minecraft:iron_hoe",
            "minecraft:iron_sword" to "minecraft:diamond_sword",
            "minecraft:iron_pickaxe" to "minecraft:diamond_pickaxe",
            "minecraft:iron_shovel" to "minecraft:diamond_shovel",
            "minecraft:iron_axe" to "minecraft:diamond_axe",
            "minecraft:iron_hoe" to "minecraft:diamond_hoe",
            "minecraft:diamond_sword" to "minecraft:netherite_sword",
            "minecraft:diamond_pickaxe" to "minecraft:netherite_pickaxe",
            "minecraft:diamond_shovel" to "minecraft:netherite_shovel",
            "minecraft:diamond_axe" to "minecraft:netherite_axe",
            "minecraft:diamond_hoe" to "minecraft:netherite_hoe",
            "minecraft:golden_sword" to "minecraft:diamond_sword",
            "minecraft:golden_pickaxe" to "minecraft:diamond_pickaxe",
            "minecraft:golden_shovel" to "minecraft:diamond_shovel",
            "minecraft:golden_axe" to "minecraft:diamond_axe",
            "minecraft:golden_hoe" to "minecraft:diamond_hoe",

            // Botania
            "minecraft:bow" to "botania:livingwood_bow",
            "botania:glass_pickaxe" to "minecraft:iron_pickaxe",
            "botania:manasteel_sword" to "botania:elementium_sword",
            "botania:manasteel_pickaxe" to "botania:elementium_pickaxe",
            "botania:manasteel_shovel" to "botania:elementium_shovel",
            "botania:manasteel_axe" to "botania:elementium_axe",
            "botania:manasteel_hoe" to "botania:elementium_hoe",

            // AE2
            "ae2:fluix_sword" to "minecraft:diamond_sword",
            "ae2:fluix_pickaxe" to "minecraft:diamond_pickaxe",
            "ae2:fluix_shovel" to "minecraft:diamond_shovel",
            "ae2:fluix_axe" to "minecraft:diamond_axe",
            "ae2:fluix_hoe" to "minecraft:diamond_hoe",

            // Simply Swords
            *arrayOf(
                "iron", "gold", "diamond", "netherite", "runic"
            ).flatMap { material ->
                listOf(
                    "longsword", "twinblade", "rapier", "katana", "sai", "spear", "glaive",
                    "warglaive", "cutlass", "claymore", "greathammer", "greataxe",
                    "chakram", "scythe", "halberd"
                ).mapNotNull { variant ->
                    val nextMaterial = when (material) {
                        "iron" -> "diamond"
                        "gold" -> "diamond"
                        "diamond" -> "netherite"
                        "netherite" -> "runic"
                        else -> null
                    }
                    nextMaterial?.let {
                        "simplyswords:${material}_${variant}" to "simplyswords:${nextMaterial}_${variant}"
                    }
                }
            }.toTypedArray()
        )

        toolUpgradesIds.map { Identifier(it.first) to Identifier(it.second) }
            .associate { Registries.ITEM[it.first] to Registries.ITEM[it.second] }
    }
}

