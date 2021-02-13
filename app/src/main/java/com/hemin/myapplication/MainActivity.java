package com.hemin.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
MediaPlayer media;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        media=MediaPlayer.create(this,R.raw.song);
        media.start();
        SharedPreferences preferences=getSharedPreferences("Dama",0);
        String lan = preferences.getString("Lan","ku");
        Locale locale = new Locale(lan);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        //config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Button play=findViewById(R.id.play);
        Button Setting=findViewById(R.id.Setting);
        Button Exit=findViewById(R.id.CreateAccount);
        play.setText(R.string.Play);
        Setting.setText(R.string.Settings);
        Exit.setText(R.string.Exit);
    }

    public void playmenu(View view) {
        startActivity(new Intent(MainActivity.this,PlayMenu.class));
        this.finish();
    }

    public void createAccount(View view) {
        startActivity(new Intent(MainActivity.this,CreateAccount.class));
     }

    public void Setting(View view) {
        startActivity(new Intent(MainActivity.this,Setting.class));
        this.finish();
    }
}