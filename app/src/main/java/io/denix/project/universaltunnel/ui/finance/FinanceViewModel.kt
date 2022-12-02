package io.denix.project.universaltunnel.ui.finance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FinanceViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Coming soon...(Finance)"
    }
    val text: LiveData<String> = _text
}