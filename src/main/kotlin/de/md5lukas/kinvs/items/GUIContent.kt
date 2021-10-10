package de.md5lukas.kinvs.items

import de.md5lukas.kinvs.GUI
import de.md5lukas.kinvs.GUIPage
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

interface GUIContent {

    companion object AIR : GUIContent {
        override fun click(gui: GUI, GUIPage: GUIPage, event: InventoryClickEvent) {}
        override val item: ItemStack = ItemStack(Material.AIR)
    }

    fun click(gui: GUI, GUIPage: GUIPage, event: InventoryClickEvent)

    val item: ItemStack

}