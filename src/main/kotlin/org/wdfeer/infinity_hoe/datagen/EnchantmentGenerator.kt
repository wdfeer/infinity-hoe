package org.wdfeer.infinity_hoe.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider
import net.minecraft.registry.RegistryWrapper
import java.util.concurrent.CompletableFuture

class EnchantmentGenerator(
    output: FabricDataOutput,
    registriesFuture: CompletableFuture<RegistryWrapper.WrapperLookup>
) : FabricDynamicRegistryProvider(output, registriesFuture) {
    override fun configure(
        registries: RegistryWrapper.WrapperLookup,
        entries: Entries
    ) {
        TODO("Create enchantment entries")
    }

    override fun getName(): String = "InfinityHoeEnchantmentGenerator"
}