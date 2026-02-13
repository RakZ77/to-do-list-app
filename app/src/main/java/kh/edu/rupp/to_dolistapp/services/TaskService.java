package kh.edu.rupp.to_dolistapp.services;

import kh.edu.rupp.to_dolistapp.models.TaskResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface TaskService {
    @GET("RakZ77/to-do-list-json-api/refs/heads/main/tasks.json")
    Call<TaskResponse> getTasks();
}