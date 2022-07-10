package de.md5lukas.kinvs.items

import de.md5lukas.kinvs.GUI
import de.md5lukas.kinvs.GUIPage
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * The GUICycleItem is a more advanced implementation of the GUIContent that allows the creation of toggle-switches or multiple-choice selection.
 *
 * On a normal click the selection cycles forward, on a shift-click the selection cycles backwards.
 *
 * To each possible internal value [T] a different [ItemStack] can be shown.
 *
 * @param T The type of the internal value
 * @property values List of pairs of internal value and displayed ItemStack
 * @property onCycle Callback that gets called with the new value when it changes
 */
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

    /**
     * The current internal value matching the current visible [item]
     */
    val currentValue: T
        get() = values[position].first

    /**
     * Programmatically cycle the selection forward.
     */
    fun cycle() {
        cycle(false)
    }

    /**
     * Programmatically cycle the selection in the desired direction.
     *
     * @param backwards `true` if the cycle direction should be reversed
     */
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