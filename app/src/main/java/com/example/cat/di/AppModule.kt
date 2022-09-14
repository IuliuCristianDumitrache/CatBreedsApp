package com.example.cat.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.cat.data.AppDatabase
import com.example.cat.data.CatDao
import com.example.cat.data.SessionManager
import com.example.cat.data.remotedatasource.CatLocalDataSource
import com.example.cat.data.remotedatasource.CatRemoteDataSource
import com.example.cat.data.remotedatasource.LoginRemoteDataSource
import com.example.cat.data.repository.CatRepositoryImpl
import com.example.cat.data.repository.LoginRepositoryImpl
import com.example.cat.network.ApiFactory
import com.example.cat.network.ApiService
import com.example.cat.network.exceptions.NetworkExceptionHandler
import com.example.cat.utils.StringResource
import com.example.cat.utils.StringResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideApiService(): ApiService {
        return ApiFactory.getClient()
    }

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
    ) = Room.databaseBuilder(app, AppDatabase::class.java, "cat_database")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideCatDao(appDatabase: AppDatabase): CatDao {
        return appDatabase.catDao()
    }

    @Provides
    fun provideStringResource(@ApplicationContext context: Context): StringResource {
        return StringResourceProvider(context)
    }

    @Provides
    fun provideNetworkExceptionHandler(stringResource: StringResource): NetworkExceptionHandler {
        return NetworkExceptionHandler(stringResource)
    }

    @Provides
    fun provideCatsRemoteDataSource(
        apiService: ApiService
    ): CatRemoteDataSource {
        return CatRemoteDataSource(apiService)
    }

    @Provides
    fun provideLoginRemoteDataSource(
        apiService: ApiService
    ): LoginRemoteDataSource {
        return LoginRemoteDataSource(apiService)
    }

    @Provides
    fun provideCatLocalDataSource(
        catDao: CatDao
    ): CatLocalDataSource {
        return CatLocalDataSource(catDao)
    }

    @Provides
    fun provideLoginRepository(loginRemoteDataSource: LoginRemoteDataSource, sessionManager: SessionManager): LoginRepositoryImpl {
        return LoginRepositoryImpl(loginRemoteDataSource, sessionManager)
    }

    @Provides
    fun provideCatRepository(catRemoteDataSource: CatRemoteDataSource, catLocalDataSource: CatLocalDataSource): CatRepositoryImpl {
        return CatRepositoryImpl(catRemoteDataSource, catLocalDataSource)
    }

    @Provides
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager {
        return SessionManager(provideSharedPreferences(context))
    }

    fun provideSharedPreferences(context: Context): SharedPreferences {
        return EncryptedSharedPreferences.create(
            "AppPreferences",
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}