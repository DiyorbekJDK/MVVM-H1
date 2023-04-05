package org.diyorbek.mvvm_h1.database

import android.content.Context
import androidx.room.*
import org.diyorbek.mvvm_h1.model.PhotoItem

@Database(entities = [PhotoItem::class], version = 1, exportSchema = false)
abstract class PhotoDatabase : RoomDatabase() {
    abstract val dao: PhotoDao

    companion object {
        private var instance: PhotoDatabase? = null

        operator fun invoke(context: Context) = instance ?: synchronized(Any()) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context): PhotoDatabase {
            return Room.databaseBuilder(
                context,
                PhotoDatabase::class.java,
                "photo.db"
            ).fallbackToDestructiveMigration().build()
        }
    }

}