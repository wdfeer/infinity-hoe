package org.wdfeer.infinity_hoe.enchantment

import net.minecraft.enchantment.Enchantment
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier
import org.wdfeer.infinity_hoe.InfinityHoe.MOD_ID

interface HoeEnchantment {
    val registryKey: RegistryKey<Enchantment>
        get() = RegistryKey.of(RegistryKeys.ENCHANTMENT, id)

    val id: Identifier get() = Identifier.of(MOD_ID, getPath())

    fun getPath(): String
}