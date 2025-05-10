package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.block.Blocks
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.item.ArmorItem
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.item.ToolItem
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Formatting
import net.minecraft.world.biome.BiomeKeys
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.enchantment.parent.charge.ChargeEnchantment
import org.wdfeer.infinity_hoe.enchantment.unique.treasure.AlchemyMushroomEnchantment
import org.wdfeer.infinity_hoe.enchantment.unique.treasure.HealMushroomEnchantment
import org.wdfeer.infinity_hoe.enchantment.unique.treasure.PoisonMushroomEnchantment
import org.wdfeer.infinity_hoe.event.listener.HoldTicker
import org.wdfeer.infinity_hoe.extension.hasEnchantment
import org.wdfeer.infinity_hoe.extension.inventoryStacks
import kotlin.math.pow

object FungusEnchanter : ChargeEnchantment(Rarity.RARE), HoldTicker {
    val mushroomEnchantments: List<HoeEnchantment> by lazy {
        listOf(
            PoisonMushroomEnchantment,
            HealMushroomEnchantment,
            AlchemyMushroomEnchantment
        )
    }

    override fun canIteratePlayers(world: ServerWorld): Boolean = world.time % 20L == 0L

    override fun holdTick(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack) {
        if (getCharge(hoe) == 0)
            setCharge(hoe, getMaxCharge(1))
        else {
            setCharge(hoe, getCharge(hoe) - getChargingSpeed(world, player))
            if (getCharge(hoe) <= 0)
                trigger(player, hoe)
        }
    }

    private fun getChargingSpeed(world: ServerWorld, player: ServerPlayerEntity): Int {
        var speed = 1

        val origin = player.blockPos

        if (world.getBiome(origin) == BiomeKeys.MUSHROOM_FIELDS)
            return 100

        val range = (-6..6)
        val blockTypes = mutableListOf(
            Blocks.BROWN_MUSHROOM,
            Blocks.RED_MUSHROOM,
            Blocks.POTTED_BROWN_MUSHROOM,
            Blocks.POTTED_RED_MUSHROOM,
            Blocks.MUSHROOM_STEM,
            Blocks.BROWN_MUSHROOM_BLOCK,
            Blocks.RED_MUSHROOM_BLOCK,
            Blocks.MYCELIUM
        )

        range.forEach { x ->
            range.forEach { y ->
                range.forEach { z ->
                    val pos = origin.add(x, y, z)
                    val block = world.getBlockState(pos).block
                    if (blockTypes.contains(block)) {
                        blockTypes.remove(block)
                        speed++
                    }
                }
            }
        }

        return 2f.pow(speed - 1).toInt()
    }

    private fun trigger(player: ServerPlayerEntity, hoe: ItemStack) {
        hoe.enchantments.remove(hoe.enchantments.find { EnchantmentHelper.getIdFromNbt(it as NbtCompound?) == getIdentifier() })

        val stack = player.inventoryStacks.filter { it.item != Items.AIR }
            .filter { it.item is ArmorItem || it.item is ToolItem }
            .filter { stack -> mushroomEnchantments.any { !stack.hasEnchantment(it) } }
            .randomOrNull() ?: return

        val enchantment = mushroomEnchantments.filter { !stack.hasEnchantment(it) }.random()
        stack.addEnchantment(enchantment, 1)
    }

    override fun getMaxCharge(level: Int): Int = 6000
    override fun getPowerRange(level: Int): IntRange = 0..25
    override fun getPath(): String = "fungus_enchanter"
    override fun getTooltipColor(): Formatting = Formatting.DARK_GREEN
}