package com.reihanalavi.mvpbarclays.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.reihanalavi.mvpbarclays.models.Pasts
import com.reihanalavi.mvpbarclays.models.Teams

@Database(entities = [Teams::class, Pasts::class], version = 1)
abstract class TeamsDatabase: RoomDatabase() {

    abstract fun teamsDao(): TeamsDao
    abstract fun pastsDao(): PastsDao

    companion object {

        @Volatile private var instance: TeamsDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            TeamsDatabase::class.java,
            "teamsdatabase"
        ).build()

    }

}