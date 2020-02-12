package it.kgtg.treasure.hunt.utils

/**
 * Converts char at specified index to Int.
 *
 * @param index the char that should be converted to int
 * @return Int representing char at specified index
 */
fun String.getIntAtIndex(index: Int): Int =
    this[index].toString().toInt()