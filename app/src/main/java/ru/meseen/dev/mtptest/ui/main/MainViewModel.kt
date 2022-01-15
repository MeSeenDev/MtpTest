package ru.meseen.dev.mtptest.ui.main

import android.app.Application
import android.os.Environment
import android.util.Log
import ru.meseen.dev.mtptest.base.BaseAndroidViewModel
import ru.meseen.dev.mtptest.utils.initMediaScan
import java.io.File


class MainViewModel(application: Application) : BaseAndroidViewModel(application) {

    private val extPathDir = Environment.getExternalStorageDirectory().absolutePath
    private val dirName = "$extPathDir/#fima"
    private val fileName = "/#funfunfun.ass"

    private val TAG = "MainViewModel"

    fun createFile() {
        editableLogs.appstart("Запуск Создания Файла")
        File(extPathDir + fileName).createFileImpl()
    }

    fun createDir() {
        editableLogs.appstart("Запуск Создания Дирректории")
        File(dirName).createDirImpl()
    }

    fun createDirWithFile() {
        editableLogs.appstart("Запуск Создания Дирректории и Файла")
        File(dirName).createDirImpl()
        File(dirName+fileName).createFileImpl()
    }

    fun scan() {
        editableLogs.appstart("Запуск Сканирования")
        context.initMediaScan(dirName, dirName + fileName)
    }

    fun clearLog() {
        editableLogs.value = ""
    }

    fun clearFiles() {
        editableLogs.appstart("Удаление файлов")
        File(extPathDir + fileName).deleteAndNotify()
        File(dirName).deleteAndNotify()
    }

    private fun File.deleteAndNotify() {
        deleteRecursively().also { isDeleted ->
            if (isDeleted) {
                context.initMediaScan(this)
            }
            editableLogs.appstart("Is File: $name exists ${exists()}")
        }
    }

    private fun File.createFileImpl() {
        Log.wtf(TAG, "createFileImpl: " +absolutePath )
        if (!exists()) {
            createNewFile().also { isCreated ->
                editableLogs.appstart("is File: $name created $isCreated")
                if (isCreated) {
                    editableLogs.appstart("path: $absolutePath")
                    context.initMediaScan(this)
                }
            }
        } else {
            editableLogs.appstart("File: $name already exists")
        }
    }

    private fun File.createDirImpl() {
        if (!exists()) {
            mkdir().also { isCreated ->
                editableLogs.appstart("is DIR: $name created $isCreated")
                if (isCreated) {
                    editableLogs.appstart("path: $absolutePath")
                    context.initMediaScan(this)
                }
            }
        } else {
            editableLogs.appstart("DIR: $name already exists")
        }
    }
}
