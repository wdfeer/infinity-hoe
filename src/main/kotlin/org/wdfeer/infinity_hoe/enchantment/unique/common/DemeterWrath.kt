package org.wdfeer.infinity_hoe.enchantment.unique.common

import net.minecraft.block.Blocks
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.stat.Stats
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.event.listener.HarvestListener
import java.util.UUID
import kotlin.math.log10

class DemeterWrath : HoeEnchantment(Rarity.COMMON), HarvestListener {
    override fun getPowerRange(level: Int): IntRange = 10 ..50

    override fun getPath(): String = "demeter_wrath"

    private fun getCrops(player: ServerPlayerEntity): Int = player.statHandler.run { listOf(
        getStat(Stats.MINED.getOrCreateStat(Blocks.BEETROOTS)),
        getStat(Stats.MINED.getOrCreateStat(Blocks.CARROTS)),
        getStat(Stats.MINED.getOrCreateStat(Blocks.POTATOES)),
        getStat(Stats.MINED.getOrCreateStat(Blocks.WHEAT)),
    ) }.sum()

    private fun getDamage(player: ServerPlayerEntity): Double = log10(getCrops(player).toDouble() + 1.0)

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
        getDamage(player),
        EntityAttributeModifier.Operation.ADDITION
    )
}