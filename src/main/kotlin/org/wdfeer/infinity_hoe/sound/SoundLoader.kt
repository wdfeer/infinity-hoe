package org.wdfeer.infinity_hoe.sound

import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.sound.SoundEvent
import net.minecraft.util.Identifier
import org.wdfeer.infinity_hoe.InfinityHoe.MOD_ID

object SoundLoader {
    private val chargedId = Identifier.of(MOD_ID, "charged")
    val chargedSoundEvent: SoundEvent = SoundEvent.of(chargedId)

    private val dischargedId = Identifier.of(MOD_ID, "discharged")
    val dischargedSoundEvent: SoundEvent = SoundEvent.of(dischargedId)

    fun initialize() {
        Registry.register(Registries.SOUND_EVENT, chargedId, chargedSoundEvent)
        Registry.register(Registries.SOUND_EVENT, dischargedId, dischargedSoundEvent)
    }
}