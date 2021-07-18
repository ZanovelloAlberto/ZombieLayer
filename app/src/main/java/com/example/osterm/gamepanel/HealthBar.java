package com.example.osterm.gamepanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;


import androidx.core.content.ContextCompat;

import com.example.osterm.R;
import com.example.osterm.gameobject.Player;

/**
 * HealthBar display the players health to the screen
 */
public class HealthBar {
    private Player player;
    private Paint borderPaint, healthPaint;
    private int width, height, margin; // pixel value

    public HealthBar(Context context, Player player) {
        this.player = player;
        this.width = 100;
        this.height = 20;
        this.margin = 2;

        this.borderPaint = new Paint();
        int borderColor = ContextCompat.getColor(context, R.color.healthBarBorder);
        borderPaint.setColor(borderColor);

        this.healthPaint = new Paint();
        int healthColor = ContextCompat.getColor(context, R.color.healthBarHealth);
        healthPaint.setColor(healthColor);
    }

    public void draw(Canvas canvas) {
        float x = (float) canvas.getWidth()/2;
        float y = (float) canvas.getHeight()/2;
        float distanceToPlayer = 30;
        float healthPointPercentage = (float) player.getHealthPoint()/player.MAX_HEALTH_POINTS;

        // Draw border
        float borderLeft, borderTop, borderRight, borderBottom;
        borderLeft = x - width/2;
        borderRight = x + width/2;
        borderBottom = y - distanceToPlayer;
        borderTop = borderBottom - height;
        canvas.drawRect(borderLeft, borderTop, borderRight, borderBottom, borderPaint);

        // Draw health
        float healthLeft, healthTop, healthRight, healthBottom, healthWidth, healthHeight;
        healthWidth = width - 2*margin;
        healthHeight = height - 2*margin;
        healthLeft = borderLeft + margin;
        healthRight = healthLeft + healthWidth*healthPointPercentage;
        healthBottom = borderBottom - margin;
        healthTop = healthBottom - healthHeight;
        canvas.drawRect(healthLeft, healthTop, healthRight, healthBottom, healthPaint);
    }
}
