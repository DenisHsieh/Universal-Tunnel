package io.denix.project.universaltunnel.ui.home

import android.app.Application
import android.content.res.AssetManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.denix.project.universaltunnel.data.user.model.UserDao

class HomeFragmentViewModelFactory(
    val application: Application,
    val userId: Int,
    val userDao: UserDao,
    private val assetManager: AssetManager
) : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeFragmentViewModel(application, userId, userDao, assetManager) as T
    }
}