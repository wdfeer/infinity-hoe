package org.wdfeer.infinity_hoe.enchantment.unique.uncommon

import net.minecraft.entity.EntityGroup
import org.wdfeer.infinity_hoe.enchantment.bc.BetterCombatEnchantment

class Sickle : BetterCombatEnchantment() {
    override fun getPath(): String = "sickle"

    override fun getAttackDamage(level: Int, group: EntityGroup?): Float { return 0.5f }
}