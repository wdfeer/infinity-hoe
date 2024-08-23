package org.wdfeer.infinity_hoe.enchantment.unique.very_rare

import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Formatting
import org.wdfeer.infinity_hoe.enchantment.parent.charge.UsableHarvestChargeEnchantment

class PesticideOverload : UsableHarvestChargeEnchantment(Rarity.VERY_RARE) {
    override fun getPowerRange(level: Int): IntRange = 30..60

    override fun getPath(): String = "pesticide_overload"

    override fun useCharge(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack): Boolean {
        // TODO: Implement
        return true
    }

    override fun getTooltipColor(): Formatting = Formatting.DARK_PURPLE
}