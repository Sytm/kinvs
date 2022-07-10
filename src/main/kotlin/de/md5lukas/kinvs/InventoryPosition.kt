package de.md5lukas.kinvs

/**
 * Basic class to represent the coordinates in an inventory.
 *
 * @property row The rows from the top
 * @property column The columns from the left
 * @constructor Creates a new InventoryPosition instance
 */
data class InventoryPosition(
    val row: Int,
    val column: Int
)