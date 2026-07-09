package com.texttranslate.voice.model

object Languages {
    @JvmField
    val langsEN = arrayOf(
        "English",
        "Punjabi",
        "Urdu"
    )
    private val langCodesEN = arrayOf(
        "en",
        "pa",
        "ur"
    )

    @JvmStatic
    fun getLangCodeEN(i: Int): String {
        return langCodesEN[i]
    }
}
