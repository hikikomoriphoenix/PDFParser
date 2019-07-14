package marabillas.loremar.andpdf.utils

internal var showAndPDFLogs = false
internal var forceHideLogs = false

internal fun logd(message: String) {
    if (showAndPDFLogs) {
        println(message)
    }
}

internal fun loge(message: String, e: Exception? = null) {
    if (showAndPDFLogs) {
        System.err.println(message)
        e?.printStackTrace()
    }
}