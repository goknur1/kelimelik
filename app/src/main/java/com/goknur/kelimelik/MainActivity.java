package com.goknur.kelimelik;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.goknur.kelimelik.app.App;
import com.goknur.kelimelik.rest.model.Kelime;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by goknur on 12.07.2017.
 */
public class MainActivity extends BaseActivity {

    private TextView txtEmail;

    public static final String ARG_LOGIN_EMAIL = "loginEmail";
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        txtEmail = (TextView) findViewById( R.id.txtUserInfo );


        Intent intent = getIntent();

        if (intent != null) {
            Bundle bundle = intent.getExtras();

            if (bundle != null) {
                String email = bundle.getString( ARG_LOGIN_EMAIL );
                txtEmail.setText( email );
                txtEmail.setText( "Hoşgeldiniz," + bundle.getString( ARG_LOGIN_EMAIL ) );

            } else {
                txtEmail.setText( "Deger Boş" );

            }
        }

    }

    public void Tıklandı(View v) {
        if (v.getId() == R.id.oyun_oyna_btn) {
            //oyun oyna butonuna basıldığında oyun oynanılacak sayfaya geçiş yapılcak.

            serviceCall();
            // Intent intent = new Intent( getApplicationContext(), OyunActivity.class );
            // startActivity( intent );
        }

        if (v.getId() == R.id.sıralama_btn) {
            //sıralama butonuna basıldığında sıralamanın olduğu sayfaya yönlendirilecek.
            Intent intent = new Intent( getApplicationContext(), OyunActivity.class );
            startActivity( intent );

        }
    }

    private void serviceCall() {

        Call<HashMap<String, Kelime>> call = App.getRestClient().getKelimeService().getKelimeList2();

        call.enqueue( new Callback<HashMap<String, Kelime>>() {
            @Override
            public void onResponse(Call<HashMap<String, Kelime>> call, Response<HashMap<String, Kelime>> response) {

                List<Kelime> kelimeList = new ArrayList<Kelime>();
                for (Map.Entry<String, Kelime> map : response.body().entrySet()) {

                    kelimeList.add( map.getValue() );
                }

                Intent intent = new Intent( getApplicationContext(), OyunActivity.class );
                Bundle bundle = new Bundle();
                bundle.putParcelable( OyunActivity.ARG_KELIME_LIST, Parcels.wrap( kelimeList ) );
                intent.putExtras( bundle );

                startActivity( intent );
            }

            @Override
            public void onFailure(Call<HashMap<String, Kelime>> call, Throwable t) {
                Log.d( TAG, "olmadı" );
            }
        } );
    }


}



