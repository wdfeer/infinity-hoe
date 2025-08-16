package org.wdfeer.infinity_hoe.extension

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap
import net.minecraft.entity.attribute.AttributeContainer
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.registry.Registries
import net.minecraft.registry.entry.RegistryEntry

private fun toMultimap(attribute: EntityAttribute, modifier: EntityAttributeModifier): Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> =
    ArrayListMultimap.create<RegistryEntry<EntityAttribute>, EntityAttributeModifier>()
        .also { it.put(Registries.ATTRIBUTE.getEntry(attribute), modifier) }

fun AttributeContainer.remove(attribute: EntityAttribute, modifier: EntityAttributeModifier) =
    this.removeModifiers(toMultimap(attribute, modifier))

fun AttributeContainer.addTemporary(attribute: EntityAttribute, modifier: EntityAttributeModifier) =
    this.addTemporaryModifiers(toMultimap(attribute, modifier))