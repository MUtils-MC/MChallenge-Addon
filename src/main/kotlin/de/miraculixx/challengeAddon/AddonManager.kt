package de.miraculixx.challengeAddon

import de.miraculixx.challenge.api.MChallengeAPI
import de.miraculixx.challenge.api.settings.ChallengeData
import de.miraculixx.challengeAddon.utils.AddonMod
import de.miraculixx.challengeAddon.utils.cmp
import de.miraculixx.challengeAddon.utils.plus
import de.miraculixx.challengeAddon.utils.prefix
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.axay.kspigot.extensions.console
import net.kyori.adventure.text.format.NamedTextColor
import java.io.File

/**
 * The core of your addon to communicate with the MUtils API
 */
object AddonManager {
    /**
     * Location of your configuration files. Those settings use the serializer from ChallengeSetting to save all data
     */
    private val configFile = File("${PluginInstance.dataFolder.path}/settings.json")

    /**
     * A mutable list of all your mods. Settings can be changed by users at any time, but they should only change your logic on start not at runtime
     */
    private val settings: MutableMap<AddonMod, ChallengeData> = mutableMapOf()

    /**
     * @param mod the target [AddonMod]
     * @return all currently available settings for the [mod] or the default settings if nothing is saved
     */
    fun getSettings(mod: AddonMod): ChallengeData {
        return settings.getOrPut(mod) { mod.getDefaultSetting() }
    }

    /**
     * Loads all data from disk and tries to connect to MUtils API. On successfully connection, all mod data is added to MUtils
     *
     * Should only be called at the server start/addon start. User changes are directly applied to the given [ChallengeData]
     */
    fun loadMods() {
        // Try to connect to MUtils API
        val api = MChallengeAPI.instance
        if (api == null) {
            console.sendMessage(prefix + cmp("Failed to connect with MUtils-Challenge API!", NamedTextColor.RED))
            return
        }

        // Try to load all settings data
        if (configFile.exists()) {
            try {
                settings.putAll(Json.decodeFromString<Map<AddonMod, ChallengeData>>(configFile.readText()))
            } catch (e: Exception) {
                console.sendMessage(prefix + cmp("Failed to read settings!"))
                console.sendMessage(prefix + cmp(e.message ?: "Reason Unknown"))
            }
        }

        // Add all mods to MUtils
        AddonMod.values().forEach { mod ->
            val prodData = api.addChallenge(mod.uuid, mod.getModData())
            if (prodData == null) {
                console.sendMessage(prefix + cmp("Failed to inject ${mod.name} to MChallenge!"))
                return@forEach
            }
            settings[mod] = prodData.data
        }

        // Finished
        console.sendMessage(prefix + cmp("Successfully hooked in!"))
    }

    /**
     * Should only be called at the server stop/addon stop. Saves all mod settings to disk
     */
    fun saveMods() {
        configFile.writeText(Json.encodeToString(settings))
    }
}