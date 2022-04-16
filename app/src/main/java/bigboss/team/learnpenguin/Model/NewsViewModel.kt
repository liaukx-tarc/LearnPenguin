package bigboss.team.learnpenguin.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewsViewModel : ViewModel() {
    private val _isLoad = MutableLiveData<Boolean>()
    val isLoad: LiveData<Boolean> get() = _isLoad

    fun setBool(bool: Boolean) {
        _isLoad.value = bool
    }
}
