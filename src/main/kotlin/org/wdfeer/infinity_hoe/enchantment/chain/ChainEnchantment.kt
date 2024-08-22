package org.wdfeer.infinity_hoe.enchantment.chain

import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment

abstract class ChainEnchantment<T : ChainAction<Any?>>(rarity: Rarity) : HoeEnchantment(rarity) {
    protected val manager: ActionManager<T> = ActionManager()
}