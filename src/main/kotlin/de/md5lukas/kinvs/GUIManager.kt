package de.md5lukas.kinvs

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryInteractEvent
import org.bukkit.event.inventory.InventoryMoveItemEvent
import org.bukkit.event.server.PluginDisableEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Level

internal object GUIManager : Listener {

    val plugin = JavaPlugin.getProvidingPlugin(GUIManager::class.java)

    init {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    private val inventories: MutableMap<Player, GUI> = mutableMapOf()

    fun registerGUI(player: Player, gui: GUI) {
        inventories[player] = gui
    }

    @EventHandler
    private fun onPluginDisable(e: PluginDisableEvent) {
        if (e.plugin !== plugin) {
            return
        }

        inventories.forEach {
            it.key.closeInventory()
            it.value.onClose()
        }
    }

    @EventHandler
    private fun onInventoryClose(e: InventoryCloseEvent) {
        inventories.remove(e.player)?.onClose()
    }

    @EventHandler
    private fun onInventoryDrag(e: InventoryDragEvent) {
        if (e.whoClicked !in inventories) {
            return
        }

        e.isCancelled = true
    }

    @EventHandler
    private fun onInventoryClick(e: InventoryClickEvent) {
        if (e.whoClicked !in inventories) {
            return
        }

        e.isCancelled = true

        if (e.clickedInventory == null) {
            return
        }

        if (e.clickedInventory === e.view.bottomInventory) {
            return
        }

        val gui = inventories[e.whoClicked]!!

        val position = InventoryPosition(
            e.slot / 9,
            e.slot % 9,
        )

        gui.onClick(e, position)

        e.isCancelled = true
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    private fun onInventoryDragMonitor(e: InventoryDragEvent) {
        if (e.whoClicked !in inventories) {
            return
        }
        plugin.logger.log(
            Level.SEVERE,
            "Another plugin has un-cancelled an InventoryDragEvent, breaking KInvs"
        )
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    private fun onInventoryClickMonitor(e: InventoryClickEvent) {
        if (e.whoClicked !in inventories) {
            return
        }
        plugin.logger.log(
            Level.SEVERE,
            "Another plugin has un-cancelled an InventoryClickEvent, breaking KInvs"
        )
    }
}