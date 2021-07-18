package com.example.osterm.gamepanel;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.style.UpdateLayout;

import com.example.osterm.gameobject.GameObject;
import com.example.osterm.gameobject.Player;

public class Map {

    public Player player;
    Pos[] spawnPoints = new Pos[]{new Pos(1250,1150),new Pos(1850,950),new Pos(1050,1850),new Pos(150,1850),new Pos(150,850),new Pos(1750,150),new Pos(150,150)};
    Paint floor = new Paint();
    Paint door = new Paint();
    Paint wall = new Paint();
    public Button button;
    public int doorCost = 150;


    private void drawDoor(int x, int y, int price,Canvas canvas,boolean vertical)
    {
        canvas.drawRect(new Rect(x,y,x+100,y+100),floor);
        if (vertical)
            canvas.drawRect(new Rect(x+33,y,x+67,y+100),door);
        else
            canvas.drawRect(new Rect(x,y+33,x+100,y+67),door);
    }
    private void drawAmmBoost(int x, int y, int price,Canvas canvas,boolean vertical)
    {
        Paint paint = new Paint();
        canvas.drawRect(new Rect(x,y,x+100,y+100),floor);
        if (vertical)
            canvas.drawRect(new Rect(x+33,y,x+67,y+100),door);
        else
            canvas.drawRect(new Rect(x,y+33,x+100,y+67),door);
    }


    public Map(Player player){
        this.player = player;
        floor.setColor(Color.GRAY);
        door.setColor(Color.rgb(176, 94, 0));//Brown
        door.setTextSize(100);
        wall.setColor(Color.DKGRAY);
        button = new Button(1700,300,100,Color.WHITE);
    }

     public int[] map = {

            1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
            1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,1,
            1,1,1,3,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,
            1,0,0,0,0,1,0,0,0,0,0,0,1,1,1,0,0,0,0,1,
            1,0,0,0,0,1,0,0,0,1,0,0,1,1,1,1,0,0,0,1,
            1,0,0,1,1,1,0,0,0,1,0,0,1,1,1,1,0,0,0,1,
            1,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,0,1,
            1,0,0,0,0,2,0,0,0,1,0,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,1,0,0,0,1,1,1,0,0,0,0,1,0,0,1,
            1,0,0,0,0,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,
            1,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1,
            1,1,1,3,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1,
            1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,1,0,0,0,1,
            1,0,0,0,0,2,0,0,0,0,0,1,0,0,1,1,0,0,0,1,
            1,0,0,0,0,1,0,0,0,1,1,1,0,0,0,0,0,0,0,1,
            1,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0,0,0,1,
            1,0,0,0,0,0,0,0,0,2,0,0,0,0,1,0,0,0,0,1,
            1,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,
            1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,

    };
    //FLOOR 0
    //WALL 1
    //DOOR_VERTICAL 2
    //DOOR_HORIZONTAL 3


    public void draw(Canvas canvas, GameObject gameObject)
    {
            gameObject.draw(canvas,player.getPositionX(),player.getPositionY());
    }
    public void draw(Canvas canvas){

        int Xcamera = (int)player.getPositionX()-canvas.getWidth()/2;
        int Ycamera = (int)player.getPositionY()-canvas.getHeight()/2;

        canvas.drawColor(Color.BLACK);



        for (int x = 0; x< 20; x++){
            for(int y = 0; y < 20; y++){
                if (map[y*20+x]==0){
                    canvas.drawRect(new Rect(x*100-Xcamera,y*100-Ycamera,x*100+100-Xcamera,y*100+100-Ycamera),floor);
                }else if (map[y*20+x]==1){
                    canvas.drawRect(new Rect(x*100-Xcamera,y*100-Ycamera,x*100+100-Xcamera,y*100+100-Ycamera),wall);
                }
                else if (map[y*20+x]==2){
                    drawDoor(x*100-Xcamera,y*100-Ycamera,200,canvas,true);
                }
                else if (map[y*20+x]==3){
                    drawDoor(x*100-Xcamera,y*100-Ycamera,200,canvas,false);
                }
            }
        }
        //canvas.drawText(uno+"",400,100,door);
        //canvas.drawText(due+"",800,100,door);


        if (interactWithTile(player.getPositionX(),player.getPositionY(),button.isPressed)){
            button.draw(canvas,doorCost);
        }
        button.isPressed = false;





        player.draw(canvas,player.getPositionX(),player.getPositionY());
    }

     public boolean passam(double x, double y,boolean zombie){
        x = x/100;
        y = y/100;
        if (map[(int)y*20+(int)x]==0)
            return true;
        if (map[(int)y*20+(int)x]==2) {
            if ((x % 1 < 0.33 || x % 1 > 0.67)||zombie) {
                return true;
            }
        }
        if (map[(int)y*20+(int)x]==3) {
            if ((y % 1 < 0.33 || y % 1 > 0.67)||zombie) {
                return true;
            }
        }
        return false;
    }
    public boolean interactWithTile(double x,double y,boolean agisci)
    {
        x = x/100;
        y = y/100;
        switch (map[(int)y*20+(int)x])
        {
            case 2:
            case 3: {
                if (agisci)
                    if (player.coins>= doorCost){
                        map[(int)y*20+(int)x] = 0;
                        player.coins-= doorCost;
                        doorCost+=(doorCost/150)*100;
                    }

                return true;
            }
        }
        return false;
    }
    public static class Pos
    {
        public int x;
        public int y;

        public Pos(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    public Pos SpawnEnemy()
    {
        int n = (int)(Math.random()*spawnPoints.length);
        Pos uno = spawnPoints[n];
        return uno;
    }

}