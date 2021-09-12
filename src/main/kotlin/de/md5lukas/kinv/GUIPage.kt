package de.md5lukas.kinv

import de.md5lukas.kinv.items.GUIContent
import kotlin.math.min

open class GUIPage(
    gui: GUI
) {

    val rows = gui.rows
    val columns = 9

    open val grid: Array<Array<GUIContent>> = Array(9) {
        Array(gui.rows) {
            GUIContent.AIR
        }
    }

    fun applyPattern(pattern: GUIPattern, vararg mappings: Pair<Char, GUIContent>) = applyPattern(pattern, GUIContent.AIR, *mappings)

    fun applyPattern(pattern: GUIPattern, defaultValue: GUIContent, vararg mappings: Pair<Char, GUIContent>) {
        for (row in 0 until min(rows, pattern.rows)) {
            for (column in 0 until min(columns, pattern.columns)) {
                grid[row][column] = mappings[pattern[row, column]] ?: defaultValue
            }
        }
    }

    private operator fun Array<out Pair<Char, GUIContent>>.get(key: Char): GUIContent? {
        for (pair in this) {
            if (pair.first == key) {
                return pair.second
            }
        }

        return null
    }
}