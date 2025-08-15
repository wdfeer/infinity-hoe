package org.wdfeer.infinity_hoe.enchantment

import net.minecraft.enchantment.Enchantment
import net.minecraft.registry.RegistryKey

interface HoeEnchantment {
    val registryKey: RegistryKey<Enchantment>
}