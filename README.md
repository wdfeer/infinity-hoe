This description is getting big, I will need to make a wiki at some point...

# Requirements
- [Fabric API](https://modrinth.com/mod/fabric-api)
- [Fabric Language Kotlin](https://modrinth.com/mod/fabric-language-kotlin)

# Enchantments

## Regular Enchantments

Common enchantments:
- **Reset**: right click to untill farmland. Can be used with _Infinity_ for mass untill. While in inventory, you may not break farmland when jumping or falling on it.
- **Disseminate**: hoe automatically plants seeds from the inventory or the other hand upon harvesting a crop or tilling. Can be triggered by _Cascade_ and _Infinity_.
- **Cascade (III)**: breaking a mature crop breaks fully-grown crops of the same type. Level increases the number of crops to be harvested.
- **Infinity (III)**: tilling tills nearby blocks of the same type. Level increases the number of blocks to be tilled.

Uncommon enchantments:
- **Calcium Burst (III)**: automatically use available bone meal on adjacent crops upon harvest.
- **Dryad's Blessing (III)**: apply a bone meal effect to nearby crops every 10 seconds while held, at the cost of durability. Level increases the number of affected crops. Frequency increases if you have the Regeneration effect.
- **Rejuvenation**: you and nearby animals gain Regeneration upon harvesting a mature crop for 2 seconds. Refreshes and stacks up to Regeneration X.
- **Miner's Harvest**: gain a chance to double your harvest which increases with Efficiency and Fortune enchantments or Haste and Luck effects.
- **Stand United (III)**: you and nearby players gain Resistance on harvest, stacking in duration. Level increases both maximum and gained duration.
- **Growing Capital**: only available if [Numismatic Overhaul](https://modrinth.com/mod/numismatic-overhaul) is installed. Mature crops sometimes drop a bronze or silver coin on harvest.

Rare enchantments:
- **Decompose (III)**: consume nearby compostable items on the ground to repair the hoe while held. Can also refill charge enchantments if applicable. Incompatible with Mending. Level increases speed.
- **Soul Siphon**: nurture nearby crops similarly to _Dryad's Blessing_, but your max hp is temporarily reduced to affect more crops at once. Higher frequency than _Dryad's Blessing_, but not affected by Regeneration. While not held, recover slowly. Incompatible with _Dryad's Blessing_ and _Pestilence_.
- **Pestilence**: whilst held, gain Infested, silverfish don't attack you unprovoked, and you nurture nearby crops if there are silverfish nearby. Crop nurturing power scales with the number of nearby silverfish. Incompatible with _Dryad's Blessing_ or _Soul Siphon_.
- **Experience (V)**: mature crops drop xp. Level increases amount.

## Specialist Enchantments
Specialists are rare, mutually incompatible enchantments that give you a 20% chance of double harvest on a specific crop type.

Available types:

- Beetroot Specialist
- Carrot Specialist
- Potato Specialist
- Wheat Specialist

## Combat Enchantments

Uncommon:
- **Reaper**: only available if [Better Combat](https://modrinth.com/mod/better-combat) is installed and allows the hoe to use scythe attacks.
- **Crescent**: only available if [Better Combat](https://modrinth.com/mod/better-combat) is installed and lets the hoe use sickle attacks. Also gives +0.5 damage to compensate for lower range.
- **Rebound**: only available if [Better Combat](https://modrinth.com/mod/better-combat) and [Simply Swords](https://modrinth.com/mod/simply-swords) is installed and allows the hoe to use chakram attacks. -0.5 damage to compensate for higher range.

Rare:
- **Equinox**: gain Strength with stacking duration on harvest, trigger a _Dryad's Blessing_ effect upon a melee hit. Triggerable by _Mystic Blade_ and _Pesticide_. Incompatible with _Dryad's Blessing_, _Soul Siphon_, _Rejuvenation_ and _Stand United_.
- **Mystic Blade**: damage nearby monsters on a melee hit. Damage scales with number and levels of enchantments, 0.1 damage per level. Incompatible with _Pesticide_.
- **Pesticide (V)**: tilling a block or breaking a fully-grown crop damages nearby monsters. Damage increases with level. Triggerable by _Cascade_ and _Infinity_. Incompatible with _Mystic Blade_.

## Charge Enchantments

Charge enchantments have charge that is gained upon harvesting mature crops and is shown in the tooltip. Many of them allow you to manually use charge to unleash its effect by right clicking.

Manual usage:
- **Leap**: Rare. Use charge to gain velocity in the direction you are looking.
- **Fleeting**: Rare. Use charge to gain Speed I for 30 seconds. Stacks up to 2 hours.
- **Demolition**: Very rare. Use charge to throw primed tnt.
- **Blazing (II)**: Very rare. Use charge to launch blaze fireballs. Can store 64 fireballs at level II.
- **Fireblast (III)**: Very rare. Use to launch a ghast fireball that splits into multiple ghast fireballs after travelling 8 blocks. Level increases explosion power of the initial fireball and number of child fireballs.
- **Poison Nova**: Very rare. Use all charge to instantly damage and give Poison to all non-animal entities in the vicinity, user excluded. Range, duration and power scales with used charge.
- **Frost Nova**: Very rare. Use all charge to instantly damage, freeze and give Slowness to all non-animal entities in the vicinity, user excluded. Range, duration and power scales with used charge.
- **Healing Nova**: Very rare. Use all charge to give Regeneration to all non-monster entities nearby. Range, duration and power scales with used charge.
- **Luna Dial**: Very rare. Use charge to teleport to your position up to 10 seconds ago.
- **Cursed Forge**: Very rare. Use charge to upgrade a non-cursed tool in the other hand to the next material level, e.g.: iron -> diamond, and give it a Curse of Vanishing.
- **Blessed Forge**: Very rare. Use charge to upgrade a tool in the other hand to the next material level, e.g.: iron -> diamond, and give it one of 3 Blessings: *Dryad's Blessing*, *Animal Blessing* or *Miracle Blessing* - even if it's not a hoe.
    - *Animal Blessing*: exclusive to *Blessed Forge*. While held, reduces the timer between breeding animals.
    - *Miracle Blessing*: exclusive to *Blessed Forge*. While held, sometimes gain Absorption, Resistance or Haste for 7 seconds. Rarely, also heal and gain saturation.

Automatical usage:
- **Demeter's Aegis**: Rare. While held, charge may be automatically used to provide damage immunity before taking damage. Guaranteed to proc if the damage >= 3.
- **Demeter's Grace**: Rare. While held, charge may be randomly used to heal its user after taking damage. Healing amount scales with incoming damage.
- **Demeter's Wrath**: Rare. While held, charge is used on melee hits to deal 5 extra magic damage.
- **Fungus Enchanter**: Rare. Starts at 6000 charge. Every second, decrement charge by 1. Increase discharging speed by 1 for each shroomy block nearby. Once charge reaches 0, remove *Fungus Enchanter* and put *Amanita Muscaria* or *Amethyst Laccaria* on a random tool or armor piece in inventory.
    - *Amanita Muscaria*: exclusive to *Fungus Enchanter*. While in inventory, apply poison to a nearby monster with stacking duration. Doesn't stack when multiple items have this enchantment.
    - *Amethyst Laccaria*: exclusive to *Fungus Enchanter*. While in inventory, very slowly heal the player. Stacks with diminishing returns.
    - *Cobalt Pleurotus*: exclusive to *Fungus Enchanter*. While in inventory, you move 1.5% faster. Doesn't stack.
    - *Panellus Stipticus*: exclusive to *Fungus Enchanter*. While in inventory, a single nearby brewing stand works at 3x speed. Doesn't stack.
- **Equivalent Exchange**: Very rare. While held, charge is used to transform items in inventory:
    - 3 Charge: 4 Gravel to 4 Sand
    - 5 Charge: 2 Arrow to 2 Spectral Arrow
    - 6 Charge: 1 Poisonous Potato to 1 Potato
    - 6 Charge: 1 Rotten Flesh to 1 Leather
    - 8 Charge: 1 String to 1 Cobweb
    - 15 Charge: 8 Copper Ore to 1 Gold Ore
    - 20 Charge: 8 Copper Ingot to 1 Gold Ingot
    - 20 Charge: 16 End Stone to 1 Ender Pearl
    - 30 Charge: 8 Charcoal to 8 Coal
    - 80 Charge: 1 Blaze Powder to 1 Blaze Rod
    - 100 Charge: 64 Coal to 1 Diamond
    - 200 Charge: 5 Iron Block to 64 Iron Ingot
    - 777 Charge: 1 Golden Apple to 1 Enchanted Golden Apple


## Automata Enchantment

**Automata** is a very rare enchantment allowing the hoe to function on its own when thrown as an item, harvesting mature crops within a 5x5 area at the cost of durability. Sets the hoe to never despawn while thrown. _Incompatible with all other enchantments unless stated otherwise._

Allowed enchantments:

- Disseminate: allows the hoe to replant crops using seeds found within 3 blocks around it.
- Decompose: same effect as when held by a player - consumes compostable items to mend itself.
- Unbreaking: reduces durability damage as usual.

# Mod Integration
- [Better Combat](https://modrinth.com/mod/better-combat) - For _Reaper_, _Crescent_ to be loaded
- [Simply Swords](https://modrinth.com/mod/simply-swords) - For _Rebound_ to be loaded (also requires Better Combat); _Cursed Forge_ and _Blessed Forge_ can upgrade its swords.
- [Numismatic Overhaul](https://modrinth.com/mod/numismatic-overhaul) - for _Growing Capital_ to be loaded
- [Contagion](https://modrinth.com/mod/contagion) - For _Rejuvenation_ to rarely give immunity 
