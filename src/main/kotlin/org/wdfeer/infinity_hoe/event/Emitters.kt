package org.wdfeer.infinity_hoe.event

import org.wdfeer.infinity_hoe.event.emitter.HoeHarvest
import org.wdfeer.infinity_hoe.event.emitter.WorldTicker

object Emitters {
    fun initialize() {
        HoeHarvest.initialize()
        WorldTicker.initialize()
    }
}