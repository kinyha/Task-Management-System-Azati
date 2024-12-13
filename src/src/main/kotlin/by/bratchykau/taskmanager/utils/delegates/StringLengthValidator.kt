package by.bratchykau.taskmanager.utils.delegates

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class StringLengthValidator(
    private val minLength: Int,
    private val maxLength: Int,
    private val fieldName: String
) : ReadWriteProperty<Any?, String> {
    private var value: String = ""

    override fun getValue(thisRef: Any?, property: KProperty<*>): String = value

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        require(value.length in minLength..maxLength) {
            "$fieldName must be between $minLength and $maxLength characters"
        }
        this.value = value
    }
}

class FutureDateValidator<T>(
    private val isAfterNow: (T) -> Boolean,
    private val fieldName: String
) : ReadWriteProperty<Any?, T?> {
    private var value: T? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): T? = value

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        value?.let {
            require(isAfterNow(it)) { "$fieldName must be in the future" }
        }
        this.value = value
    }
}

// Factory functions for easier usage
fun stringLength(minLength: Int, maxLength: Int, fieldName: String) =
    StringLengthValidator(minLength, maxLength, fieldName)

fun <T> futureDate(isAfterNow: (T) -> Boolean, fieldName: String) =
    FutureDateValidator(isAfterNow, fieldName)