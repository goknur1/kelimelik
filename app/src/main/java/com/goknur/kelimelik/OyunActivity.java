package com.goknur.kelimelik;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.goknur.kelimelik.rest.model.Kelime;
import com.goknur.kelimelik.rest.model.Option;

import org.parceler.Parcels;

import java.util.List;

/**
 * Created by goknur on 13.07.2017.
 */

public class OyunActivity extends BaseActivity {


    private static final String TAG = OyunActivity.class.getSimpleName();
    public static final String ARG_KELIME_LIST = "kelimeList";

    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    TextView kelime_text;
    List<Kelime> kelimeList;

    private int kelimeIndex = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_oyun );

        btn1 = (Button) findViewById( R.id.button1 );
        btn2 = (Button) findViewById( R.id.button2 );
        btn3 = (Button) findViewById( R.id.button3 );
        btn4 = (Button) findViewById( R.id.button4 );


        TextView kelime_text;
        kelime_text = (TextView) findViewById( R.id.kelime_text );

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = getIntent().getExtras();
            this.kelimeList = Parcels.unwrap( bundle.getParcelable( ARG_KELIME_LIST ) );

        }


        Kelime kelime = kelimeList.get( kelimeIndex );
        kelime_text.setText( kelime.getKelime() );
        btn1.setText( kelime.getOptions().get( 0 ).getOptionName() );
        btn2.setText( kelime.getOptions().get( 1 ).getOptionName() );
        btn3.setText( kelime.getOptions().get( 2 ).getOptionName() );
        btn4.setText( kelime.getOptions().get( 3 ).getOptionName() );


        listenBtn1();
        listenBtn2();
        listenBtn3();
        listenBtn4();
        buttonClick();
    }

    private void listenBtn4() {
        btn4.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCorrectKelime( kelimeIndex, 3 )) {
                    btn4.setBackgroundResource( R.color.yeşil );
                    btn1.setClickable( false );
                    btn2.setClickable( false );
                    btn3.setClickable( false );
                    btn4.setClickable( true );


                } else {
                    btn4.setBackgroundResource( R.color.kırmızı );

                }
                kelimeIndex++;
            }

        } );

    }

    private void listenBtn3() {
        btn3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCorrectKelime( kelimeIndex, 2 )) {
                    btn3.setBackgroundResource( R.color.yeşil );
                    btn1.setClickable( false );
                    btn2.setClickable( false );
                    btn3.setClickable( true );
                    btn4.setClickable( false );

                } else {
                    btn3.setBackgroundResource( R.color.kırmızı );
                }
                kelimeIndex++;

            }
        } );

    }

    private void listenBtn2() {
        btn2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCorrectKelime( kelimeIndex, 1 )) {
                    btn2.setBackgroundResource( R.color.yeşil );
                    btn1.setClickable( false );
                    btn2.setClickable( true );
                    btn3.setClickable( false );
                    btn4.setClickable( false );
                } else {
                    btn2.setBackgroundResource( R.color.kırmızı );
                }


                kelimeIndex++;
            }
        } );
    }


    private void listenBtn1() {
        btn1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCorrectKelime( kelimeIndex, 0 )) {
                    btn1.setBackgroundResource( R.color.yeşil );
                    btn1.setClickable( true );
                    btn2.setClickable( false );
                    btn3.setClickable( false );
                    btn4.setClickable( false );
                } else {
                    btn1.setBackgroundResource( R.color.kırmızı );

                }
                kelimeIndex++;

            }


        } );
    }

    private boolean isCorrectKelime(int kelimeIndex, int btnClickIndex) {

        Kelime kelime = this.kelimeList.get( kelimeIndex );
        Option option = kelime.getOptions().get( btnClickIndex );

        if (option.isCorrect()) {
            return true;
        } else {
            return false;
        }

    }

    private void buttonClick() {

        btn1.setClickable( true );
        btn2.setClickable( true );
        btn3.setClickable( true );
        btn4.setClickable( true );
    }
}
