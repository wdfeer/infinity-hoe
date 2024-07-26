package org.wdfeer.infinity_hoe.util

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap
import net.minecraft.entity.attribute.AttributeContainer
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier

private fun toMultimap(attribute: EntityAttribute, modifier: EntityAttributeModifier): Multimap<EntityAttribute, EntityAttributeModifier> =
    ArrayListMultimap.create<EntityAttribute, EntityAttributeModifier>()
        .also { it.put(attribute, modifier) }

fun AttributeContainer.remove(attribute: EntityAttribute, modifier: EntityAttributeModifier) =
    this.removeModifiers(toMultimap(attribute, modifier))

fun AttributeContainer.addTemporary(attribute: EntityAttribute, modifier: EntityAttributeModifier) =
    this.addTemporaryModifiers(toMultimap(attribute, modifier))