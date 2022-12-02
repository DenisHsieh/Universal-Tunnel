package io.denix.project.universaltunnel.ui.media

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MediaViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Coming soon...(Media)"
    }
    val text: LiveData<String> = _text
}