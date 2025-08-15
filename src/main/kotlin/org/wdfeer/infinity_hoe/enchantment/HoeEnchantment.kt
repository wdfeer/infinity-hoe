package org.wdfeer.infinity_hoe.enchantment

import net.minecraft.enchantment.Enchantment
import net.minecraft.registry.RegistryKey
import net.minecraft.util.Identifier

interface HoeEnchantment {
    val registryKey: RegistryKey<Enchantment>

    fun getPath(): Identifier = registryKey.value
}