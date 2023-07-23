package com.prior.diciotest.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.prior.diciotest.data.database.DicioDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providesHttpClient(): HttpClient {
        val client = HttpClient(Android){
            install(Logging){
                level = LogLevel.BODY
            }
            install(JsonFeature){
                val json = Json {
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                }
                serializer = KotlinxSerializer(json)
            }
        }

        return client
    }

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): DicioDatabase{
        return Room.databaseBuilder(context, DicioDatabase::class.java, DicioDatabase.NAME).build()
    }

    @Singleton
    @Provides
    fun providesSettingsDao(database: DicioDatabase) = database.settingsDao
}