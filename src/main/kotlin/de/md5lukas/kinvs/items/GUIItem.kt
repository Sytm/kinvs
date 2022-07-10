package de.md5lukas.kinvs.items

import de.md5lukas.kinvs.GUI
import de.md5lukas.kinvs.GUIPage
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * Most basic functional implementation for [GUIContent] with a fixed item and optional call back on click.
 *
 * @param item The item that should appear in the slot.
 * @param onClick The callback that gets called on a click event. Event is always cancelled.
 * @constructor Creates an instance of the GUIItem with an item and click callback.
 */
class GUIItem(
    override val item: ItemStack,
    private val onClick: ((InventoryClickEvent) -> Unit)?
) : GUIContent {

    /**
     * Creates an instance of the GUIItem only with an item.
     *
     * @param item The item that should appear in the slot.
     */
    constructor(item: ItemStack): this(item, null)

    override fun click(gui: GUI, GUIPage: GUIPage, event: InventoryClickEvent) {
        onClick?.invoke(event)
    }

}