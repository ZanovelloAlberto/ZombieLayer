package com.example.osterm.gameobject;

import android.content.Context;
import android.graphics.Canvas;


import androidx.core.content.ContextCompat;

import com.example.osterm.GameLoop;
import com.example.osterm.Menage.MenageEnemy;
import com.example.osterm.R;

import java.util.List;


/**
 * Enemy is a character which always moves in the direction of the player.
 * The Enemy class is an extension of a Circle, which is an extension of a GameObject
 */
public class Enemy extends Circle
{

    private static final double SPEED_PIXELS_PER_SECOND = Player.SPEED_PIXELS_PER_SECOND*0.3;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private static final double SPAWNS_PER_MINUTE = 20;
    public int life;
    private static final double SPAWNS_PER_SECOND = SPAWNS_PER_MINUTE/60.0;
    private static final double UPDATES_PER_SPAWN = GameLoop.MAX_UPS/SPAWNS_PER_SECOND;
    private static double updatesUntilNextSpawn = UPDATES_PER_SPAWN;
    private Player player;

    public MenageEnemy.NodeWay ways[];

    public Enemy(Context context, Player player, double positionX, double positionY, MenageEnemy.NodeWay[] ways,int life) {
        super( ContextCompat.getColor(context, R.color.enemy), positionX, positionY, 30);
        this.player = player;
        this.ways = ways;
        this.life = life;
    }


    /**
     * Enemy is an overload constructor used for spawning enemies in random locations
     * @param context
     * @param player
     */
    public Enemy(Context context, Player player, MenageEnemy.NodeWay nodeWay) {

        super(ContextCompat.getColor(context, R.color.enemy), Math.random()*1800+100, Math.random()*1800+100, 30);
        this.player = player;
    }

    /**
     * readyToSpawn checks if a new enemy should spawn, according to the decided number of spawns
     * per minute (see SPAWNS_PER_MINUTE at top)
     * @return
     */
    public static boolean readyToSpawn(int round) {
        if (updatesUntilNextSpawn <= 0) {
            updatesUntilNextSpawn += UPDATES_PER_SPAWN / (Math.sqrt(round));
            return true;
        } else {
            updatesUntilNextSpawn --;
            return false;
        }
    }
    public void update() {
        // =========================================================================================
        //   Update velocity of the enemy so that the velocity is in the direction of the player
        // =========================================================================================
        // Calculate vector from enemy to player (in x and y)
        int xP = (int)(player.getPositionX()/100);
        int yP = (int)(player.getPositionY()/100);

        int xE =(int)(getPositionX()/100);
        int yE =(int)(getPositionY()/100);

        if (xP == xE && yE == yP) {
            double distanceToPlayerX = player.getPositionX() - positionX;
            double distanceToPlayerY = player.getPositionY() - positionY;

            // Calculate (absolute) distance between enemy (this) and player
            double distanceToPlayer = GameObject.getDistanceBetweenObjects(this, player);

            // Calculate direction from enemy to player
            double directionX = distanceToPlayerX / distanceToPlayer;
            double directionY = distanceToPlayerY / distanceToPlayer;

            // Set velocity in the direction to the player
            if (distanceToPlayer > 0) { // Avoid division by zero
                velocityX = directionX * MAX_SPEED;
                velocityY = directionY * MAX_SPEED;
            } else {
                velocityX = 0;
                velocityY = 0;
            }


        }
        else if (ways[xE+yE*20] != null && ways[xE+yE*20].prev != null)
        {
            MenageEnemy.NodeWay destination = ways[xE+yE*20].prev;
            double distance = Math.sqrt(
                    Math.pow((destination.x*100+50)-positionX, 2) +
                            Math.pow((destination.y*100+50)-positionY, 2)
            );
            directionX = ((destination.x*100+50)-positionX)/distance;
            directionY = ((destination.y*100+50)-positionY)/distance;

            velocityX = ((destination.x*100+50)-positionX)/distance*MAX_SPEED;
            velocityY = ((destination.y*100+50)-positionY)/distance*MAX_SPEED;
        }
        // =========================================================================================
        //   Update position of the enemy
        // =========================================================================================

        positionX += velocityX;
        positionY += velocityY;

    }
}

