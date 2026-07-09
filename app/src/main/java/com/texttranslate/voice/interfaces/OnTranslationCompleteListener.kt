package com.texttranslate.voice.interfaces

interface OnTranslationCompleteListener {
    fun onStartTranslation()
    fun onCompleted(text: String?)
    fun onError(e: Exception?)
}
