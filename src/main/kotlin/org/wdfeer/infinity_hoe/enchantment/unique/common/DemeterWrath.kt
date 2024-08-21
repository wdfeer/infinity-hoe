package org.wdfeer.infinity_hoe.enchantment.unique.common

import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.enchantment.demeter.DemeterEnchantment
import org.wdfeer.infinity_hoe.event.listener.HarvestListener
import java.util.UUID
import kotlin.math.log10

class DemeterWrath : HoeEnchantment(Rarity.COMMON), HarvestListener, DemeterEnchantment {
    override fun getPowerRange(level: Int): IntRange = 10 ..50

    override fun getPath(): String = "demeter_wrath"

    private fun getDamage(world: ServerWorld, player: ServerPlayerEntity): Double = log10(getPlayerHarvestCount(world, player).toDouble() + 1.0)

    private val attributeUUID = UUID.nameUUIDFromBytes(getIdentifier().toString().toByteArray())

    override fun onCropBroken(
        world: ServerWorld,
        player: ServerPlayerEntity,
        hoe: ItemStack,
        pos: BlockPos,
        mature: Boolean
    ) = updateHoe(player, hoe)

    private fun updateHoe(player: ServerPlayerEntity, hoe: ItemStack) =
        hoe.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, getModifier(player), EquipmentSlot.MAINHAND)

    private fun getModifier(player: ServerPlayerEntity) = EntityAttributeModifier(
        attributeUUID,
        getPath(),
        getDamage(player.serverWorld, player),
        EntityAttributeModifier.Operation.ADDITION
    )
}