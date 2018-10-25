package com.example.hoon.thesiswork;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchActivity extends Activity {

    String myJSON;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_NAME = "product_name";  // 상품명
    private static final String TAG_PRICE = "price";    // 가격
    private static final String TAG_CATEGORY = "category";  // 카테고리
    private static final String TAG_MAKER = "maker";    // 제조사

    private static final String TAG_IMAGE_LINK = "image_link";  // 상품별 이미지 링크. item 테이블에 정의됨.

    String name;
    String price;
    String category;
    String maker;

    String[] name2;
    String[] price2;
    String[] category2;
    String[] maker2;

    JSONArray items = null; // 리스트뷰의 한칸한칸들

    ArrayList<HashMap<String,String>> itemList;

    ListView list;  // 리스트뷰
    //// (ListView)findViewById(R.id.itemView);

    // 상품 검색 내용 필드
    EditText editSearch;
    //SearchAdapter searchAdapter;

    // 미리담기 버튼
    Button preBuyButton; // (Button) header.findViewById(R.id.preAddButton)

    ImageView itemImageView; // 리스트뷰의 상품 이미지

    //String image_link; // 서버에서 받아오는 이미지의 URL주소

    URL imageURL; // String image_link 변환용

   private View header; // list_item 의 위젯을 참조하기 위함

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //context = this;

        header = getLayoutInflater().inflate(R.layout.list_item , null, false);

        list = (ListView)findViewById(R.id.itemView);
        itemList = new ArrayList<HashMap<String, String>>();

        // !!!!!!!!!!!! 문제의 코드. header붙여줘야 안 튕긴다.
        itemImageView = (ImageView) header.findViewById(R.id.itemImage);

        preBuyButton = (Button) header.findViewById(R.id.preAddButton);

        //list.setAdapter(listViewAdapter);

        // !!!! wifi - ip 확인 요망!!
        getData("http://"+MainActivity.IPstring+"/PHP_connection.php");

        //!!
        //! ###################


        editSearch = (EditText)findViewById(R.id.searchText);
//!!!@@@@@@@@@@@@@ 에딧텍스트 내용 변경에 따라 검색결과 반영
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable edit) {
                // TODO : item filtering
                String filterText = edit.toString();

                /*
                ########## 밑에 돋보기글씨같은거 안보이게 주석처리
                if (filterText.length() > 0) {
                    list.setFilterText(filterText);
                } else {
                    list.clearTextFilter();
                }
*/
                // ########### 밑에 돋보기글씨같은거 안보이게 하는코드
                ((SimpleAdapter)list.getAdapter()).getFilter().filter(filterText);
            }
        });


    }


    protected void showList(){

        final ListAdapter adapter;

        try {
            JSONObject jsonObject = new JSONObject(myJSON);
            items = jsonObject.getJSONArray(TAG_RESULTS);

            for (int i = 0; i< items.length(); i++) {

                // for문 안의 변수들임 final or not
                JSONObject c = items.getJSONObject(i);
                name = c.getString(TAG_NAME);
                price = c.getString(TAG_PRICE);
                category = c.getString(TAG_CATEGORY);
                maker = c.getString(TAG_MAKER);

                // %%%%%
                String image_link = c.getString(TAG_IMAGE_LINK);

                // ######
                String decoded = URLDecoder.decode(image_link , "UTF-8");

                // JSONObject c..

//                Log.i("tagkk" , "1111");
//                ImageView itemImageView = (ImageView) findViewById(R.id.itemImage);
//                Log.i("tagppp" , "2222");

                HashMap<String , String> goods = new HashMap<String, String>();

                goods.put(TAG_NAME , name); // goods.put(String key , String value)
                goods.put(TAG_PRICE , price +"원");
                goods.put(TAG_CATEGORY , "분류 : " + category);
                goods.put(TAG_MAKER , "제조사 : " + maker);

                goods.put(TAG_IMAGE_LINK , image_link);

                Log.i("tagll" , "5555");
//                // !!!!!!!!
//                ImageView itemImageView = (ImageView) findViewById(R.id.itemImage);
                Log.i("tagll" , "6666");

//                if (itemImageView == null){
//                    Log.i("tagERROR" , "itemImageView(target)(into) is NULL . it will cause picasso error");
//                }

                // !!!!!!!!!!!!!!!!!!!!!
                //final Uri imageUri = Uri.parse(image_link);

               // String encodeResult = URLEncoder.encode(image_link , "UTF-8");

                //Glide.with(this).load(encodeResult).into(itemImageView);

                Log.i("tagtt", name+price+category);
                itemList.add(goods);    // itemList = new ArrayList<Hashmap<String,String>>
                // HashMap<String,String> goods

                Log.e("TAG_IMAGE_LINK" , TAG_IMAGE_LINK);
                Log.i("image_link " , image_link); // 왜 로그캣에 테이블 맨 밑에 주소가 뜨지..??
                Log.i("TAG_NAME" ,TAG_NAME);
                Log.i("TAG_PRICE", TAG_PRICE);
                Log.i("product_name" , name);
                Log.i("decoded" , decoded);

            } // EOF for()


            // ####
            adapter = new SimpleAdapter(
                    //  listViewAdapter = new ListViewAdapter(
                    this , itemList , R.layout.list_item,
                    new String[]{TAG_NAME,TAG_PRICE,TAG_CATEGORY,TAG_MAKER, TAG_IMAGE_LINK }, // TAG_IMAGE_LINK -> unable to decode stream: FileNotFoundException 뜸. resolveUri failed on bad bitmap uri.
                    new int[]{R.id.nameText ,R.id.priceText , R.id.categoryText , R.id.makerText , R.id.itemImage} // R.id.itemImage형식.. 그런데 위처럼 에러..
            );

            list.setAdapter(adapter);


            Log.i("hhhhh" , "000000");

            //############################
            list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view,final int position, long id) {

                    PopupMenu popup = new PopupMenu(SearchActivity.this , view); // 뷰는 오래 눌러진 뷰

                    getMenuInflater().inflate(R.menu.menu , popup.getMenu());

//                    //final int index = position;
//                    adapter.getItem(position);

                    Log.i("hhhhh" , "111111");

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {

                            switch(menuItem.getItemId()){
                                case R.id.preAddPopup:
                                    Log.i("hhhhh" , "22222");
//                                    PreAdd.nameMoved = name;
////                                    PreAdd.priceMoved = price;
////                                    PreAdd.categoryMoved = category;
////                                    PreAdd.makerMoved = maker;

                                    Intent intent = new Intent(getApplicationContext(), PreAdd.class);
                                    intent.putExtra("name" , itemList.get(position).get(TAG_NAME));
                                    intent.putExtra("category" , itemList.get(position).get(TAG_CATEGORY));
                                    intent.putExtra("maker" , itemList.get(position).get(TAG_MAKER));
                                    intent.putExtra("price" , itemList.get(position).get(TAG_PRICE));

                                    startActivity(intent);
                                    Log.i("인텐트" , intent+"");

//                                    Log.i("PreAdd.nameMoved" , PreAdd.nameMoved);
                                      Log.i("name" , name);
                                      Log.i("hhhh" , "44444");
                                    Toast.makeText(SearchActivity.this , "미리담기 리스트로 이동되었습니다." , Toast.LENGTH_SHORT).show();

                                    break;

                            }


                            return false;
                        }
                    });

                    popup.show();

                    return false;
                }
            });

            //list.setAdapter(listViewAdapter);
            // EOF try{}
        }   catch (JSONException e) {
            e.printStackTrace();
        }

        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

//        catch (UnsupportedEncodingException use){
//            use.printStackTrace();
//        }

    }

    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String , Void , String> {

            protected String doInBackground(String... params){
                String uri = params[0];

                BufferedReader bufferedReader = null;
                Log.i("tagtt","1");
                try {
                    URL url = new URL(uri);
                    Log.i("tagtt","2");
                    HttpURLConnection con = (HttpURLConnection)url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    Log.i("tagtt","3");
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    Log.i("tagtt","4");
                    while ((json = bufferedReader.readLine()) != null) {
                        Log.i("tagtt","5");

                        sb.append(json + "\n");
                        Log.i("tagtt",sb.toString());
                    }
                    return sb.toString().trim();
                } catch(Exception e) {
                    return null;
                }
            }

            protected void onPostExecute(String result) {
                Log.i("tagtt","6");
                myJSON = result;
                showList();
            }
        }
        Log.i("tagtt","7");
        GetDataJSON g = new GetDataJSON();
        g.execute(url);

    }

}
