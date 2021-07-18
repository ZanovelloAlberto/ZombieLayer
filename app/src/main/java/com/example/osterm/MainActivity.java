package com.example.osterm;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * MainActivity is the entry point to our application.
 */
public class MainActivity extends Activity {

    private Game game;
    boolean pause = false;
    TextView[] textViews = new TextView[3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity.java", "onCreate()");
        super.onCreate(savedInstanceState);



        FrameLayout.LayoutParams params;
        final FrameLayout frameLayout = new FrameLayout(this);

        //GAME
        game = new Game(this);
        params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.UNSPECIFIED_GRAVITY);
        game.setLayoutParams(params);
        frameLayout.addView(game);

        //BUTTON
        Button button = new Button(this);
        params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT,Gravity.RIGHT);
        button.setLayoutParams(params);
        button.setText("pause");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pause)
                {
                    pause = false;
                    ((Button)v).setText("pause");
                    game.gameLoop.isRunning = true;

                }
                else
                {
                    pause = true;
                    ((Button)v).setText("resume");
                    game.gameLoop.isRunning = false;
                }
            }
        });
        button.setGravity(Gravity.RIGHT);
        frameLayout.addView(button);

        LinearLayout bar = new LinearLayout(this);
        //COIN VIEW
        bar.addView(getItem(0,R.drawable.coin));
        bar.addView(getItem(1,R.drawable.bullet));
        bar.addView(getItem(2,R.drawable.round));


        params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT,Gravity.CENTER_HORIZONTAL);
        bar.setLayoutParams(params);

        frameLayout.addView(bar);

        //


        setContentView(frameLayout);




        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                    update();

            }
        });

    }

    private View getItem(int i,int drawable)
    {
        View item = getLayoutInflater().inflate(R.layout.item, null);
        ImageView imageView = item.findViewById(R.id.image);
        imageView.setImageResource(drawable);
        textViews[i] = item.findViewById(R.id.textView1);;


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        item.setLayoutParams(params);
        return item;
    }
    public void update()
    {


        textViews[0].setText(game.player.coins+"");
        textViews[1].setText(game.player.weapon.bullets+"");
        textViews[2].setText(game.menageEnemy.round+"");



    }


    @Override
    protected void onStart() {
        Log.d("MainActivity.java", "onStart()");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d("MainActivity.java", "onResume()");
        super.onResume();

    }

    @Override
    protected void onPause() {
        Log.d("MainActivity.java", "onPause()");
        if (game!=null)
            game.pause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("MainActivity.java", "onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("MainActivity.java", "onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        // Comment out "super.onBackPressed()" to disable button
        //super.onBackPressed();
    }
}
