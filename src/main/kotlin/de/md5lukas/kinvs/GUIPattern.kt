package de.md5lukas.kinvs

/**
 * A GUI pattern allows you to design inventories with characters visually instead of manually picking rows and columns.
 *
 * For example a three row GUI could be created like this:
 * ```
 * GUIPattern(
 *   "_________",
 *   "__a_b_c__",
 *   "_________",
 * )
 * ```
 * Where _ is the background, and a, b and c are some control elements.
 *
 *
 * @param pattern The pattern to use
 * @throws IllegalArgumentException If any line in the pattern does not match the length of the first line.
 * @constructor Creates a new pattern based on the lines provided
 */
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

    /**
     * Get the character at the given coordinates from the pattern
     */
    operator fun get(row: Int, column: Int): Char = pattern[row][column]
}