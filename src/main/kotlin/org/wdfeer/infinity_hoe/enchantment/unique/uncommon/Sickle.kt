package org.wdfeer.infinity_hoe.enchantment.unique.uncommon

import net.bettercombat.api.WeaponAttributes
import net.bettercombat.api.WeaponAttributesHelper
import net.minecraft.entity.EntityGroup
import org.wdfeer.infinity_hoe.enchantment.bc.BetterCombatEnchantment
import java.io.StringReader

class Sickle : BetterCombatEnchantment() {
    override fun getPath(): String = "sickle"

    override fun getAttackDamage(level: Int, group: EntityGroup?): Float { return 0.5f }

    override val attributes: WeaponAttributes
        get() = attr

    // TODO: learn to read json files
    private val attr = WeaponAttributesHelper.decode(StringReader("{\"parent\":\"\",\"attributes\":{\"attack_range\":2.5,\"two_handed\":false,\"category\":\"sickle\",\"attacks\":[{\"hitbox\":\"HORIZONTAL_PLANE\",\"damage_multiplier\":0.9,\"angle\":120.0,\"upswing\":0.5,\"animation\":\"bettercombat:one_handed_slash_horizontal_right\",\"swing_sound\":{\"id\":\"bettercombat:sickle_slash\",\"volume\":1.0,\"pitch\":1.0,\"randomness\":0.1}},{\"hitbox\":\"HORIZONTAL_PLANE\",\"damage_multiplier\":0.9,\"angle\":120.0,\"upswing\":0.5,\"animation\":\"bettercombat:one_handed_slash_horizontal_left\",\"swing_sound\":{\"id\":\"bettercombat:sickle_slash\",\"volume\":1.0,\"pitch\":1.0,\"randomness\":0.1}},{\"conditions\":[\"DUAL_WIELDING_SAME_CATEGORY\",\"MAIN_HAND_ONLY\"],\"hitbox\":\"FORWARD_BOX\",\"damage_multiplier\":1.2,\"angle\":0.0,\"upswing\":0.5,\"animation\":\"bettercombat:dual_handed_slash_cross\",\"swing_sound\":{\"id\":\"bettercombat:sickle_slash\",\"volume\":1.0,\"pitch\":0.8,\"randomness\":0.1}},{\"conditions\":[\"DUAL_WIELDING_SAME_CATEGORY\",\"OFF_HAND_ONLY\"],\"hitbox\":\"FORWARD_BOX\",\"damage_multiplier\":1.3,\"angle\":0.0,\"upswing\":0.5,\"animation\":\"bettercombat:dual_handed_slash_uncross\",\"swing_sound\":{\"id\":\"bettercombat:sickle_slash\",\"volume\":1.0,\"pitch\":1.2,\"randomness\":0.1}}]}}"))
        .attributes()
}