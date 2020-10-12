package com.example.githubpaging.di

import android.content.Context
import androidx.room.Query
import androidx.room.Room
import com.example.githubpaging.BuildConfig
import com.example.githubpaging.api.GithubService
import com.example.githubpaging.api.GithubService.Companion.BASE_URL
import com.example.githubpaging.data.GithubRemoteMediator
import com.example.githubpaging.db.RemoteKeysDao
import com.example.githubpaging.db.RepoDao
import com.example.githubpaging.db.RepoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
object ModuleApp {

    @Provides
    @Singleton
    fun provideRepoDatabase(@ApplicationContext context: Context): RepoDatabase {
        return Room.databaseBuilder(
            context,
            RepoDatabase::class.java,
            "Github.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepoDao(db: RepoDatabase): RepoDao {
        return db.reposDao()
    }

    @Provides
    @Singleton
    fun provideRemoteKeysDao(db: RepoDatabase): RemoteKeysDao {
        return db.remoteKeysDao()
    }



    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class BaseUrl

    @Provides
    @BaseUrl
    fun provideBaseUrl() = BASE_URL


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        } else {
            OkHttpClient.Builder()
                .build()
        }

    }


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, @BaseUrl BASE_URL: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideGithubService(retrofit: Retrofit): GithubService {
        return retrofit.create(GithubService::class.java)
    }

}