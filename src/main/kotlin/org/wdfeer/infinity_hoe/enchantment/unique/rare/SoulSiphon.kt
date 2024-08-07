package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.damage.DamageTypes
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.world.World
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.enchantment.catalyze.CropCatalyzer.trigger
import org.wdfeer.infinity_hoe.enchantment.unique.uncommon.GrowthAcceleration
import org.wdfeer.infinity_hoe.util.*
import java.util.*
import kotlin.random.Random

class SoulSiphon : HoeEnchantment(Rarity.RARE) {
    override fun getPath(): String = "soul_siphon"

    override fun getPowerRange(level: Int): IntRange = 19..50

    override fun canAccept(other: Enchantment?): Boolean = other !is GrowthAcceleration

    init {
        ServerTickEvents.END_WORLD_TICK.register(::onWorldTick)
    }

    private val interval: Int = 60
    private fun canIteratePlayers(world: World): Boolean = world.time % interval == 0L

    private val modifierUUID = UUID.nameUUIDFromBytes("soul_siphon".toByteArray())
    private fun getModifier(hpIncrease: Double) = EntityAttributeModifier(
        modifierUUID,
        "soul_siphon",
        hpIncrease,
        EntityAttributeModifier.Operation.ADDITION)
    private fun onWorldTick(world: ServerWorld) {
        if (canIteratePlayers(world))
            for (player in world.players){
                if (!player.isAlive) continue

                val attribute = EntityAttributes.GENERIC_MAX_HEALTH
                val mod = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)?.getModifier(modifierUUID)

                val hoe = player.handItems.find { !it.isEmpty && it.hasEnchantment(this) }

                if (hoe == null || player.health <= 2) {
                    if (mod != null && Random.roll(3)) {
                        recover(mod, player, attribute)
                    }

                    continue
                }

                if (trigger(world, player, 3, hoe, 0.5f)) {
                    player.attributes.addTemporary(attribute, getModifier(mod?.value?.minus(2) ?: -2.0))

                    player.damage(DamageTypes.MAGIC, 2f)
                }
            }
    }

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