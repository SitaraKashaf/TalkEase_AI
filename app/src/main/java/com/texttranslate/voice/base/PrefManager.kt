package com.texttranslate.voice.base

import android.content.Context
import android.content.SharedPreferences

class PrefManager(_context: Context) {

    var pref: SharedPreferences = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    var editor: SharedPreferences.Editor = pref.edit()

    fun setBoolean(dnd: String?, checked: Boolean) {
        editor.putBoolean(dnd, checked)
        editor.commit()
    }

    fun getBoolean(key: String?): Boolean {
        return pref.getBoolean(key, false)
    }

    companion object {
        private const val PREF_NAME = "TEXT-VOICE-TRANSLATOR"
    }
}
