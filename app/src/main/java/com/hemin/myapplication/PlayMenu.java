package com.hemin.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class PlayMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_menu2);
        SharedPreferences preferences=getSharedPreferences("Dama",0);
        String lan = preferences.getString("Lan","ku");
        Locale locale = new Locale(lan);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        //config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Button Back=findViewById(R.id.back);
        Back.setText(R.string.Back);


    }

    public void back2main(View view) {
        startActivity(new Intent(PlayMenu.this,MainActivity.class));
        this.finish();
    }

    public void twoplayer(View view) {
        startActivity(new Intent(PlayMenu.this,Game.class));
        this.finish();
    }
}