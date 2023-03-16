package com.example.astronomypicdaily.Structure;

import android.content.Context;
import android.widget.Toast;

import com.example.astronomypicdaily.MainActivity;
import com.example.astronomypicdaily.OnFetchDataListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {

    public Context context;

    public Retrofit retrofit = new Retrofit.Builder().baseUrl("https://go-apod.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create()).build();

    public RequestManager(Context context){
        this.context = context;
    }


    public void getResponse(OnFetchDataListener listener){


        getPhoto callApi = retrofit.create(getPhoto.class);

        Call<Api> call = callApi.getThatImageMethod();

        try{

            call.enqueue(new Callback<Api>() {
                @Override
                public void onResponse(Call<Api> call, Response<Api> response) {

                           if(!response.isSuccessful())
                               Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();
                           else{

                               listener.onFetchData(response.body().copyright,response.body().data,response.body().explanation,response.body().hdurl,response.body().media_type,
                                       response.body().service_version,
                                       response.body().title,response.body().url,response.message());

                           }

                }

                @Override
                public void onFailure(Call<Api> call, Throwable t) {

                    Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();

                }
            });


        }
        catch (Exception e){
            e.printStackTrace();
        }

    }


}

 interface getPhoto{

     @GET("apod")

    Call<Api> getThatImageMethod(



    );

}
