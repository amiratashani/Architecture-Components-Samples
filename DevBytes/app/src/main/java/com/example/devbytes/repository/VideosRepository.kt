package com.example.devbytes.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.devbytes.database.VideosDatabase
import com.example.devbytes.database.asDomainModel
import com.example.devbytes.domain.Video
import com.example.devbytes.network.Network
import com.example.devbytes.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideosRepository(private val database: VideosDatabase) {


    val videos: LiveData<List<Video>> =
        Transformations.map(database.videoDao.getVideos()) {
            it.asDomainModel()
        }

    suspend fun refreshVideos() {
        withContext(Dispatchers.IO) {
            val playlist = Network.devbytes.getPlaylist().await()
            database.videoDao.insertAll(*playlist.asDatabaseModel())
        }
    }
}