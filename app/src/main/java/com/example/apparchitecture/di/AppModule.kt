package com.example.apparchitecture.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.apparchitecture.db.NoteDao
import com.example.apparchitecture.db.NoteDatabase
import com.example.apparchitecture.repository.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java, "note_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideNoteDao(database: NoteDatabase): NoteDao {
        return database.noteDao()
    }

    @Singleton
    @Provides
    fun provideRepository(noteDao: NoteDao): NoteRepository {
        return NoteRepository(noteDao)
    }
}
