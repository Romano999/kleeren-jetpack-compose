package nl.romano.kleeren.di

import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nl.romano.kleeren.data.KleerenDatabase
import nl.romano.kleeren.data.UserSearchDatabaseDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNotesDao(kleerenDatabase: KleerenDatabase): UserSearchDatabaseDao =
        kleerenDatabase.userSearchDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): KleerenDatabase =
        Room.databaseBuilder(context, KleerenDatabase::class.java, "kleeren.db")
            .fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideNavController(@ApplicationContext context: Context): NavController = NavHostController(context).apply {
        navigatorProvider.addNavigator(ComposeNavigator())
        navigatorProvider.addNavigator(DialogNavigator())
    }
}
