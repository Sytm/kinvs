package de.md5lukas.kinvs

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.server.PluginDisableEvent
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Level

internal object GUIManager : Listener {

    val plugin = JavaPlugin.getProvidingPlugin(GUIManager::class.java)

    init {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    @EventHandler
    fun onPluginDisable(e: PluginDisableEvent) {
        if (e.plugin !== plugin) {
            return
        }

        plugin.server.onlinePlayers.forEach { player ->
            player.openInventory.topInventory.ifGui {
                player.closeInventory(InventoryCloseEvent.Reason.CANT_USE)
                it.onClose()
            }
        }
    }

    @EventHandler
    fun onInventoryClose(e: InventoryCloseEvent) {
        e.inventory.ifGui {
            it.onClose()
        }
    }

    @EventHandler
    fun onInventoryDrag(e: InventoryDragEvent) {
        e.view.topInventory.ifGui {
            e.isCancelled = true
        }
    }

    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {
        e.view.topInventory.ifGui {
            e.isCancelled = true

            if (e.clickedInventory == null) {
                return
            }

            if (e.clickedInventory === e.view.bottomInventory) {
                return
            }

            val position = InventoryPosition(
                e.slot / 9,
                e.slot % 9,
            )

            it.onClick(e, position)
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onInventoryDragMonitor(e: InventoryDragEvent) {
        e.view.topInventory.ifGui {
            plugin.logger.log(
                Level.SEVERE,
                "Another plugin has un-cancelled an InventoryDragEvent, breaking KInvs"
            )
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onInventoryClickMonitor(e: InventoryClickEvent) {
        e.view.topInventory.ifGui {
            plugin.logger.log(
                Level.SEVERE,
                "Another plugin has un-cancelled an InventoryClickEvent, breaking KInvs"
            )
        }
    }

    private inline fun Inventory.ifGui(block: (GUI) -> Unit) {
        (holder as? GUI)?.let(block)
    }
}