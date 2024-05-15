package com.jkontodos.cabifystore.data.di

import android.content.Context
import android.content.SharedPreferences
import com.jkontodos.cabifystore.data.server.StoreDataSource
import com.jkontodos.cabifystore.data.sharedpreferences.SharedPreferencesDataSource
import com.jkontodos.cabifystore.data.source.LocalDataSource
import com.jkontodos.cabifystore.data.source.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    /** * Responsible for internal calls to shared preferences. */
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("cabify_store_pref", Context.MODE_PRIVATE)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsDataModule {

    /** * Module responsible for internal calls from the app using shared preferences. */
    @Binds
    abstract fun bindsLocalDataSource(sharedPreferencesDataSource: SharedPreferencesDataSource): LocalDataSource

    /** * Module responsible for external calls from the app using Retrofit. */
    @Binds
    abstract fun bindRemoteDataSource(storeDataSource: StoreDataSource): RemoteDataSource
}