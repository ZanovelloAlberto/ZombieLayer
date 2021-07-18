package com.example.osterm.gamepanel;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class Button {
    private int outerCircleCenterPositionX;
    private int outerCircleCenterPositionY;

    public  int ID;

    private int outerCircleRadius;

    private double joystickCenterToTouchDistance;

    private Paint outerCirclePaint;
    public boolean isPressed = false;

    public Button(int centerPositionX, int centerPositionY, int outerCircleRadius,int color) {

        // Outer and inner circle make up the joystick
        outerCircleCenterPositionX = centerPositionX;
        outerCircleCenterPositionY = centerPositionY;


        // Radii of circles
        this.outerCircleRadius = outerCircleRadius;

        // paint of circles
        outerCirclePaint = new Paint();
        outerCirclePaint.setColor(color);
        outerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        outerCirclePaint.setTextSize(50);

    }
    public void draw(Canvas canvas,int coin) {
        // Draw outer circle
        canvas.drawCircle(
                outerCircleCenterPositionX,
                outerCircleCenterPositionY,
                outerCircleRadius,
                outerCirclePaint
        );
        canvas.drawText(coin+"",outerCircleCenterPositionX,outerCircleCenterPositionY-outerCircleRadius,outerCirclePaint);
    }
    public boolean isPressed(double touchPositionX, double touchPositionY) {
        joystickCenterToTouchDistance = Math.sqrt(
                Math.pow(outerCircleCenterPositionX - touchPositionX, 2) +
                        Math.pow(outerCircleCenterPositionY - touchPositionY, 2)
        );
        return joystickCenterToTouchDistance < outerCircleRadius;
    }
    public void onTouch(MotionEvent event)
    {
                int n = event.getPointerCount();
                for (int i = 0; n > i;i++ ) {

                    if (isPressed(event.getX(i), event.getY(i))) {
                        ID = event.getPointerId(event.getActionIndex());
                        isPressed = true;
                    }
                }
    }

}
