package org.wdfeer.infinity_hoe.enchantment.unique.very_rare

import net.minecraft.entity.ItemEntity
import net.minecraft.item.HoeItem
import org.wdfeer.infinity_hoe.enchantment.EnchantmentLoader
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.extension.hasEnchantment

class Automata : HoeEnchantment(Rarity.VERY_RARE) {
    override fun getPowerRange(level: Int): IntRange = 25..60

    override fun getPath(): String = "automata"

    companion object {
        fun mixinItemEntityTick(itemEntity: ItemEntity) {
            if (itemEntity.stack.item !is HoeItem) return
            if (!itemEntity.stack.hasEnchantment(EnchantmentLoader.automata)) return

            tick(itemEntity)
        }

        private fun tick(validItemEntity: ItemEntity) {
            // TODO: Implement item entity logic
        }
    }
}