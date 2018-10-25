package com.example.hoon.thesiswork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuPage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);

        Button search = (Button) findViewById(R.id.searchButton);
        Button preAdd = (Button) findViewById(R.id.preAddButton);
        Button shopList = (Button) findViewById(R.id.shopbasketButton);
        Button qrButton = (Button) findViewById(R.id.billQRButton);

        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        preAdd.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent2 = new Intent(getApplicationContext() , PreAdd.class);
                startActivity(intent2);
            }
        });

        shopList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getApplicationContext(), ShopList.class);
                startActivity(intent3);
            }
        });

        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(getApplicationContext(), QRpay.class);
                startActivity(intent4);
            }
        });

    }
}
