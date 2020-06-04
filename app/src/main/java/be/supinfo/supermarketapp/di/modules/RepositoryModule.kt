package be.supinfo.supermarketapp.di.modules

import android.app.Application
import be.supinfo.supermarketapp.data.Repository
import be.supinfo.supermarketapp.data.local.AppDatabase
import be.supinfo.supermarketapp.data.local.ProductDao
import be.supinfo.supermarketapp.data.local.RayonDao
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class RepositoryModule(private val baseUrl: String) {
    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesDatabase(context: Application): AppDatabase = AppDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun providesProductDao(database: AppDatabase): ProductDao = database.productDao()

    @Singleton
    @Provides
    fun providesRayonDao(database: AppDatabase): RayonDao = database.rayonDao()

    @Singleton
    @Provides
    fun providesRepository(): Repository = Repository()
}