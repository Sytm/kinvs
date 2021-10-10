package de.md5lukas.kinvs

import de.md5lukas.kinvs.items.GUIContent
import kotlin.math.min

open class GUIPage(
    gui: GUI
) {

    val rows = gui.rows
    val columns = 9

    open val grid: Array<Array<GUIContent>> = Array(rows) {
        Array(columns) {
            GUIContent.AIR
        }
    }

    fun applyPattern(pattern: GUIPattern, rowOffset: Int, columnOffset: Int, defaultValue: GUIContent, vararg mappings: Pair<Char, GUIContent>) {
        for (row in 0 until min(rows - rowOffset, pattern.rows)) {
            for (column in 0 until min(columns - columnOffset, pattern.columns)) {
                grid[row + rowOffset][column + columnOffset] = mappings[pattern[row, column]] ?: defaultValue
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