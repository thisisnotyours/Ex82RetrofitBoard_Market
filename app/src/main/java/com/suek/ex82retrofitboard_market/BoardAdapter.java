package com.suek.ex82retrofitboard_market;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<BoardItem> boardItems;

    public BoardAdapter(Context context, ArrayList<BoardItem> boardItems) {
        this.context = context;
        this.boardItems = boardItems;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.recycler_board_item, parent, false);
        VH holder= new VH(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh= (VH)holder;

        BoardItem item= boardItems.get(position);

        //DB 안에는 업로드된 파일의 서버내부 경로만 저장되어있음.
        //서버 주소까지 포함한 풀서버 url 이 필요
        String imgUrl= "http://suhyun2963.dothome.co.kr/Retrofit_market/" + item.file;
        Glide.with(context).load(imgUrl).into(vh.iv);

        vh.tvTitle.setText(item.title);
        vh.tvMsg.setText(item.msg);
        vh.tvPrice.setText(item.price+"원");

        //좋아요 토글버튼의 체크상태 설정
        vh.tbFav.setChecked(item.fav==1? true : false);

    }

    @Override
    public int getItemCount() {
        return boardItems.size();
    }



    //inner class
    class VH extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView tvTitle;
        TextView tvMsg;
        TextView tvPrice;
        ToggleButton tbFav;

        public VH(@NonNull View itemView) {
            super(itemView);    //itemView= recycler_board_items.xml

            iv= itemView.findViewById(R.id.iv);
            tvTitle= itemView.findViewById(R.id.tv_title);
            tvMsg= itemView.findViewById(R.id.tv_msg);
            tvPrice= itemView.findViewById(R.id.tv_price);
            tbFav= itemView.findViewById(R.id.tb_fav);
            
            
            
            //좋아요 토글버튼 선택 리스터
            tbFav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    
                    //바꿔야 할 데이터는 'fav' 뿐이지만 나중에 확장성을 위해서..
                    
                    //현재 누른 아이템 항목 얻어오기
                    BoardItem item= boardItems.get(getLayoutPosition());  //getLayoutPosition()= 현재 누른 번째 
                    item.fav= isChecked? 1:0;    //isChecked 가 true 면 1, 아니면 0
                    
                    RetrofitService retrofitService= RetrofitHelper.getInstance().create(RetrofitService.class);
                    Call<BoardItem> call= retrofitService.updateData("updateFav.php", item);
                    call.enqueue(new Callback<BoardItem>() {
                        @Override
                        public void onResponse(Call<BoardItem> call, Response<BoardItem> response) {
                            
                        }

                        @Override
                        public void onFailure(Call<BoardItem> call, Throwable t) {

                        }
                    });
                }
            });

        }
    }
}
