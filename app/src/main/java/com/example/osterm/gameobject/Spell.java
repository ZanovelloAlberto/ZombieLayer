package com.example.osterm.gameobject;

import android.content.Context;
import android.graphics.Color;

import androidx.core.content.ContextCompat;

import com.example.osterm.GameLoop;
import com.example.osterm.R;
import com.example.osterm.gamepanel.Joystick;
import com.example.osterm.gamepanel.Map;

public class Spell extends Circle {
    public static final double SPEED_PIXELS_PER_SECOND = 800.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    public int dmg;
    Map map;

    public Spell(Player spellcaster,int dmg) {
        super(Color.WHITE, spellcaster.getPositionX(), spellcaster.getPositionY(), 15);

        this.dmg = dmg;
        this.map = spellcaster.map;
        velocityX = spellcaster.directionX*MAX_SPEED;
        velocityY = spellcaster.directionY*MAX_SPEED;
    }

    @Override
    public void update() {
        positionX = positionX + velocityX;
        positionY = positionY + velocityY;

    }
    public boolean validate()
    {
        return map.passam(positionX,positionY,false);
        /*        if (Map.passam(positionX+velocityX,positionY +velocityY,false)){
            return true;
        }
        else if(Map.passam(positionX,positionY +velocityY,false))
            return  true;
        else if(Map.passam(positionX+velocityX,positionY,false))
            return  true;
        return  false;

         */
    }
}
