package com.example.devbytes.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

import com.example.devbytes.database.getDatabase
import com.example.devbytes.repository.VideosRepository
import retrofit2.HttpException
import timber.log.Timber

class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    /**
     * A coroutine-friendly method to do your work.
     */
    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = VideosRepository(database)
        return try {
            repository.refreshVideos()
            Timber.d("asdasdasdasdasdasd")
            Result.success()
        } catch (e: HttpException) {
            Timber.d("nope_dasdadasdasdsad")
            Result.retry()
        }
    }
}