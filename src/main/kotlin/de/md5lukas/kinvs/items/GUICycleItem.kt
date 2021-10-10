package de.md5lukas.kinvs.items

import de.md5lukas.kinvs.GUI
import de.md5lukas.kinvs.GUIPage
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

open class GUICycleItem<T>(
    private val values: List<Pair<T, ItemStack>>,
    private val onCycle: ((T) -> Unit)?
) : GUIContent {

    private var position = 0

    override fun click(gui: GUI, GUIPage: GUIPage, event: InventoryClickEvent) {
        cycle(event.isShiftClick)
        onCycle?.invoke(currentValue)
    }

    override val item: ItemStack
        get() = values[position].second

    val currentValue: T
        get() = values[position].first

    fun cycle() {
        cycle(false)
    }

    fun cycle(backwards: Boolean) {
        if (backwards) {
            position--
            if (position < 0) {
                position += values.size
            }
        } else {
            position = ++position % values.size
        }
    }
}