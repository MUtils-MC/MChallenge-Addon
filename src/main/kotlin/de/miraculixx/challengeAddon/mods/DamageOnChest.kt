package de.miraculixx.challengeAddon.mods

import de.miraculixx.challenge.api.modules.challenges.Challenge
import de.miraculixx.challengeAddon.AddonManager
import de.miraculixx.challengeAddon.utils.AddonMod
import net.axay.kspigot.event.listen
import net.axay.kspigot.event.register
import net.axay.kspigot.event.unregister
import org.bukkit.Material
import org.bukkit.event.player.PlayerInteractEvent

/**
 * Logic for the [AddonMod.DAMAGE_ON_CHEST_CLICK] mod.
 * @see Challenge
 */
class DamageOnChest : Challenge {
    /**
     * The damage value. Initialized with 2 as default but should always be checked on [start]
     */
    private var damage: Int = 2

    override fun start(): Boolean {
        // Refresh all mod settings and apply them
        val settings = AddonManager.getSettings(AddonMod.DAMAGE_ON_CHEST_CLICK).settings
        damage = settings["damage"]?.toInt()?.getValue() ?: 2

        // Only return false on critical errors. This will prevent all mods to start
        return true
    }

    override fun register() {
        // Register our listener
        onChestClick.register()
    }

    override fun unregister() {
        // Unregister our listener
        onChestClick.unregister()
    }

    /**
     * We use KSpigot to easily implement our listeners with [listen].
     *
     * **IMPORTANT**
     * - Set the listen state "register" to false, to prevent preloading the listener!
     * - If you not use KSpigot for any reason, use a local state variable that toggles on and off in [register] and [unregister] and check the state at the start of your listener
     */
    private val onChestClick = listen<PlayerInteractEvent>(register = false) {
        val block = it.clickedBlock ?: return@listen
        if (block.type != Material.CHEST) return@listen
        it.player.damage(damage.toDouble())
    }
}