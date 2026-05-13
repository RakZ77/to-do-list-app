package kh.edu.rupp.to_dolistapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kh.edu.rupp.to_dolistapp.models.User
import kh.edu.rupp.to_dolistapp.repositories.UserRepository
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(repository: UserRepository) : ViewModel() {

    // Fixed: non-nullable LiveData<MutableList<User>> — matches repository
    val users: LiveData<MutableList<User>> = repository.users
}
