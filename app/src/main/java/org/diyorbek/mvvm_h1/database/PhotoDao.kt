package org.diyorbek.mvvm_h1.database

import androidx.lifecycle.LiveData
import androidx.room.*
import org.diyorbek.mvvm_h1.model.PhotoItem

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePhoto(photo: PhotoItem)

    @Delete
    suspend fun deletePhoto(photo: PhotoItem)

    @Query("DELETE FROM PhotoItem")
    suspend fun clearPhotos()

    @Query("SELECT * FROM PhotoItem ORDER BY id DESC")
    fun getAllPhotos(): LiveData<List<PhotoItem>>
}