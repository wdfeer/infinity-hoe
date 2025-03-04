package org.wdfeer.infinity_hoe.enchantment.unique.uncommon

import net.fabricmc.loader.api.FabricLoader
import net.minecraft.enchantment.Enchantments
import net.minecraft.entity.ItemEntity
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.registry.Registries
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import org.wdfeer.infinity_hoe.InfinityHoe
import org.wdfeer.infinity_hoe.enchantment.HoeEnchantment
import org.wdfeer.infinity_hoe.event.listener.HarvestListener
import org.wdfeer.infinity_hoe.extension.getEnchantmentLevel
import org.wdfeer.infinity_hoe.extension.getStatusPotency
import org.wdfeer.infinity_hoe.extension.roll
import kotlin.math.max
import kotlin.random.Random

object GrowingCapital : HoeEnchantment(Rarity.UNCOMMON), HarvestListener {
    private const val NUMISMATIC_OVERHAUL_ID = "numismatic-overhaul"

    val getSelfIfCanRegister
        get() = if (FabricLoader.getInstance().isModLoaded(NUMISMATIC_OVERHAUL_ID)) listOf(this) else emptyList()

    override fun getPowerRange(level: Int): IntRange = 15..45
    override fun getPath(): String = "growing_capital"

    override fun onCropBroken(
        world: ServerWorld,
        player: ServerPlayerEntity,
        hoe: ItemStack,
        pos: BlockPos,
        mature: Boolean
    ) {
        if (!FabricLoader.getInstance().isModLoaded(NUMISMATIC_OVERHAUL_ID)) return

        val luck = player.getStatusPotency(StatusEffects.LUCK)
        val fortune = hoe.getEnchantmentLevel(Enchantments.FORTUNE)
        val chanceMult = 1f + max(luck / 2f + 0.5f, 0f) + max(fortune / 2f + 0.5f, 0f)

        val coinId: String = when {
            Random.roll(1 / 7777f * chanceMult) -> "$NUMISMATIC_OVERHAUL_ID:gold_coin"
            Random.roll(1 / 777f * chanceMult) -> "$NUMISMATIC_OVERHAUL_ID:silver_coin"
            Random.roll(1 / 77f * chanceMult) -> "$NUMISMATIC_OVERHAUL_ID:bronze_coin"
            else -> null
        } ?: return

        val coin: Item = Registries.ITEM[Identifier(coinId)].takeIf { it != Items.AIR } ?: run {
            InfinityHoe.logger.error("Coin $coinId not found!")
            return
        }
        val position = pos.toCenterPos()
        val entity = ItemEntity(world, position.x, position.y, position.z, ItemStack(coin))

        world.spawnEntity(entity)
    }
}