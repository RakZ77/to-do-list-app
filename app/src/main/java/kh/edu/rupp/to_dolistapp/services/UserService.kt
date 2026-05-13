package kh.edu.rupp.to_dolistapp.services

import kh.edu.rupp.to_dolistapp.models.UserResponse
import retrofit2.Call
import retrofit2.http.GET

interface UserService {
    // Fixed: use a regular fun instead of a property with @get:GET
    // The @get:GET syntax causes Retrofit to fail at runtime
    @GET("RakZ77/to-do-list-json-api/refs/heads/main/users.json")
    fun getUsers(): Call<UserResponse>
}
