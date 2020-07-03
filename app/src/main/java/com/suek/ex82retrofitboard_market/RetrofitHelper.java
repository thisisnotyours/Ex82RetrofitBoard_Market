package com.suek.ex82retrofitboard_market;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitHelper {

    //json 으로 리턴받음
    //GSON 용도
    public static Retrofit getInstance(){    //Retrofit 을 만들어 리턴시켜줌
        Retrofit.Builder builder= new Retrofit.Builder();
        builder.baseUrl("http://suhyun2963.dothome.co.kr");
        builder.addConverterFactory(GsonConverterFactory.create());
        return  builder.build();
    }

    //String 으로 리턴받음
    //Scalars 용도
    public static Retrofit getInstance2(){    //Retrofit 을 만들어 리턴시켜줌
        Retrofit.Builder builder= new Retrofit.Builder();
        builder.baseUrl("http://suhyun2963.dothome.co.kr");
        builder.addConverterFactory(ScalarsConverterFactory.create());
        return  builder.build();
    }
}
