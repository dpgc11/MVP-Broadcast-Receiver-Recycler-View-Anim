package com.example.finin.rest;


import com.example.finin.data.network.model.UsersResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by yogesh on 15/5/17.
 */

public interface ApiService {

    @GET(ApiClient.GET_USERS_DATA)
    Single<UsersResponse> getUsersData(@Query("page") Integer page, @Query("delay") Integer size);
}
