package ru.meseen.dev.mtptest.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseAndroidViewModel(application: Application) : AndroidViewModel(application) {

    protected val editableLogs = MutableStateFlow("")
    val logs: StateFlow<String> = editableLogs

    protected val context: Context get() = getApplication<Application>()

    protected fun MutableStateFlow<String>.appstart(text: String) {
        editableLogs.value = text + "\n" + editableLogs.value
    }

}