package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.block.Blocks
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageTypes
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.stat.Stats
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.event.listener.PreAttackListener
import org.wdfeer.infinity_hoe.extension.damage
import kotlin.math.log10

class DemeterWrath : HoeEnchantment(Rarity.RARE), PreAttackListener {
    override fun getPowerRange(level: Int): IntRange = 10 ..50

    override fun getPath(): String = "demeter_wrath"

    private fun getCrops(player: ServerPlayerEntity): Int = player.statHandler.run { listOf(
        getStat(Stats.MINED.getOrCreateStat(Blocks.BEETROOTS)),
        getStat(Stats.MINED.getOrCreateStat(Blocks.CARROTS)),
        getStat(Stats.MINED.getOrCreateStat(Blocks.POTATOES)),
        getStat(Stats.MINED.getOrCreateStat(Blocks.WHEAT)),
    ) }.sum()

    private fun getDamage(player: ServerPlayerEntity): Float = log10(getCrops(player).toFloat())

    override fun preAttack(player: ServerPlayerEntity, target: LivingEntity, hoe: ItemStack) {
        target.hurtTime = 0
        target.damage(DamageTypes.MAGIC, getDamage(player), player)
    }
}