package de.md5lukas.kinv.items

import de.md5lukas.kinv.GUI
import de.md5lukas.kinv.GUIPage
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.util.*

interface GUIContent {

    companion object AIR : GUIContent {
        override fun click(gui: GUI, GUIPage: GUIPage, event: InventoryClickEvent) {}
        override val item: ItemStack = ItemStack(Material.AIR)
    }

    fun click(gui: GUI, GUIPage: GUIPage, event: InventoryClickEvent)

    val item: ItemStack

}