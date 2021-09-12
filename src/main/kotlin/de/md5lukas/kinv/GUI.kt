package de.md5lukas.kinv

import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

class GUI(
    private val player: Player,
    title: String,
    val rows: Int
) {
    private val inventory = GUIManager.plugin.server.createInventory(null, rows * 9, title)

    private var onClose: (() -> Unit)? = null

    val activePage: GUIPage = GUIPage(this)

    fun update() {
        activePage.grid.forEachIndexed { row, rows ->
            rows.forEachIndexed { column, guiContent ->
                inventory.setItem((row * 9 + column), guiContent.item)
            }
        }
    }

    fun open() {
        GUIManager.registerGUI(player, this)
        player.openInventory(inventory)
    }

    fun onClick(event: InventoryClickEvent, position: InventoryPosition) {
        activePage.grid[position.row][position.column].click(this, activePage, event)
    }

    fun onClose() {
        onClose?.invoke()
    }
}