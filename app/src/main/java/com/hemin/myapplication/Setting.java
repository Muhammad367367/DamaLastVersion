package com.hemin.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Locale;

public class Setting extends AppCompatActivity {
   private String lan="ku";
   private SharedPreferences preferences;
   private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        preferences=getSharedPreferences("Dama",0);
        editor=preferences.edit();
    }

    public void English(View view) {
        editor.putString("Lan","en");
        editor.apply();
        Refresh();
    }

    public void Kurdish(View view) {
        editor.putString("Lan","ku");
        editor.apply();
        Refresh();
    }
    private void Refresh(){
        String lan = preferences.getString("Lan","ku");
        Locale locale = new Locale(lan);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        //config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Button English=findViewById(R.id.English);
        Button Kurdish=findViewById(R.id.Kurdish);
        Button Back=findViewById(R.id.back2);
        Switch Sound=findViewById(R.id.Sound);
        TextView textView2=findViewById(R.id.textView2);
        English.setText(R.string.English);
        Kurdish.setText(R.string.Kurdish);
        Back.setText(R.string.Back);
        Sound.setText(R.string.Sound);
        textView2.setText(R.string.Lanuage);
    }

    public void back2menu(View view) {
        startActivity(new Intent(Setting.this,MainActivity.class));
        this.finish();
    }
}