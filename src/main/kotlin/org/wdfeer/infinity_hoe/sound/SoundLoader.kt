package org.wdfeer.infinity_hoe.sound

import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.sound.SoundEvent
import net.minecraft.util.Identifier
import org.wdfeer.infinity_hoe.InfinityHoe.MOD_ID

object SoundLoader {
    private val soundEvents: List<String> = listOf(
        "charged",
        "discharged"
    )

    fun initialize() {
        soundEvents.map { Identifier(MOD_ID, it) }.forEach { id ->
            Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id))
        }
    }
}