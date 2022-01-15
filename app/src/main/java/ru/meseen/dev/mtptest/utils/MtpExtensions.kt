package ru.meseen.dev.mtptest.utils

import android.content.Context
import android.media.MediaScannerConnection
import java.io.File


private const val TAG = "MtpUtils"


fun Context.initMediaScan(file: File) {
    MediaScannerConnection.scanFile(this, arrayOf(file.absolutePath), null, null)
}
fun Context.initMediaScan(vararg paths: String) {
    MediaScannerConnection.scanFile(this, paths, null, null)
}