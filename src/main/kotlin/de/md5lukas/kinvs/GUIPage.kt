package de.md5lukas.kinvs

import de.md5lukas.kinvs.items.GUIContent
import kotlin.math.min
import net.kyori.adventure.text.Component

/**
 * A GUIPage represents the current contents of the inventory at any given moment when the page is
 * the [GUI.activePage]
 *
 * @param gui The GUI to create the page for
 * @constructor Creates a GUI page filled entirely with air
 */
open class GUIPage(gui: GUI) {

  /**
   * The amount of rows this GUI page has. Same as the amount of rows the GUI has provided at
   * creation
   */
  val rows = gui.rows

  /** The amount of columns this GUI page has. */
  val columns = 9

  open val grid: Array<Array<GUIContent>> = Array(rows) { Array(columns) { GUIContent.AIR } }

  open val title: Component?
    get() = null

  /**
   * Applies the provided GUI pattern to this page at the given offset
   *
   * @param pattern The pattern to apply
   * @param rowOffset The offset from the top
   * @param columnOffset The offset from the left
   * @param mappings The mappings from the characters in the pattern to an actual GUIContent
   */
  fun applyPattern(
      pattern: GUIPattern,
      rowOffset: Int,
      columnOffset: Int,
      defaultValue: GUIContent,
      mappings: Map<Char, GUIContent>
  ) {
    for (row in 0 ..< min(rows - rowOffset, pattern.rows)) {
      for (column in 0 ..< min(columns - columnOffset, pattern.columns)) {
        grid[row + rowOffset][column + columnOffset] =
            mappings.getOrDefault(pattern[row, column], defaultValue)
      }
    }
  }

  /**
   * Applies the provided GUI pattern to this page at the given offset
   *
   * @param pattern The pattern to apply
   * @param rowOffset The offset from the top
   * @param columnOffset The offset from the left
   * @param mappings The mappings from the characters in the pattern to an actual GUIContent
   */
  fun applyPattern(
      pattern: GUIPattern,
      rowOffset: Int,
      columnOffset: Int,
      defaultValue: GUIContent,
      vararg mappings: Pair<Char, GUIContent>
  ) = applyPattern(pattern, rowOffset, columnOffset, defaultValue, mapOf(*mappings))
}
