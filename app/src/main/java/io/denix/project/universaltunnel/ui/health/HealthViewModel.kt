package io.denix.project.universaltunnel.ui.health

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HealthViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Upcoming...(Health)"
    }
    val text: LiveData<String> = _text
}