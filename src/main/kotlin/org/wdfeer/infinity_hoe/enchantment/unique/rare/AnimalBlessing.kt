package org.wdfeer.infinity_hoe.enchantment.unique.rare

import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.enchantment.unique.uncommon.GrowthAcceleration
import org.wdfeer.infinity_hoe.event.listener.HoldTicker
import org.wdfeer.infinity_hoe.extension.*
import kotlin.math.max
import kotlin.math.min

class AnimalBlessing : HoeEnchantment(Rarity.RARE), HoldTicker {
    override fun getPath(): String = "animal_blessing"

    override val maxLvl: Int
        get() = 3

    override fun getPowerRange(level: Int): IntRange = (12..20).incrementBounds(level * 5)

    override fun canIteratePlayers(world: ServerWorld) = world.time % 20 == 0L

    override fun holdTick(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack) {
        val level = hoe.getEnchantmentLevel(this) * 2 + 1
        for (e in world.iterateEntities().filterIsInstance<AnimalEntity>().filter { it.distanceTo(player) < 25 }) {
            // for breeding to work breedingAge must be 0
            if (e.breedingAge > 0)
                e.breedingAge = max(0, e.breedingAge - level)
            else if (e.breedingAge < 0)
                e.breedingAge = min(0, e.breedingAge + level)

            // can only be fed if loveTicks < 0
            if (e.loveTicks > 0)
                e.loveTicks -= level
        }
    }

    override fun canAccept(other: Enchantment?): Boolean =
        // because it's also a blessing (Dryad's Blessing)
        super.canAccept(other) && other !is GrowthAcceleration
}