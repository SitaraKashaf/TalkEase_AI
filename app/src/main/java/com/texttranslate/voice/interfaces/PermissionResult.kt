package com.texttranslate.voice.interfaces

interface PermissionResult {
    fun permissionGranted()
    fun permissionDenied()
    fun permissionForeverDenied()
}
