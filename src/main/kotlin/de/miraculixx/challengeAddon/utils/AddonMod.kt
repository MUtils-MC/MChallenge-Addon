package de.miraculixx.challengeAddon.utils

import de.miraculixx.challengeAddon.AddonManager
import de.miraculixx.challengeAddon.mods.DamageOnChest
import de.miraculixx.challenge.api.modules.challenges.ChallengeTags
import de.miraculixx.challenge.api.modules.challenges.CustomChallengeData
import de.miraculixx.challenge.api.settings.ChallengeData
import de.miraculixx.challenge.api.settings.ChallengeIntSetting
import de.miraculixx.challenge.api.utils.Icon
import de.miraculixx.challenge.api.utils.IconNaming
import de.miraculixx.challengeAddon.MAddon
import java.util.*

/**
 * All of our addon mods. Each mod is unique by his [uuid] but not bound to it on each startup. It is only important that the [uuid] is the same at all time of one session.
 * @param uuid The unique ID. Don't choose a simple hard coded [uuid], it could conflict with other addons
 */
enum class AddonMod(val uuid: UUID) {
    DAMAGE_ON_CHEST_CLICK(UUID.randomUUID()),
    ;

    /**
     * Holds all mod data. Should only be called once at startup to ship all data to the MUtils API
     * @see AddonManager.loadMods
     */
    fun getModData(): CustomChallengeData {
        return when (this) {
            DAMAGE_ON_CHEST_CLICK -> CustomChallengeData(
                uuid,
                DamageOnChest(),
                AddonManager.getSettings(this),
                Icon("CHEST", naming = IconNaming(cmp("Chest Damage"), listOf(cmp("You get damage on chest"), cmp("interactions")))),
                setOf(ChallengeTags.FUN),
                MAddon.addonName
            )
        }
    }

    /**
     * Holds all settings information. Should only be called on initial startup if no saved settings are present.
     *
     * @see AddonManager.getSettings
     */
    fun getDefaultSetting(): ChallengeData {
        return when (this) {
            DAMAGE_ON_CHEST_CLICK -> ChallengeData(
                mapOf(
                    "damage" to ChallengeIntSetting("BEETROOT", 2, "hp", min = 1, max = 20)
                ),
                mapOf(
                    "damage" to IconNaming(cmp("Damage Amount"), listOf(cmp("The amount of Damage on interaction")))
                ),
            )
        }
    }
}