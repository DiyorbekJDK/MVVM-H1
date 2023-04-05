package org.diyorbek.mvvm_h1.presenter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.diyorbek.mvvm_h1.database.PhotoDatabase
import org.diyorbek.mvvm_h1.model.PhotoItem
import org.diyorbek.mvvm_h1.repository.PhotoRepository

class MainViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val repository = PhotoRepository(PhotoDatabase.invoke(application).dao)

    fun savePhoto(photo: PhotoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.savePhoto(photo)
        }
    }

    fun deletePhoto(photo: PhotoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePhoto(photo)
        }
    }

    fun getAllNotes() = repository.getAllPhotos()

    fun clearPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearPhotos()
        }
    }
}