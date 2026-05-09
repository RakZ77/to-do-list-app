package kh.edu.rupp.to_dolistapp.services;

import kh.edu.rupp.to_dolistapp.models.UserResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface UserService {
    @GET("RakZ77/to-do-list-json-api/refs/heads/main/users.json")
    Call<UserResponse> getUsers();
}
