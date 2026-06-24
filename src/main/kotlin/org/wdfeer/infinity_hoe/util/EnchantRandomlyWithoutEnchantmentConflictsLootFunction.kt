package org.wdfeer.infinity_hoe.util

import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.enchantment.Enchantment
import net.minecraft.item.ItemStack
import net.minecraft.loot.condition.LootCondition
import net.minecraft.loot.context.LootContext
import net.minecraft.loot.function.ConditionalLootFunction
import net.minecraft.loot.function.LootFunction
import net.minecraft.loot.function.LootFunctionType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier
import org.wdfeer.infinity_hoe.InfinityHoe
import org.wdfeer.infinity_hoe.extension.enchantmentMap

/**
 * EnchantRandomlyLootFunction but it actually respects enchantment exclusivity.
 */
class EnchantRandomlyWithoutEnchantmentConflictsLootFunction internal constructor(
    conditions: MutableList<LootCondition>,
) : ConditionalLootFunction(conditions) {
    companion object {
        fun initialize() {}

        val CODEC: MapCodec<EnchantRandomlyWithoutEnchantmentConflictsLootFunction?> =
            RecordCodecBuilder.mapCodec { instance: RecordCodecBuilder.Instance<EnchantRandomlyWithoutEnchantmentConflictsLootFunction?>? ->
                addConditionsField(instance).apply(
                    instance
                ) { EnchantRandomlyWithoutEnchantmentConflictsLootFunction(it) }
            }


        val TYPE: LootFunctionType<EnchantRandomlyWithoutEnchantmentConflictsLootFunction?> =
            Registry.register(
                Registries.LOOT_FUNCTION_TYPE,
                Identifier.of(InfinityHoe.MOD_ID, "enchant_randomly_without_enchantment_conflicts"),
                LootFunctionType(CODEC)
            )
    }

    class Builder : ConditionalLootFunction.Builder<Builder>() {
        override fun getThisBuilder(): Builder = this

        override fun build(): LootFunction =
            EnchantRandomlyWithoutEnchantmentConflictsLootFunction(thisBuilder.conditions)
    }

    override fun getType(): LootFunctionType<out ConditionalLootFunction?> = TYPE

    override fun process(
        stack: ItemStack, context: LootContext
    ): ItemStack {
        val enchantmentRegistry = context.world.registryManager.get(RegistryKeys.ENCHANTMENT)
        val enchantments = enchantmentRegistry.filter { enchant ->
            enchant.isSupportedItem(stack) && !stack.enchantmentMap.any {
                it.key == enchantmentRegistry.getKey(
                    enchant
                )
            } && enchant.exclusiveSet.none {
                stack.enchantmentMap.containsKey<RegistryKey<Enchantment>>(it.key.get())
            }
        }
        val enchant = enchantments.randomOrNull() ?: return stack
        return stack.apply {
            addEnchantment(
                enchantmentRegistry.getEntry(enchant), (enchant.minLevel..enchant.maxLevel).random()
            )
        }
    }
}