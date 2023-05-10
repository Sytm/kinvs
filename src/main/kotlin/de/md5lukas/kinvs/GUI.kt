package de.md5lukas.kinvs

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

/**
 * A GUI instance represents a single Bukkit inventory with fixed title and size.
 *
 * The content can be changed by swapping out the [activePage] with another one or altering its content.
 *
 * By default, the inventory stays closed and must be opened by calling [open]
 *
 * @property player The player the GUI should be visible for
 * @property rows The amount of rows this GUI has
 * @param title The title of the GUI
 * @constructor Creates an empty GUI
 */
class GUI(
    private val player: Player,
    val rows: Int,
    title: Component,
) : InventoryHolder {

    private val inventory = GUIManager.plugin.server.createInventory(this, rows * 9, title)

    override fun getInventory(): Inventory = inventory

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