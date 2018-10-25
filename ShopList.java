package com.example.hoon.thesiswork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class ShopList extends Activity {

    int totalPrice = 0; // 가격 총액
    TextView textView;
    EditText editText_topic;
    Button button_subscribe, button_clear;
    private MqttClient client;
    String topic = "market/+";
    TextView totalSum; // 총액 표시 뷰
    Button getSum; // 총액 구하기 버튼
    Button sendQR; // QR코드로 가격스트링값 전송

    int num1, num2, num3, num4 = 0;

    Items[] items = new Items[100]; // 상품 정보들을 저장하기 위한 배열

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

//        items[0].uID = "181,116,21,211";
////        items[0].itemName = "지우개";
////        items[0].itemPrice = "500";

        items[0] = new Items("지우개", "500" , "181,116,21,211");

        // Widgets
        textView = findViewById(R.id.textView); // 상품 정보들이 출력되는 텍스트뷰
        editText_topic = findViewById(R.id.editText_topic);
        button_subscribe = findViewById(R.id.button_subscribe);
        button_clear = findViewById(R.id.button_clear);
        editText_topic.setText(topic);
        totalSum = findViewById(R.id.totalSum);
        getSum = findViewById(R.id.getSum);
        sendQR = findViewById(R.id.sendQR);

        String clientId = MqttClient.generateClientId();
        try {

            // 네이버 클라우드 서버 IP
            client = new MqttClient("tcp://45.119.147.28:1883", clientId, new MemoryPersistence());
            client.connect();
            // hello message
//            client.publish("hello/world", new MqttMessage(new String("Hello MQTT !").getBytes()));
            client.subscribe(topic, new IMqttMessageListener() {

                @Override
                public void messageArrived(final String topic, final MqttMessage message) throws Exception {
                    Log.d(topic, message.toString());
                    runOnUiThread(new Runnable() {
                        public void run() {
                            String itemInfo; // 상품 UID
                            itemInfo = message.toString();
                            //textView.append("Message : " + itemInfo + "\n");

                            if (itemInfo == "181,116,21,211")
                            textView.append("상품명 : " + "지우개 \t" + "가격 : 500" +"\n");
                        }
                    });

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "error!", Toast.LENGTH_LONG).show();
        }

        // 구독 버튼 클릭시 기존 토픽 구독 해제 후 입력받은 토픽 구독
        button_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    client.unsubscribe(topic);
                    topic = editText_topic.getText().toString();
                    client.subscribe(topic, new IMqttMessageListener() {

                        @Override
                        public void messageArrived(final String topic, final MqttMessage message) throws Exception {
                            Log.d(topic, message.toString());
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    String itemInfo;
                                    itemInfo = message.toString();
                                    //textView.append("Message : " + itemInfo + "\n");
                                    Log.i("iteminfo" , itemInfo);
                                    Log.i("going" , "going");

                                    if (itemInfo.trim().equals("181,116,21,211")) {
                                        num1 = 500;
                                        textView.append("상품명 : " + "지우개 \t" + "가격 :" + num1 + "\n");
                                        totalPrice += num1;


//                                        if(totalSum.getText().equals("\0")) {
//                                            num1 = 500;
//                                            totalSum.append(Integer.toString(num1));
//                                            totalPrice += num1;
//                                            }
                                    }

                                    else if (itemInfo.trim().equals("37,161,30,211")) {
                                        num2 = 5000;
                                        textView.append("상품명 : " + "충전기 \t" + "가격 : " + num2 + "\n");
                                        totalPrice += num2;
                                    }

                                    else if (itemInfo.trim().equals("49,149,194,46")) {
                                        num3 = 2000;
                                        textView.append("상품명 : " + "RFID 카드 \t" + "가격 : " + num3 + "\n");
                                        totalPrice += num3;
                                    }

                                    else if (itemInfo.trim().equals("54,67,93,131")) {
                                        num4 = 1000;
                                        textView.append("상품명 : " + "열쇠고리 \t" + "가격 : " + num4 + "\n");
                                        totalPrice += num4;
                                    }

                                    else
                                        textView.append("등록되지 않은 상품입니다! \n");;

                                    }
                            });

                        }
                    });
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        });

        // 삭제 버튼 클릭시 내용 제거
        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("");
                totalSum.setText("");
            }
        });

        getSum.setOnClickListener(new View.OnClickListener() { // 총액 구하기 버튼 클릭 이벤트
            @Override
            public void onClick(View v) {
                totalSum.setText(Integer.toString(totalPrice));
            }
        });

        // 결제액 QR 전송 버튼
        sendQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext() , QRpay.class);
                intent.putExtra("price" , totalSum.getText().toString());
                startActivity(intent);
            }
        });

//        int sum = num1 + num2 + num3 + num4;
//        totalSum.setText(Integer.parseInt(String.valueOf(sum)));
    }


    class Items {

        String itemName;
        String itemPrice;
        String uID; // 태그시 찍힌 UID

        public Items(String itemName , String itemPrice, String uID){

            this.itemName = itemName;
            this.itemPrice = itemPrice;
            this.uID = uID;

        }

    }

}
