package de.md5lukas.kinv

class GUIPattern(vararg pattern: String) {

    private val pattern: Array<String>

    val rows: Int
    val columns: Int

    init {
        columns = pattern[0].length
        this.pattern = Array(pattern.size) {
            val currentLine = pattern[it]

            if (currentLine.length != columns) {
                throw IllegalArgumentException("The line $it has a different length (${currentLine.length}) than the first line ($columns).")
            }

            currentLine
        }
        rows = pattern.size
    }

    operator fun get(row: Int, column: Int): Char = pattern[row][column]
}