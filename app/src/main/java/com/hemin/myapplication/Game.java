package com.hemin.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.Locale;

public class Game extends AppCompatActivity {
    String[][] images = new String[8][8];
    int currentX, currentY, newX, newY,Xxwardn,Yxwardn;
    boolean clicked = false;
    String moved;
    private final float mx_max= Resources.getSystem().getDisplayMetrics().widthPixels;
    private final int cell=(int)((mx_max-100)/8);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);



        initializeMatrix();
        addCells();


    }
    void initializeMatrix(){
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if (i == 1 || i == 2) {
                    images[i][j] = "redMan";
                } else if (i == 5 || i == 6) {
                    images[i][j] = "blueMan";
                } else {
                    images[i][j] = null;
                }
            }
        }
    }

    void addCells(){
        TableLayout tab = findViewById(R.id.table);
        tab.removeAllViews();

        for(int i = 0; i < 8; i++){
            final int x = i;
            TableRow row = new TableRow(this);
            tab.addView(row);
            for(int j = 0; j < 8; j++){
                // creating image matrix
                ImageView button = new ImageView(this);
                final int y = j;
                row.addView(button);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    hWidth(button);
                }
                setBoardBackground(button, x, y);
                setImages(button, x, y);
                button.setOnClickListener(view -> {
                    if(clicked == false){
                        if(images[x][y] != null){
                            clicked = true;
                            currentX = x;
                            currentY = y;
                            moved = images[x][y];
                        }
                    }
                    else{
                        newX = x;
                        newY = y;
                        move();
                        addCells();
                        clicked = false;
                    }
                });
            }
        }
    }

    void setBoardBackground(ImageView button, int i, int j){
        if (i % 2 == 0) {
            if (j % 2 == 0) {
                button.setBackgroundColor(Color.rgb(210,210,210));
            }
            else {
                button.setBackgroundColor(Color.rgb(40,40,40));
            }
        }
        else {
            if (j % 2 == 0) {
                button.setBackgroundColor(Color.rgb(40,40,40));
            } else {
                button.setBackgroundColor(Color.rgb(210,210,210));
            }
        }
    }

    void setImages(ImageView button, int i, int j){
        if(images[i][j] == "redMan"){
            button.setImageDrawable(getResources().getDrawable(R.mipmap.red_man));
        }
        else if(images[i][j] == "blueMan"){
            button.setImageDrawable(getResources().getDrawable(R.mipmap.blue_man));
        }
        else if(images[i][j] == "redKing"){
            button.setImageDrawable(getResources().getDrawable(R.mipmap.red_king));
        }
        else if(images[i][j] == "blueKing"){
            button.setImageDrawable(getResources().getDrawable(R.mipmap.blue_king));
        }
    }

    void move(){
        if(!isEqual()){
            if(moved == "redMan"){
                if((newX == currentX + 1 && newY == currentY) || (newY == currentY + 1 && newX == currentX) || (newY == currentY - 1 && newX == currentX)){
                    images[newX][newY] = "redMan";
                    images[currentX][currentY] = null;
                    if(newX == 7)
                        images[newX][newY] = "redKing";
                }
                else if((((newX == currentX + 2 && newY == currentY)&&(images[currentX+1][currentY]=="blueMan")  ))
                        || ((newY == currentY + 2 && newX == currentX) &&(images[currentX][currentY+1]=="blueMan"))
                        || ((newY == currentY - 2 && newX == currentX)&&(images[currentX][currentY-1]=="blueMan"))
                ){
                    images[newX][newY] = "redMan";
                    images[currentX][currentY] = null;
                    redCaptures();
                    images[Xxwardn][Yxwardn]=null;
                    if(newX == 7)
                        images[newX][newY] = "redKing";
                }
            }
            else if(moved == "blueMan"){
                if((newX == currentX - 1 && newY == currentY) || (newY == currentY + 1 && newX == currentX) || (newY == currentY - 1 && newX == currentX)){
                    images[newX][newY] = "blueMan";
                    images[currentX][currentY] = null;
                    if(newX == 0)
                        images[newX][newY] = "blueKing";
                }
                else if((((newX == currentX - 2  && newY == currentY)&&(images[currentX-1][currentY]=="redMan")  ))
                        || ((newY == currentY + 2 && newX == currentX) &&(images[currentX][currentY+1]=="redMan"))
                        || ((newY == currentY - 2 && newX == currentX)&&(images[currentX][currentY-1]=="redMan"))
                ){
                    images[newX][newY] = "blueMan";
                    images[currentX][currentY] = null;
                    blueCaptures();
                    images[Xxwardn][Yxwardn]=null;
                    if(newX == 0)
                        images[newX][newY] = "blueKing";
                }
            }
            else if(moved == "redKing"){
                KingCaptures("redMan");
            }
            else if(moved == "blueKing"){
                KingCaptures("blueMan");
            }
        }
    }

    boolean isEqual(){
        return images[newX][newY] == "redMan" || images[newX][newY] == "redKing" || images[newX][newY] == "blueMan" || images[newX][newY] == "blueKing";
    }

    private void hWidth(ImageView button){
        button.getLayoutParams().height=cell;
        button.getLayoutParams().width=cell;
    }

    void redCaptures(){
        if(currentY == newY){
            Xxwardn = newX - 1;
            Yxwardn = currentY;
        }
        else
            captureInYAxis();
    }

    void blueCaptures(){
        if(currentY == newY){
            Xxwardn = newX + 1;
            Yxwardn = currentY;
        }
        else
            captureInYAxis();
    }

    void captureInYAxis(){
        if(currentX == newX){
            if(newY > currentY){
                Yxwardn = newY - 1;
            }
            else {
                Yxwardn = newY + 1;
            }
            Xxwardn = currentX;
        }
    }





    void KingCaptures(String movedking){
        String check = null;
        int piecesCount = 0;
        String dashijulaw,kingyjulaw,dashyxwraw,kingixwraw;

        if(movedking=="redMan"){
            //kingy swr julawa
            dashijulaw="redMan";
            kingyjulaw="redKing";
            dashyxwraw="blueMan";
            kingixwraw="blueKing";
        }else{
            //kingy shin julawa
            dashijulaw="blueMan";
            kingyjulaw="blueKing";
            dashyxwraw="redMan";
            kingixwraw="redKing";

        }
        if(currentY == newY){
            if(newX > currentX){
                for(int i=currentX+1 ;i<= newX;i++){
                    check= images[i][currentY];
                    if(check==dashyxwraw || check==kingixwraw){
                        piecesCount += 1;
                        Xxwardn = i;
                    }
                    else if(check == dashijulaw || check == kingyjulaw)
                        piecesCount = 10;
                }
                if(piecesCount == 1){
                    images[newX][newY] = moved;
                    System.out.println(moved);
                    images[currentX][currentY] = null;
                    images[Xxwardn][currentY] = null;
                    kingMoves();
                    // Toast.makeText(getApplicationContext(), "Youuuuuuu", Toast.LENGTH_LONG).show();
                }
                else if(piecesCount == 0){
                    kingMoves();
                }
                piecesCount = 0;
            }
            else if(newX < currentX){
                for(int i=currentX-1 ;i>= newX;i--){
                    check= images[i][currentY];
                    if(check==dashyxwraw || check==kingixwraw){
                        piecesCount += 1;
                        Xxwardn = i;
                    }
                    else if(check == dashijulaw || check == kingyjulaw)
                        piecesCount = 10;
                }
                if(piecesCount == 1){
                    images[newX][newY] = moved;
                    images[currentX][currentY] = null;
                    images[Xxwardn][currentY] = null;
                    kingMoves();
                    // Toast.makeText(getApplicationContext(), "Youuuuuuu", Toast.LENGTH_LONG).show();
                }
                else if(piecesCount == 0){
                    kingMoves();
                }
                piecesCount = 0;
            }
        }
        else if(currentX == newX){
            if(newY > currentY){
                for(int i=currentY+1 ;i<= newY;i++){
                    check= images[currentX][i];
                    if(check==dashyxwraw || check==kingixwraw){
                        piecesCount += 1;
                        Yxwardn = i;
                    }
                    else if(check == dashijulaw || check == kingyjulaw)
                        piecesCount = 10;
                }
                if(piecesCount == 1){
                    images[newX][newY] = moved;
                    images[currentX][currentY] = null;
                    images[currentX][Yxwardn] = null;
                    kingMoves();
                    Toast.makeText(getApplicationContext(), "Youuuuuuu", Toast.LENGTH_LONG).show();
                }
                else if(piecesCount == 0){
                    kingMoves();
                }
                piecesCount = 0;
            }
            else if(newY < currentY){
                for(int i=currentY-1 ;i>= newY;i--){
                    check= images[currentX][i];
                    if(check==dashyxwraw || check==kingixwraw){
                        piecesCount += 1;
                        Yxwardn = i;
                    }
                    else if(check == dashijulaw || check == kingyjulaw)
                        piecesCount = 10;
                }
                if(piecesCount == 1){
                    images[newX][newY] = moved;
                    images[currentX][currentY] = null;
                    images[currentX][Yxwardn] = null;
                    kingMoves();
                    // Toast.makeText(getApplicationContext(), "Youuuuuuu", Toast.LENGTH_LONG).show();
                }
                else if(piecesCount == 0){
                    kingMoves();
                }
                piecesCount = 0;
            }
        }
    }

    void kingMoves(){
        images[currentX][currentY] = null;
        images[newX][newY] = moved;
    }


    public void back2menu(View view) {
       
        startActivity(new Intent(Game.this,PlayMenu.class));
        this.finish();
    }
}