package com.goknur.kelimelik;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by goknur on 12.07.2017.
 */
public class MainActivity extends BaseActivity {

    private TextView txtEmail;

    public static final String ARG_LOGIN_EMAIL = "loginEmail";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtEmail = (TextView) findViewById(R.id.txtUserInfo);


        Intent intent = getIntent();

        if (intent != null) {
            Bundle bundle = intent.getExtras();

            if (bundle != null) {
                String email = bundle.getString(ARG_LOGIN_EMAIL);
                txtEmail.setText(email);
                txtEmail.setText("Hoşgeldiniz," + bundle.getString(ARG_LOGIN_EMAIL));

            } else {
                txtEmail.setText("Deger Boş");

            }
        }

    }

    public void Tıklandı(View v) {
        if (v.getId() == R.id.oyun_oyna_btn) {
            //oyun oyna butonuna basıldığında oyun oynanılacak sayfaya geçiş yapılcak.
            Intent intent = new Intent(getApplicationContext(), OyunActivity.class);
            startActivity(intent);
        }

else if (v.getId()==R.id.sıralama_btn){
            //sıralama butonuna basıldığında sıralamanın olduğu sayfaya yönlendirilecek.

        }
    }

}



