package org.wdfeer.infinity_hoe.enchantment.unique.treasure

import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.item.Items
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.event.listener.PlayerTicker
import org.wdfeer.infinity_hoe.extension.addTemporary
import org.wdfeer.infinity_hoe.extension.hasEnchantment
import org.wdfeer.infinity_hoe.extension.inventoryStacks
import org.wdfeer.infinity_hoe.extension.remove
import java.util.*

object SpeedMushroomEnchantment : HoeEnchantment(Rarity.VERY_RARE), PlayerTicker {
    override fun isTreasure(): Boolean = true
    override fun getPowerRange(level: Int): IntRange = 30..60
    override fun getPath(): String = "speed_mushroom"

    override fun canIteratePlayers(world: ServerWorld): Boolean = world.time % 21 == 0L

    override fun tickPlayer(world: ServerWorld, player: ServerPlayerEntity) {
        val uuid = UUID.nameUUIDFromBytes(getPath().toByteArray())
        val modifier = player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)?.getModifier(uuid)

        if (player.inventoryStacks.any { it.item != Items.AIR && it.hasEnchantment(this) }) {
            if (modifier == null)
                player.attributes.addTemporary(
                    EntityAttributes.GENERIC_MOVEMENT_SPEED,
                    EntityAttributeModifier(uuid, getPath(), 0.015, EntityAttributeModifier.Operation.ADDITION)
                )
        } else if (modifier != null) {
            player.attributes.remove(EntityAttributes.GENERIC_MOVEMENT_SPEED, modifier)
        }
    }
}