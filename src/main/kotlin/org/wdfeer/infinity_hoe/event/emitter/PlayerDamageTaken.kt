package org.wdfeer.infinity_hoe.event.emitter

import net.minecraft.item.HoeItem
import net.minecraft.server.network.ServerPlayerEntity
import org.wdfeer.infinity_hoe.enchantment.EnchantmentLoader
import org.wdfeer.infinity_hoe.event.listener.PlayerDamageTaken
import org.wdfeer.infinity_hoe.extension.hasEnchantment

object PlayerDamageTaken {
    fun mixinPreDamageTaken(
        player: ServerPlayerEntity,
        amount: Float
    ) = onDamageTaken(player, amount, PlayerDamageTaken::preDamageTaken)

    fun mixinPostDamageTaken(
        player: ServerPlayerEntity,
        amount: Float,
    ) = onDamageTaken(player, amount, PlayerDamageTaken::postDamageTaken)

    private inline fun onDamageTaken(player: ServerPlayerEntity, amount: Float, method: PlayerDamageTaken.(ServerPlayerEntity, Float) -> Unit) {
        EnchantmentLoader.enchantments
            .filter { e -> player.handItems.any { it.item is HoeItem && it.hasEnchantment(e) } }
            .filterIsInstance<PlayerDamageTaken>()
            .forEach { it.method(player, amount) }
    }
}