package be.supinfo.supermarketapp.di.modules

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(val context: Application) {
    @Singleton
    @Provides
    fun providesContext(): Application = this.context

    @Provides
    @Singleton
    fun providesSharedPreferences(): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(providesContext())
}