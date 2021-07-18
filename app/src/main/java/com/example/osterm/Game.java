package com.example.osterm;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.osterm.Menage.MenageEnemy;


import com.example.osterm.gameobject.Player;
import com.example.osterm.gameobject.Spell;
import com.example.osterm.gamepanel.GameOver;

import com.example.osterm.gamepanel.Map;
import com.example.osterm.gamepanel.Performance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Game manages all objects in the game and is responsible for updating all states and render all
 * objects to the screen
 */
class Game extends SurfaceView implements SurfaceHolder.Callback {

    public Player player;
    public GameLoop gameLoop;
    private List<Spell> spellList = new ArrayList<Spell>();
    private GameOver gameOver;
    private Performance performance;
    Map map;
    MenageEnemy menageEnemy;
    String s = "";
    int openScene = 30;
    boolean stop = false;
    Context context;


    public Game(Context context) {
        super(context);




        // Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();


        surfaceHolder.addCallback(this);
        gameLoop = new GameLoop(this, surfaceHolder,context);


        // Initialize game panels
        performance = new Performance(context, gameLoop);
        gameOver = new GameOver(context);


        // Initialize game objects
        map = new Map(player);
        player = new Player(context, 1750, 1750, 30,map, BitmapFactory.decodeResource(getResources(), R.drawable.bad1));
        map.player = player;

        menageEnemy = new MenageEnemy(player,context);


        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        // Handle user input touch event actions
        if (player.getHealthPoint()>0){
            map.button.onTouch(event);
            player.onTouch(event);
        }
        else if(event.getActionMasked()==MotionEvent.ACTION_DOWN)
        {
            gameLoop.stopLoop();
            ((Activity)getContext()).finish();
        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("Game.java", "surfaceCreated()");
        if (gameLoop.getState().equals(Thread.State.TERMINATED)) {
            SurfaceHolder surfaceHolder = getHolder();
            surfaceHolder.addCallback(this);
            gameLoop = new GameLoop(this, surfaceHolder,context);
        }
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d("Game.java", "surfaceChanged()");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d("Game.java", "surfaceDestroyed()");
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        map.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(100);
        //canvas.d


        performance.draw(canvas);


        // Draw game objects
        //player.draw(canvas);
        menageEnemy.draw(canvas,map);

        for (Spell spell : spellList) {
            map.draw(canvas,spell);
        }



        // Draw Game over if the player is dead
        if (player.getHealthPoint() <= 0) {
            gameOver.draw(canvas);
        }
        if (openScene>0)
        {
            Paint p = new Paint();
            p.setColor(0xFF000000);
            canvas.drawRect(new Rect(0,0,canvas.getWidth()/30*openScene,canvas.getHeight()),p);
            openScene-=2;
        }



    }

    public void update() {

        // Stop updating the game if the player is dead
        if (player.getHealthPoint() <= 0) {
            return;
        }

        // Update game state
        player.update();



        // Update states of all enemies
        menageEnemy.update();

        // Update states of all spells
        Spell shoot = player.weapon.fire(player);
        if (shoot != null)
            spellList.add(shoot);

        for (Spell spell : spellList) {
            spell.update();
        }
        for (Iterator it = spellList.iterator(); it.hasNext();) {
            if (!((Spell)it.next()).validate()) {
                it.remove();
            }
        }

        menageEnemy.checkColl(spellList);
    }

    public void pause() {
        gameLoop.stopLoop();
    }
}
