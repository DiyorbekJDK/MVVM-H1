package org.diyorbek.mvvm_h1.repository

import org.diyorbek.mvvm_h1.database.PhotoDao
import org.diyorbek.mvvm_h1.model.PhotoItem

class PhotoRepository(
    private val dao: PhotoDao
) {

    fun getAllPhotos() = dao.getAllPhotos()

    suspend fun savePhoto(photo: PhotoItem) = dao.savePhoto(photo)

    suspend fun deletePhoto(photo: PhotoItem) = dao.deletePhoto(photo)

    suspend fun clearPhotos() = dao.clearPhotos()

}