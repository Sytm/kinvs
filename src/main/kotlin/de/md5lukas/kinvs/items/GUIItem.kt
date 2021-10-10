package de.md5lukas.kinvs.items

import de.md5lukas.kinvs.GUI
import de.md5lukas.kinvs.GUIPage
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class GUIItem(
    override val item: ItemStack,
    private val onClick: ((InventoryClickEvent) -> Unit)?
) : GUIContent {

    constructor(item: ItemStack): this(item, null)

    override fun click(gui: GUI, GUIPage: GUIPage, event: InventoryClickEvent) {
        onClick?.invoke(event)
    }

}