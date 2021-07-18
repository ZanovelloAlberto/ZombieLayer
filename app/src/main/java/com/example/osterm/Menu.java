package com.example.osterm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

public class Menu extends AppCompatActivity {

    boolean showFps = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
    public void clickStart(View view){

        //setContentView(R.layout.loading);
        final Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        //TT tt = new TT(intent);
        //tt.start();



    }
    public void clickOptions(View view){
        setContentView(R.layout.options);
        ((Switch)findViewById(R.id.switchFps)).setText("Show Fps");
        ((Switch)findViewById(R.id.switchFps)).setChecked(showFps);

    }
    public void clickExit(View view){
        finish();
    }
    class TT extends Thread
    {
        Intent i;
        public TT(Intent i)
        {
           this.i = i;
        }

        @Override
        public void run() {
            try{
                sleep(1000);
            }catch (Exception e)
            {
                e.printStackTrace();
            }finally {

                startActivity(i);

            }
        }
    }
}
