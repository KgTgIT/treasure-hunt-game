package it.kgtg.treasure.hunt.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * By inheriting this class your class gets access to [Logger] and logging support.
 * Your class should not extend it directly, rather the companion object of your class should do it, for example:
 * ```kotlin
 * class MyClass {
 *
 *     companion object : Loggable() {
 *         // ...some constants (when used otherwise without the block)
 *     }
 * }
 * ```
 */
abstract class Loggable {

    val log: Logger = LoggerFactory.getLogger(this.javaClass)
}