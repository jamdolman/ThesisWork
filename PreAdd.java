package com.example.hoon.thesiswork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

// 미리담기 클래스
public class PreAdd extends Activity {

    //String myJSON;

    // 검색 액티비티에서 리스트아이템 버튼이 눌렸을때 스트링값들을 복사받음 -> 복사된 값들을 어댑터에 표시하자
    static String nameMoved;
    static String priceMoved;
    static String categoryMoved;
    static String makerMoved;

    private View header;

    TextView nameText;
    TextView priceText;
    TextView categoryText;
    TextView makerText;

    String name2;
    String price2;
    String category2;
    String maker2;

    ListView listView;  // 리스트뷰
    ListAdapter adapter;    // 어댑터
    ArrayList<HashMap<String,String>> itemList; // itemList.add(goods);
    HashMap<String, String> goods = new HashMap<String , String>();
    String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_add);

        Log.i("hhhhh" , "5");

        header = getLayoutInflater().inflate(R.layout.list_item , null, false);

        nameText = (TextView) header.findViewById(R.id.nameText);
        priceText = (TextView) header.findViewById(R.id.priceText);
        categoryText = (TextView) header.findViewById(R.id.categoryText);
        makerText = (TextView) header.findViewById(R.id.makerText);

        // #####################################
        listView = (ListView) findViewById(R.id.preAddList);
        itemList = new ArrayList<HashMap<String, String>>();

//        Log.i("hhhhh" , "6");
////        // ############################ 인텐트 시작 (검색액티비티서 받아오기)
//        Intent intent = getIntent();
//        Log.i("받는" +"인텐트" , intent+"");
//        Log.i("hhhhh" , "7");
//
//        String log = Integer.toString(items.length);
//        Log.i("items.length" , log);
//
//        Log.i("hhhhh" , "8");
//
//        for (int i = 0; i < items.length; i++){
//
//            Log.i("hhhhh" , "9");
//            goods.put(nameMoved , nameMoved);
//            goods.put(priceMoved , priceMoved);
//            goods.put(categoryMoved , categoryMoved);
//            goods.put(makerMoved , makerMoved);
//
//            itemList.add(goods);
                //goods.put(key,value);
//            Log.i("hhhhh" , "10");
//        }

        Log.i("hhhhh" , "11");

        Intent intent = getIntent();

//        nameText.setText(intent.getStringExtra("name"));
////        Log.i("nameText" , nameText.getText().toString());
////        priceText.setText(intent.getStringExtra("price"));
////        categoryText.setText(intent.getStringExtra("category"));
////        makerText.setText(intent.getStringExtra("maker"));
////        Log.i("makerText" , makerText.getText().toString());
////
////        goods.put(nameText.getText().toString(),nameText.getText().toString());
////        goods.put(priceText.getText().toString(),priceText.getText().toString());
////        goods.put(categoryText.getText().toString(),categoryText.getText().toString());
////        goods.put(makerText.getText().toString(),makerText.getText().toString());
////
////        itemList.add(goods);

        Log.i("hhhhh" , "12");


        adapter = new SimpleAdapter(this , itemList , R.layout.list_item ,
                new String[]{ nameText.getText().toString() , priceText.getText().toString() , categoryText.getText().toString() , makerText.getText().toString()} , // from ~
                new int[]{R.id.nameText ,R.id.priceText , R.id.categoryText , R.id.makerText , R.id.itemImage} ); // to !!
//        Log.i("nameMoved" , nameMoved);
        Log.i("nameText" , ""+nameText);


        Log.i("hhhhh" , "13");

        listView.setAdapter(adapter);   // listView = (ListView) findViewById(R.id.preAddList)

        Log.i("hhhhh" , "14");




    }


    /*

    try {
    // HashMap<String, String> goods = new HashMap<String , String>();
    // String[] items;
    // JSONArray items = null;
    // ArrayList<HashMap<String,String>> itemList;
//    adapter = new SimpleAdapter(this , itemList , R.layout.list_item ,
  //              new String[]{ nameMoved , priceMoved , categoryMoved , makerMoved} ,
    //            new int[]{R.id.nameText ,R.id.priceText , R.id.categoryText , R.id.makerText , R.id.itemImage} );




            JSONObject jsonObject = new JSONObject(myJSON);
            items = jsonObject.getJSONArray(TAG_RESULTS);

            for (int i = 0; i< items.length(); i++) {

                // for문 안의 변수들임 final or not
                JSONObject c = items.getJSONObject(i);
                name = c.getString(TAG_NAME);
                price = c.getString(TAG_PRICE);
                category = c.getString(TAG_CATEGORY);
                maker = c.getString(TAG_MAKER);

                HashMap<String , String> goods = new HashMap<String, String>();

                goods.put(TAG_NAME , name); // goods.put(String key , String value)
                goods.put(TAG_PRICE , price +"원");
                goods.put(TAG_CATEGORY , "분류 : " + category);
                goods.put(TAG_MAKER , "제조사 : " + maker);

                goods.put(TAG_IMAGE_LINK , image_link);

            } // EOF for()


     */

}
