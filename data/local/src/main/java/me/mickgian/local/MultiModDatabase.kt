package me.mickgian.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.mickgian.local.converter.Converters
import me.mickgian.local.dao.UserDao
import me.mickgian.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MultiModDatabase: RoomDatabase() {

    // DAO
    abstract fun userDao(): UserDao

    companion object {

        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, MultiModDatabase::class.java, "MultiMod.db")
                .build()
    }
}