package org.wdfeer.infinity_hoe.enchantment.unique.uncommon

import net.bettercombat.api.WeaponAttributes
import net.bettercombat.api.WeaponAttributesHelper
import org.wdfeer.infinity_hoe.enchantment.bc.BetterCombatEnchantment
import java.io.StringReader

class Reaper : BetterCombatEnchantment() {
    override fun getPath(): String = "reaper"

    override val attributes: WeaponAttributes
        get() = attr

    // TODO: learn to read json files
    private val attr = WeaponAttributesHelper.decode(StringReader("{\"parent\":\"\",\"attributes\":{\"attack_range\":3.5,\"pose\":\"bettercombat:pose_two_handed_scythe\",\"two_handed\":true,\"category\":\"scythe\",\"attacks\":[{\"hitbox\":\"HORIZONTAL_PLANE\",\"damage_multiplier\":1.0,\"angle\":150.0,\"upswing\":0.5,\"animation\":\"bettercombat:two_handed_slash_horizontal_right\",\"swing_sound\":{\"id\":\"bettercombat:scythe_slash\",\"volume\":1.0,\"pitch\":1.0,\"randomness\":0.1}},{\"hitbox\":\"HORIZONTAL_PLANE\",\"damage_multiplier\":1.0,\"angle\":150.0,\"upswing\":0.5,\"animation\":\"bettercombat:two_handed_slash_horizontal_left\",\"swing_sound\":{\"id\":\"bettercombat:scythe_slash\",\"volume\":1.0,\"pitch\":1.0,\"randomness\":0.1}}]}}"))
        .attributes()
}