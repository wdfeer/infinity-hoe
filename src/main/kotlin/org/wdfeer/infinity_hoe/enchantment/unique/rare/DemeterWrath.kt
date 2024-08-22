package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageTypes
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Formatting
import org.wdfeer.infinity_hoe.enchantment.charge.HarvestChargeEnchantment
import org.wdfeer.infinity_hoe.event.listener.HarvestListener
import org.wdfeer.infinity_hoe.event.listener.PreAttackListener
import org.wdfeer.infinity_hoe.extension.damage
import kotlin.math.log10

class DemeterWrath : HarvestChargeEnchantment(Rarity.RARE), HarvestListener, PreAttackListener {
    override fun getPowerRange(level: Int): IntRange = 10 ..50

    override fun getPath(): String = "demeter_wrath"

    override fun preAttack(player: ServerPlayerEntity, target: LivingEntity, hoe: ItemStack) {
        if (getCharge(hoe) == 0) return

        target.hurtTime = 0
        target.damage(DamageTypes.MAGIC, getDamage(getCharge(hoe)), player)
        setCharge(hoe, 0)
    }


    private fun getDamage(charge: Int): Float = log10(charge.toFloat() + 1f) * 5
    override fun chargeToString(charge: Int): String = "%.1f".format(getDamage(charge))

    override fun getMaxCharge(level: Int): Int = 10000
    override fun getChargeDecrement(): Int = 100 // Only affects the tooltip color
    override fun getTooltipColor(): Formatting = Formatting.GREEN

    override fun canAccept(other: Enchantment?): Boolean = other !is MysticBlade
}