package bigboss.team.learnpenguin.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    var usersame: String = ""
    var role: String = ""
    var collection: MutableList<String> = mutableListOf()
}