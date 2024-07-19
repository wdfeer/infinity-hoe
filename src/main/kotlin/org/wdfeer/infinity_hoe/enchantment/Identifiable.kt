package org.wdfeer.infinity_hoe.enchantment

import net.minecraft.util.Identifier
import org.wdfeer.infinity_hoe.InfinityHoe

interface Identifiable {
    fun getPath(): String

    fun getIdentifier(): Identifier = Identifier(InfinityHoe.MOD_ID, getPath())
}