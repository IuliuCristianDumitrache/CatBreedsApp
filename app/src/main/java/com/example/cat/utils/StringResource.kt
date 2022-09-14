package com.example.cat.utils

import android.content.Context
import java.lang.ref.WeakReference

open class StringResourceProvider(context: Context) : StringResource {

    private val context: WeakReference<Context> = WeakReference(context)

    override fun getString(stringId: Int): String {
        return context.get()?.getString(stringId).toString()
    }

    override fun getString(stringId: Int, vararg formatArgs: String): String {
        return context.get()?.getString(stringId, *formatArgs).toString()
    }

    override fun getStringArray(stringId: Int): Array<String> {
        return context.get()?.resources?.getStringArray(stringId) ?: emptyArray()
    }
}

interface StringResource {
    fun getString(stringId: Int): String
    fun getStringArray(stringId: Int): Array<String>
    fun getString(stringId: Int, vararg formatArgs: String): String
}