package com.example.osterm.gamepanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


import androidx.core.content.ContextCompat;

import com.example.osterm.R;


/**
 * GameOver is a panel which draws the text Game Over to the screen.
 */
public class GameOver {

    private Context context;

    public GameOver(Context context) {
        this.context = context;
    }

    public void draw(Canvas canvas) {
        String text = "Game Over";

        float x = canvas.getWidth()/2-text.length()*80/2;
        float y = canvas.getHeight()/2;

        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.gameOver);
        paint.setColor(color);
        paint.setTextSize(150);
        canvas.drawText(text, x, y, paint);

        paint.setTextSize(50);
        paint.setColor(0xFFFFFFFF);
        canvas.drawText("press to back to menu",x+100,y+100,paint);
    }
}
