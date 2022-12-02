package io.denix.project.universaltunnel.ui.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NoteViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Coming soon...(Note)"
    }
    val text: LiveData<String> = _text
}