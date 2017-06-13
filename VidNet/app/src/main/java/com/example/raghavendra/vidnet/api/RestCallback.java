package com.example.raghavendra.vidnet.api;

import retrofit.Callback;
import retrofit.RetrofitError;


/**
 * Sometimes retrofit does not give correct message. So this class will extract the correct message
 * @param <T>
 */

public abstract class RestCallback<T> implements Callback<T> {

    public abstract void failure(RestError restError);

    @Override
    public void failure(RetrofitError error) {
        RestError restError = (RestError) error.getBodyAs(RestError.class);

        if (restError != null)
            failure(restError);
        else
        {
            failure(new RestError(error.getMessage()));
        }
    }

}
