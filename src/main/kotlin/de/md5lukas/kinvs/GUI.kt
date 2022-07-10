package de.md5lukas.kinvs

import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

/**
 * A GUI instance represents a single Bukkit inventory with fixed title and size.
 *
 * The content can be changed by swapping out the [activePage] with another one or altering its content.
 *
 * By default, the inventory stays closed and must be opened by calling [open]
 *
 * @property player The player the GUI should be visible for
 * @param title The title of the inventory
 * @property rows The amount of rows the inventory should have.
 * @constructor Creates an empty GUI
 */
class GUI(
    private val player: Player,
    title: String,
    val rows: Int
) {
    private val inventory = GUIManager.plugin.server.createInventory(null, rows * 9, title)

    /**
     * Callback that gets called when the inventory is closed
     */
    var onClose: (() -> Unit)? = null

    /**
     * The active page that is currently displayed by the GUI.
     *
     * After changing the active page an update must be forced by calling [update]
     */
    var activePage: GUIPage = GUIPage(this)

    /**
     * Calling this method refreshed the inventory by applying the [activePage] to it.
     */
    fun update() {
        activePage.grid.forEachIndexed { row, rows ->
            rows.forEachIndexed { column, guiContent ->
                inventory.setItem((row * 9 + column), guiContent.item)
            }
        }
    }

    /**
     * To (re)open the inventory this function must be called.
     */
    fun open() {
        GUIManager.registerGUI(player, this)
        player.openInventory(inventory)
    }

    /**
     * This function gets called by the KInvs listener when a click has been registered in this inventory
     */
    internal fun onClick(event: InventoryClickEvent, position: InventoryPosition) {
        activePage.grid[position.row][position.column].click(this, activePage, event)
    }

    /**
     * This function gets called by the KInvs listener when the inventory gets closed
     */
    internal fun onClose() {
        onClose?.invoke()
    }
}