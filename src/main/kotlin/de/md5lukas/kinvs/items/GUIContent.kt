package de.md5lukas.kinvs.items

import de.md5lukas.kinvs.GUI
import de.md5lukas.kinvs.GUIPage
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * GUIContent is an abstract representation of a slot in an inventory. It has an [item] it contains and a function that gets called on a [click]
 */
interface GUIContent {

    /**
     * Instance of GUIContent that represents an empty slot that does nothing when clicked
     */
    companion object AIR : GUIContent {
        override fun click(gui: GUI, GUIPage: GUIPage, event: InventoryClickEvent) {}
        override val item: ItemStack = ItemStack(Material.AIR)
    }

    /**
     * Function that gets called when the slot the item is in is clicked.
     *
     * After this function is run, the event gets cancelled.
     */
    fun click(gui: GUI, GUIPage: GUIPage, event: InventoryClickEvent)

    /**
     * The item that should appear in the slot.
     */
    val item: ItemStack
}