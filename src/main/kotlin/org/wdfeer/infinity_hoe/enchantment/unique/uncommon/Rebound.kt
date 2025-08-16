package org.wdfeer.infinity_hoe.enchantment.unique.uncommon

import net.minecraft.entity.EntityGroup
import org.wdfeer.infinity_hoe.enchantment.parent.BetterCombatEnchantment

object Rebound : BetterCombatEnchantment() {
    override fun getPath(): String = "rebound"

    // FIXME: define the attribute in json
    override fun getAttackDamage(level: Int, group: EntityGroup?): Float = -0.5f
}