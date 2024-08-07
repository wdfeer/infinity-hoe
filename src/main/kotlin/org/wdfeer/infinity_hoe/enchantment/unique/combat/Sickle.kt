package org.wdfeer.infinity_hoe.enchantment.unique.combat

import net.minecraft.entity.EntityGroup

class Sickle : BetterCombatEnchantment() {
    override fun getPath(): String = "sickle"

    override fun getAttackDamage(level: Int, group: EntityGroup?): Float { return 0.5f }
}