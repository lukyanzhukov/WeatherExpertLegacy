package com.lukianbat.prefser

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


class PrefserReadWriteProperty(val prefser: Prefser, val key: String) : ReadWriteProperty<Any, String?> {
    override fun getValue(thisRef: Any, property: KProperty<*>): String? {
        return prefser.get(key, String::class.java, null)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String?) {
        if (value != null) {
            prefser.put(key, value)
        } else {
            prefser.remove(key)
        }
    }
}