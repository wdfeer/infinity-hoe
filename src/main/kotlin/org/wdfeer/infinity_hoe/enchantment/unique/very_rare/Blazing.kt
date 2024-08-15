package org.wdfeer.infinity_hoe.enchantment.unique.very_rare

import net.minecraft.entity.projectile.FireballEntity
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.Style
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.event.listener.AirUseListener
import org.wdfeer.infinity_hoe.event.listener.AppendTooltipListener
import org.wdfeer.infinity_hoe.event.listener.HarvestListener
import org.wdfeer.infinity_hoe.util.getEnchantmentLevel

class Blazing : HoeEnchantment(Rarity.VERY_RARE), HarvestListener, AirUseListener, AppendTooltipListener {
    override val maxLvl: Int
        get() = 2

    override fun getPowerRange(level: Int): IntRange = 20 + 5 * level..60

    override fun getPath(): String = "blazing"

    private val nbtKey = getPath() + "_charge"
    private fun getMaxCharge(level: Int) = 50 * level

    override fun onCropBroken(
        world: ServerWorld,
        player: ServerPlayerEntity,
        hoe: ItemStack,
        pos: BlockPos,
        mature: Boolean
    ) {
        if (!mature) return

        val nbt = hoe.orCreateNbt
        val charge = nbt.getInt(nbtKey)
        val level = hoe.getEnchantmentLevel(this)

        if (nbt.contains(nbtKey) && charge < getMaxCharge(level)) {
            nbt.putInt(nbtKey, charge + 1)
        } else
            nbt.putInt(nbtKey, 1)
    }

    override fun onUseInAir(world: ServerWorld, player: ServerPlayerEntity, hoe: ItemStack) {
        val nbt = hoe.nbt ?: return
        val charge = nbt.getInt(nbtKey)
        if (!nbt.contains(nbtKey) || charge <= 0) return

        world.spawnEntity(createFireball(world, player))

        player.itemCooldownManager.set(hoe.item, 10)

        nbt.putInt(nbtKey, charge - 1)
    }

    private fun createFireball(
        world: ServerWorld,
        player: ServerPlayerEntity
    ): FireballEntity {
        val velocity = player.rotationVector.multiply(3.0)
        val pos = player.eyePos

        val fireball = FireballEntity(world, player, velocity.x, velocity.y, velocity.z, 0)
        fireball.setPosition(pos)

        return fireball
    }

    override fun appendTooltip(stack: ItemStack, tooltip: MutableList<Text>) {
        val nbt = stack.nbt
        val charge = if (nbt?.contains(nbtKey) == true) nbt.getInt(nbtKey) else 0
        val maxCharge = getMaxCharge(stack.getEnchantmentLevel(this))

        tooltip.add(Text.translatable("tooltip.infinity_hoe.blazing.charge", charge, maxCharge).apply {
            style = Style.EMPTY.withColor(if (charge > 0) Formatting.RED else Formatting.GRAY)
        })
    }
}