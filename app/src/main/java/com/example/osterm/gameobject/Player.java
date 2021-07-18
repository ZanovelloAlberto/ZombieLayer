package com.example.osterm.gameobject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;


import androidx.core.content.ContextCompat;

import com.example.osterm.GameLoop;
import com.example.osterm.Menage.Weapon;
import com.example.osterm.R;
import com.example.osterm.Utils;
import com.example.osterm.gamepanel.HealthBar;
import com.example.osterm.gamepanel.Joystick;
import com.example.osterm.gamepanel.Map;

/**
 * Player is the main character of the game, which the user can control with a touch joystick.
 * The player class is an extension of a Circle, which is an extension of a GameObject
 */
public class Player extends Circle {
    public static final double SPEED_PIXELS_PER_SECOND = 400.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    public static final int MAX_HEALTH_POINTS = 5;
    public Joystick joystick;
    public Weapon weapon;
    private HealthBar healthBar;
    private int healthPoints = MAX_HEALTH_POINTS;
    public int coins = 0;
    int animState;
    public Map map;
    private int width;
    private int height;
    Bitmap bmp;
    int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };

    public Player(Context context, double positionX, double positionY, double radius, Map map,Bitmap bitmap) {
        super( ContextCompat.getColor(context, R.color.player), positionX, positionY, radius);
        this.joystick =  new Joystick(275, 700, 150, 40);
        this.healthBar = new HealthBar(context, this);
        weapon = new Weapon(1,1,1,20,"pistol",this);
        this.map = map;
        this.bmp = bitmap;
        this.width = bmp.getWidth() / 3;

        this.height = bmp.getHeight() / 4;
    }

    public void update() {
        weapon.update();
        joystick.update();

        // Update velocity based on actuator of joystick
        velocityX = joystick.getActuatorX()*MAX_SPEED;
        velocityY = joystick.getActuatorY()*MAX_SPEED;

        // Control For Wall
        if (map.passam(positionX+velocityX,positionY +velocityY,false)){
            // Update position
            positionX += velocityX;
            positionY += velocityY;
        }
        else if(map.passam(positionX,positionY +velocityY,false))
            positionY+=velocityY;
        else if(map.passam(positionX+velocityX,positionY,false))
            positionX+=velocityX;



        // Update direction
    }
    private int getAnimationRow() {

        double dirDouble = (Math.atan2(velocityX, velocityY) / (Math.PI / 2) + 2);

        int direction = (int) Math.round(dirDouble) % 4;

        return DIRECTION_TO_ANIMATION_MAP[direction];

    }
    public void drawThis(Canvas canvas,int x,int y)
    {
        animState++;
        int srcX = animState%27/9 *width ;
        int srcY = getAnimationRow() * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);

        Rect dst = new Rect((int)canvas.getWidth()/2-60, (int)canvas.getHeight ()/2-60,(int)canvas.getWidth()/2 + 60, canvas.getHeight ()/2+ 60);
        canvas.drawBitmap(bmp, src, dst, null);
    }

    public void draw(Canvas canvas,double x , double y) {
        //super.draw(canvas,x , y);
        drawThis(canvas,(int)x , (int)y);
        healthBar.draw(canvas);
        joystick.draw(canvas);
        weapon.draw(canvas);
    }

    public int getHealthPoint() {
        return healthPoints;
    }

    public void setHealthPoint(int healthPoints) {
        // Only allow positive values
        if (healthPoints >= 0)
            this.healthPoints = healthPoints;
    }
    public boolean onTouch(MotionEvent event){
        joystick.onTouch(event);
        weapon.joystick.onTouch(event);
        return  true;
    }

}
