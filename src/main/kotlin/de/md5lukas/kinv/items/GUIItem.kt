package de.md5lukas.kinv.items

import de.md5lukas.kinv.GUI
import de.md5lukas.kinv.GUIPage
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.util.*

class GUIItem(
    override val item: ItemStack,
    private val onClick: ((InventoryClickEvent) -> Unit)?
) : GUIContent {

    override fun click(gui: GUI, GUIPage: GUIPage, event: InventoryClickEvent) {
        onClick?.invoke(event)
    }

}