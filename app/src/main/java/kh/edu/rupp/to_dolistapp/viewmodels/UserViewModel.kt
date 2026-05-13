package kh.edu.rupp.to_dolistapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import kh.edu.rupp.to_dolistapp.models.User;
import kh.edu.rupp.to_dolistapp.repositories.UserRepository;

@HiltViewModel
public class UserViewModel extends ViewModel {
    private UserRepository repo;
    private LiveData<List<User>> users;

    @Inject
    public UserViewModel(UserRepository repository) {
        this.repo = repository;
        users = repository.getUsers();
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }
}
