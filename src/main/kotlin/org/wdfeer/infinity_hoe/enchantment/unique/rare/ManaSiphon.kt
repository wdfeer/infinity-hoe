package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.fabricmc.loader.api.FabricLoader
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.event.listener.HoldTicker

object ManaSiphon : HoeEnchantment(Rarity.RARE), HoldTicker {
    val getSelfIfCanRegister
        get() = if (FabricLoader.getInstance().isModLoaded("botania")) listOf(this) else emptyList()

    override fun getPowerRange(level: Int): IntRange = 10..40
    override fun getPath(): String = "mana_siphon"

    override fun canIteratePlayers(world: ServerWorld): Boolean = world.time.toInt() % 2 == 0
    override fun holdTick(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack) {
        TODO("Not yet implemented")
    }
}