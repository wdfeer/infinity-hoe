package org.wdfeer.infinity_hoe.enchantment.unique.very_rare

import net.minecraft.entity.projectile.FireballEntity
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.event.listener.AirUseListener
import org.wdfeer.infinity_hoe.event.listener.AppendTooltipListener
import org.wdfeer.infinity_hoe.event.listener.HarvestListener
import kotlin.math.cos
import kotlin.math.sin

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

        fun createFireballEntity(): FireballEntity {
            val fireball = FireballEntity(world, player, 0.0, 0.0, 0.0, 0)

            val playerFacing = player.rotationClient
            val playerPos = player.pos
            val forwardVector = Vec3d(
                -sin(Math.toRadians(playerFacing.y.toDouble())),
                0.0,
                cos(Math.toRadians(playerFacing.y.toDouble()))
            )

            fireball.updatePosition(
                playerPos.x + forwardVector.x * 2.0,
                playerPos.y + player.eyeY - 0.10000000149011612,
                playerPos.z + forwardVector.z * 2.0
            )

            fireball.setVelocity(forwardVector.x * 3.0, 0.0, forwardVector.z * 3.0)

            return fireball
        }

        world.spawnEntity(createFireballEntity())

        nbt.putInt(nbtKey, charge - 1)
    }

    override fun appendTooltip(stack: ItemStack, tooltip: MutableList<Text>) {
        val nbt = stack.nbt ?: return
        if (!nbt.contains(nbtKey)) return
        val charge = nbt.getInt(nbtKey)

        tooltip.add(Text.of("Fireballs: $charge")) // TODO: change this to translatable
    }
}