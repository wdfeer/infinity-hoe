package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.damage.DamageTypes
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Identifier
import org.wdfeer.infinity_hoe.InfinityHoe.MOD_ID
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.enchantment.parent.CropCatalyzer
import org.wdfeer.infinity_hoe.event.listener.PlayerTicker
import org.wdfeer.infinity_hoe.extension.*
import java.util.*
import kotlin.random.Random

object SoulSiphon : HoeEnchantment, PlayerTicker, CropCatalyzer {
    private const val INTERVAL: Int = 60

    override fun getPath(): String = "soul_siphon"

    override fun canIteratePlayers(world: ServerWorld): Boolean = world.time % INTERVAL == 0L

    override fun tickPlayer(world: ServerWorld, player: ServerPlayerEntity) {
        val attribute = EntityAttributes.GENERIC_MAX_HEALTH
        val mod = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)?.getModifier(modifierId)

        val hoe = player.handItems.find { !it.isEmpty && it.hasEnchantment(this) }

        if (hoe == null || player.health <= 2) {
            if (mod != null && Random.roll(3)) {
                recover(mod, player, attribute.value())
            }

            return
        }

        if (catalyze(world, player, 3, hoe, 0.5f)) {
            player.attributes.addTemporary(attribute.value(), getModifier(mod?.value?.minus(2) ?: -2.0))

            player.damage(DamageTypes.MAGIC, 2f)
        }
    }

    private val modifierId = Identifier.of(MOD_ID,"soul_siphon")
    private fun getModifier(hpIncrease: Double) = EntityAttributeModifier(
        modifierId,
        hpIncrease,
        EntityAttributeModifier.Operation.ADD_VALUE
    )

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