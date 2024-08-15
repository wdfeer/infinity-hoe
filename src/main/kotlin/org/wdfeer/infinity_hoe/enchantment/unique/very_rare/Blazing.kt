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

class Blazing : HoeEnchantment(Rarity.VERY_RARE), HarvestListener, AirUseListener, AppendTooltipListener {
    override fun getPowerRange(level: Int): IntRange = 20..70

    override fun getPath(): String = "blazing"

    private val nbtKey = getPath() + "_charge"
    override fun onCropBroken(
        world: ServerWorld,
        player: ServerPlayerEntity,
        hoe: ItemStack,
        pos: BlockPos,
        mature: Boolean
    ) {
        if (!mature) return

        val nbt = hoe.orCreateNbt

        if (nbt.contains(nbtKey))
            nbt.putInt(nbtKey, nbt.getInt(nbtKey) + 1)
        else
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
        val nbt = stack.nbt ?: return
        if (!nbt.contains(nbtKey)) return
        val charge = nbt.getInt(nbtKey)

        tooltip.add(Text.translatable("tooltip.infinity_hoe.blazing.charge", charge).apply {
            style = Style.EMPTY.withColor(if (charge > 0) Formatting.RED else Formatting.GRAY)
        })
    }
}