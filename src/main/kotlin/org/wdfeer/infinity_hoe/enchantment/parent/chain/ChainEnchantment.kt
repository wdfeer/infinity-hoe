package org.wdfeer.infinity_hoe.enchantment.parent.chain

import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment

abstract class ChainEnchantment<T : ChainAction>(rarity: Rarity) : HoeEnchantment(rarity) {
    protected val manager: ActionManager<T> = ActionManager()
}