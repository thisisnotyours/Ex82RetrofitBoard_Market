package com.suek.ex82retrofitboard_market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<BoardItem> boardItems= new ArrayList<>();  //대량의 데이터(BoardItem 을 끌고다니는)
    BoardAdapter adapter;

    SwipeRefreshLayout refreshLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView= findViewById(R.id.recycler);
        adapter= new BoardAdapter(this, boardItems);
        recyclerView.setAdapter(adapter);

        refreshLayout= findViewById(R.id.swipe_refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //리사이클러 뷰에 보여줄 데이터들을 리 로딩
                loadData();

                //리프레시 아이콘이 안보이도록...
                refreshLayout.setRefreshing(false);
            }
        });

    }//onCreate()


    //액티비티가 화면에 보여질때.. 서버에서 데이터를 읽어옴 --> 자동 실행되는 라이프사이클 메소드
    @Override
    protected void onResume() {
        super.onResume();

        //서버의 데이터 읽어오기
        loadData();
    }


    //서버에서 데이터를 불러들이는 작업메소드
    void loadData(){
        //테스트용..
        /*boardItems.add(new BoardItem(1,"name", "title", "msg","1,000", null, 1, "2020" ));
        boardItems.add(new BoardItem(2,"name", "title", "msg","1,000", null, 0, "2020" ));*/

        //레트로핏으로 읽기
        Retrofit retrofit= RetrofitHelper.getInstance();   //json 응답
        RetrofitService retrofitService= retrofit.create(RetrofitService.class);
        Call<ArrayList<BoardItem>> call= retrofitService.loadDataFromBoard();
        call.enqueue(new Callback<ArrayList<BoardItem>>() {
            @Override
            public void onResponse(Call<ArrayList<BoardItem>> call, Response<ArrayList<BoardItem>> response) {
                if(response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                    //서버데이터를 읽어와 새로운 List 객체 얻어옴
                    ArrayList<BoardItem> items= response.body();

                    //새로운 아답터를 만들면 조금 느리므로..
                    //기존 아답터가 보여주던 boardItems 리스트 객체의
                    //값들을 변경

                    //일단, 기존 리스트들 모두 삭제
                    boardItems.clear();
                    adapter.notifyDataSetChanged(); //모든 데이터가 다 바뀜

                    //서버에서 읽어온  items 를 boardItems 에 새로 추가
                    for(BoardItem item : items){
                        boardItems.add(0, item);
                        adapter.notifyItemInserted(0);   //recycler 의 화면 갱신법-빠귄 데이터만 notify 해줌- 훨씬 빠름
                    }

                    //리사이클러에서 보여줘야 되므로.. 위에서 얻어온 리스트로 새로운 아답터 객체를 생성
                    /*adapter= new BoardAdapter(MainActivity.this, items);
                    recyclerView.setAdapter(adapter);*/
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BoardItem>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




    public void clickEdit(View view) {
        //글작성 화면으로 전환
        Intent intent= new Intent(this, EditActivity.class);
        startActivity(intent);
    }
}
