package com.suek.ex82retrofitboard_market;

//서버에서 읽어온 게시글 Market 테이블의 한 record(row)의 데이터를 저장하는 VO 클래스(Value Object)-  중고거래 게시글같은..
public class BoardItem {
    int no;        //아이템번호
    String name;   //작성자 이름
    String title;  //제목
    String msg;    //내용
    String price;  //가격
    String file;   //업로드된 이미지 파일경로
    int fav;       //좋아요 여부 [ mysql 에 true, false 를 1, 0으로 대체하여 저장 -int 로 저장해야함]
    String date;   //작성일자



    public BoardItem() {
    }



    public BoardItem(int no, String name, String title, String msg, String price, String file, int fav, String date) {
        this.no = no;
        this.name = name;
        this.title = title;
        this.msg = msg;
        this.price = price;
        this.file = file;
        this.fav = fav;
        this.date = date;
    }
}
