package com.suek.ex82retrofitboard_market;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface RetrofitService {

    //데이터들와 파일을 동시에 전송
    @Multipart
    @POST("/Retrofit_market/insertDB.php")     //데이터들와 파일을 동시에 전송할 수 있는 post 방식으로 서버에있는 /Retrofit/insertDB.php 에 insert 해줌
    Call<String> postDataToBoard(@PartMap Map<String, String> dataPart, //$_POST 로 받음(데이터들)
                                 @Part MultipartBody.Part filePart);    //추상메소드니까 ;세미콜론 찍어줌   //파일만 보낼거면 @Part 만  //MultipartBody- request 할때 식별자와 파일을 넣어주는?
                                                                       // $_FILE 로 받음(파일)

    //서버에서 데이터를 json 문서를 읽어와서 GSON 라이브러리를 통해 곧바로
    //자바 객체로 응답결과를 주는 추상메소드
    @GET("/Retrofit_market/loadDB.php")
    Call<ArrayList<BoardItem>> loadDataFromBoard();

    //'좋아요' 업데이트 해주는 추상메소드
    // [ @Body : 전달받은 객체를 서버에 json 으로 변환하여 전달 ]
    @PUT("/Retrofit_market/{filename}")
    Call<BoardItem> updateData(@Path("filename") String filename,
                                @Body BoardItem item);

}
