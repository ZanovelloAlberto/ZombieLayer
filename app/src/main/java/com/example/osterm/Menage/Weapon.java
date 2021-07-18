package com.example.osterm.Menage;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.osterm.Utils;
import com.example.osterm.gameobject.Player;
import com.example.osterm.gameobject.Spell;
import com.example.osterm.gamepanel.Button;
import com.example.osterm.gamepanel.Joystick;

import java.util.List;

import static java.security.AccessController.getContext;

public class Weapon {
    public double attackSpeed;
    public double reloadSpeed;
    public int damage;
    public int bulletSize;
    public String name;
    //public Joystick joystick;
    private long fireRateMs  = 0;
    public static Paint text = new Paint();
    private boolean reloading;
    public int bullets = 20;
    public  Joystick joystick;
    Player player;


    public Weapon(double attackSpeed,double reloadSpeed, int damage, int bulletSize, String name,Player player) {
        this.attackSpeed = attackSpeed;
        this.reloadSpeed = reloadSpeed;
        this.bulletSize = bulletSize;
        this.damage = damage;
        this.player = player;
        this.name = name;
        text.setTextSize(50);
        text.setColor(Color.WHITE);
        joystick = new Joystick(1700,700,150,50);
        //this.joystick =  new Joystick(1700, 700, 150, 40);

    }
    public Spell fire(Player player)
    {
        if ( fireRateMs < System.currentTimeMillis())
        {
                if (joystick.isPressed)
            {
                if (bullets>0){
                    if (player.directionX != 0 || player.directionY != 0) {
                        fireRateMs = System.currentTimeMillis() + (int) (200 * 1 / attackSpeed);
                        bullets--;
                        reloading = false;
                        return new Spell(player,1);
                    }
                }
                else
                {
                    reloading = true;
                    fireRateMs = System.currentTimeMillis()+(int)(3000*1/reloadSpeed);
                    bullets = bulletSize;
                }
            }

        }
        return null;
    }
    public void draw(Canvas canvas)
    {
        joystick.draw(canvas);

        //joystick.draw(canvas);
    }
    public void update()
    {
        joystick.update();
        if (joystick.actuatorX != 0 || joystick.actuatorY != 0) {
            // Normalize velocity to get direction (unit vector of velocity)
            double distance = Utils.getDistanceBetweenPoints(0, 0, joystick.actuatorX, joystick.actuatorY);
            player.directionX = joystick.actuatorX/distance;
            player.directionY = joystick.actuatorY/distance;
        }
    }

}
