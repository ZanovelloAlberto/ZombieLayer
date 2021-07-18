package com.example.osterm.Menage;

import android.content.Context;
import android.graphics.Canvas;

import com.example.osterm.gameobject.Circle;
import com.example.osterm.gameobject.Enemy;
import com.example.osterm.gameobject.Player;
import com.example.osterm.gameobject.Spell;
import com.example.osterm.gamepanel.Map;


import org.w3c.dom.Node;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MenageEnemy {
    public Player player;
    public int round = 0;
    public int remain;
    public boolean pause;
    public int pauseRound;
    Context context;
    long timeToPathFind = 0;


    private List<Enemy> enemyList = new ArrayList<Enemy>();
    public MenageEnemy(Player player, Context context){
        this.player = player;
        this.context = context;

    }

    List<NodeWay> activeWay = new ArrayList<>();
    NodeWay[] ways = new NodeWay[20*20];

    public void spawnEnemy()
    {

        if (pause){
            if (((int) System.currentTimeMillis())>pauseRound){
                pause = false;
                round++;
                remain = (int) Math.sqrt(round)*10+round;
            }
        }else{
            if (remain>0) {
                if(Enemy.readyToSpawn(round)) {
                    Map.Pos p = player.map.SpawnEnemy();
                    enemyList.add(new Enemy(context, player,p.x,p.y,ways,round));
                    remain--;
                }
            }
            else
            {
                if (enemyList.isEmpty()){
                    pauseRound = ((int) System.currentTimeMillis())+10000;
                    pause = true;
                }
            }
        }
    }
    public void draw(Canvas canvas,Map map){
        for (Enemy enemy : enemyList) {
            map.draw(canvas,enemy);
        }
    }
    public class NodeWay{
        public NodeWay prev;
        public double cost;
        public int x,y;
        public NodeWay(int x, int y,double cost, NodeWay prev) {
            this.x = x;
            this.y = y;
            this.cost = cost;
            this.prev = prev;
        }
    }
    public void update()
    {
        spawnEnemy();
        if (System.currentTimeMillis()>timeToPathFind)
        {
            ways = new NodeWay[20*20];
            // Path finder algorithm

        if (enemyList.size()>0) {
            int x = (int) player.getPositionX() / 100;
            int y = (int) player.getPositionY() / 100;

            NodeWay a = new NodeWay(x, y, 0, null);
            activeWay.add(a);
            ways[x + y * 20] = a;

            List<NodeWay> sup = new ArrayList<>();
            while (activeWay.size() != 0) {
                for (NodeWay n : activeWay) {


                    NodeWay u;
                    //  X X X
                    //  X 0 0
                    //  X X 0
                    u = BuildWay(n.x + 1, n.y, n, 1);
                    if (u!=null)
                        sup.add(u);

                    //  X 0 0
                    //  X 0 0
                    //  X X 0
                    u = BuildWay(n.x, n.y - 1, n, 1);
                    if (u!=null)
                        sup.add(u);

                    //  0 0 0
                    //  0 0 0
                    //  X X 0
                    u = BuildWay(n.x - 1, n.y, n, 1);
                    if (u!=null)
                        sup.add(u);

                    //  0 0 0
                    //  0 0 0
                    //  0 0 0
                    u = BuildWay(n.x, n.y + 1, n, 1);
                    if (u!=null)
                        sup.add(u);

                    //diagonal(n,sup);
                }

                activeWay = sup;
                sup = new ArrayList<>();
            }
        }
        timeToPathFind = System.currentTimeMillis()+500;
        }
        for (Enemy enemy : enemyList) {
            enemy.ways = ways;
            enemy.update();

        }
    }



    public void diagonal(NodeWay n, List<NodeWay> sup)
    {
        //  X X X
        //  X 0 X
        //  X X 0
        NodeWay u;
        u = BuildWay(n.x + 1, n.y + 1, n, 1.41);
        if (u!=null)
            sup.add(u);
        //  X X 0
        //  X 0 0
        //  X X 0
        u = BuildWay(n.x + 1, n.y - 1, n, 1.41);
        if (u!=null)
            sup.add(u);
        //  0 0 0
        //  X 0 0
        //  X X 0
        u = BuildWay(n.x - 1, n.y - 1, n, 1.41);
        if (u!=null)
            sup.add(u);
        //  0 0 0
        //  0 0 0
        //  0 X 0
        u = BuildWay(n.x - 1, n.y + 1, n, 1.41);
        if (u!=null)
            sup.add(u);

    }
    private NodeWay BuildWay(int x, int y,NodeWay n,double cost)
    {
        if (player.map.passam((x)*100+50,(y)*100+50,true)){
            if (ways[x+(y)*20]==null){
                NodeWay a = new NodeWay(x,y,n.cost+cost,n);
                ways[x+y*20] = a;
                return a;

            }else if (ways[x+y*20].cost>n.cost+cost){
                NodeWay a = new NodeWay(x,y,n.cost+cost,n);
                ways[x+y*20] = a;
                return a;

            }
        }
        return null;
    }
    public void checkColl(List<Spell> spellList){
        // Iterate through enemyList and Check for collision between each enemy and the player and
        // spells in spellList.
        Iterator<Enemy> iteratorEnemy = enemyList.iterator();
        while (iteratorEnemy.hasNext()) {
            Enemy enemy = iteratorEnemy.next();
            if (Circle.isColliding(enemy, player)) {
                // Remove enemy if it collides with the player
                iteratorEnemy.remove();
                player.setHealthPoint(player.getHealthPoint() - 1);
                continue;
            }

            Iterator<Spell> iteratorSpell = spellList.iterator();
            while (iteratorSpell.hasNext()) {
                Spell spell = iteratorSpell.next();
                // Remove enemy if it collides with a spell
                if (Circle.isColliding(spell, enemy)) {
                    iteratorSpell.remove();
                    enemy.life-=spell.dmg;//spell power
                    if (enemy.life < 1)
                        iteratorEnemy.remove();
                    player.coins+= 15;
                    break;
                }
            }
        }
    }
}
