package de.miraculixx.challengeAddon

import net.axay.kspigot.main.KSpigot

/**
 * Entry point for the MChallenge-Addon plugin. We use [KSpigot] to use all KSpigot utilities later
 */
class MAddon : KSpigot() {
    companion object {
        lateinit var INSTANCE: KSpigot
        lateinit var addonName: String
    }

    override fun load() {
        INSTANCE = this
        // Don't change your addons name to something different from your plugin name!
        // Users should be able to easily know the source of a mod.
        @Suppress("DEPRECATION")
        addonName = description.name
    }

    override fun startup() {
        // Load all data on startup
        AddonManager.loadMods()
    }

    override fun shutdown() {
        // Save all data on shutdown
        AddonManager.saveMods()
    }
}

val PluginInstance by lazy { MAddon.INSTANCE }