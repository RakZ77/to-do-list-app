package kh.edu.rupp.to_dolistapp.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import kh.edu.rupp.to_dolistapp.models.User;
import kh.edu.rupp.to_dolistapp.models.UserResponse;
import kh.edu.rupp.to_dolistapp.services.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private UserService api;

    @Inject
    public UserRepository(UserService api) {
        this.api = api;
    }

    public LiveData<List<User>> getUsers(){
        MutableLiveData<List<User>> data = new MutableLiveData<>();

        api.getUsers().enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body().getUsers());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
        return data;
    }
}
