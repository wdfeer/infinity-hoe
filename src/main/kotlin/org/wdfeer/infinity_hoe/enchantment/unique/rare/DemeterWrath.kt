package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageTypes
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import org.wdfeer.infinity_hoe.enchantment.parent.DemeterEnchantment
import org.wdfeer.infinity_hoe.event.emitter.HoeHit
import org.wdfeer.infinity_hoe.event.listener.PreAttackListener
import org.wdfeer.infinity_hoe.extension.damage

object DemeterWrath : DemeterEnchantment(), PreAttackListener {
    override fun getPath(): String = "demeter_wrath"

    private const val DAMAGE = 5f
    override fun preAttack(player: ServerPlayerEntity, target: LivingEntity, hoe: ItemStack) {
        val charge = getCharge(hoe)
        if (charge < getChargeDecrement()) return

        target.damage(DamageTypes.MAGIC, DAMAGE, player)
        target.hurtTime = 0

        setChargeWithSound(player.serverWorld, player, hoe, charge - getChargeDecrement())

        HoeHit.postHit(hoe, target, player, this)
    }

    override fun getMaxCharge(level: Int): Int = getChargeDecrement() * 256
    override fun getChargeDecrement(): Int = 5
    override fun chargeToString(charge: Int): String = "%.1f".format(charge.toFloat() / getChargeDecrement())

    override fun getTooltipArgs(hoe: ItemStack): List<String> = buildList {
        add("%.1f".format(DAMAGE))
        addAll(super.getTooltipArgs(hoe))
    }

    override fun canAccept(other: Enchantment?): Boolean = super.canAccept(other) && other != MysticBlade
}