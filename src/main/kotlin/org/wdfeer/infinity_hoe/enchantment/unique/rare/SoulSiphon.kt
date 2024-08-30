package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.damage.DamageTypes
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.enchantment.parent.CropCatalyzer
import org.wdfeer.infinity_hoe.enchantment.unique.uncommon.GrowthAcceleration
import org.wdfeer.infinity_hoe.event.listener.PlayerTicker
import org.wdfeer.infinity_hoe.extension.*
import java.util.*
import kotlin.random.Random

class SoulSiphon : HoeEnchantment(Rarity.RARE), PlayerTicker, CropCatalyzer {
    companion object {
        private const val INTERVAL: Int = 60
    }

    override fun getPath(): String = "soul_siphon"
    override fun getPowerRange(level: Int): IntRange = 19..50
    override fun canAccept(other: Enchantment?): Boolean = other !is GrowthAcceleration


    override fun canIteratePlayers(world: ServerWorld): Boolean = world.time % INTERVAL == 0L

    override fun tickPlayer(world: ServerWorld, player: ServerPlayerEntity) {
        val attribute = EntityAttributes.GENERIC_MAX_HEALTH
        val mod = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)?.getModifier(modifierUUID)

        val hoe = player.handItems.find { !it.isEmpty && it.hasEnchantment(this) }

        if (hoe == null || player.health <= 2) {
            if (mod != null && Random.roll(3)) {
                recover(mod, player, attribute)
            }

            return
        }

        if (catalyze(world, player, 3, hoe, 0.5f)) {
            player.attributes.addTemporary(attribute, getModifier(mod?.value?.minus(2) ?: -2.0))

            player.damage(DamageTypes.MAGIC, 2f)
        }
    }

    private val modifierUUID = UUID.nameUUIDFromBytes("soul_siphon".toByteArray())
    private fun getModifier(hpIncrease: Double) = EntityAttributeModifier(
        modifierUUID,
        "soul_siphon",
        hpIncrease,
        EntityAttributeModifier.Operation.ADDITION)

    private fun recover(
        mod: EntityAttributeModifier,
        player: ServerPlayerEntity,
        attribute: EntityAttribute
    ) {
        if (mod.value >= -2.0)
            player.attributes.remove(attribute, mod)
        else
            player.attributes.addTemporary(attribute, getModifier(mod.value + 2.0))
    }
}