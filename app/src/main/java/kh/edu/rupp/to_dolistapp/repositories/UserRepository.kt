package kh.edu.rupp.to_dolistapp.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kh.edu.rupp.to_dolistapp.models.User
import kh.edu.rupp.to_dolistapp.models.UserResponse
import kh.edu.rupp.to_dolistapp.services.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val api: UserService) {

    val users: LiveData<MutableList<User>>
        get() {
            val data = MutableLiveData<MutableList<User>>()

            // Fixed: Callback<UserResponse> — no nullable type argument
            api.getUsers()?.enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        // Fixed: users is List<User?> in model — filter nulls out
                        val userList = response.body()!!.users
                            ?.filterNotNull()
                            ?.toMutableList()
                            ?: mutableListOf()
                        data.value = userList
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {}
            })
            return data
        }
}
