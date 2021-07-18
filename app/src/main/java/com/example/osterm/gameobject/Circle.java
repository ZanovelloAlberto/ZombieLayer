package com.example.osterm.gameobject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Circle is an abstract class which implements a draw method from GameObject for drawing the object
 * as a circle.
 */
public abstract class Circle extends GameObject {
    protected double radius;
    public Paint paint;

    public Circle(int color, double positionX, double positionY, double radius) {
        super(positionX, positionY);
        this.radius = radius;

        // Set colors of circle
        paint = new Paint();
        paint.setColor(color);
    }

    /**
     * isColliding checks if two circle objects are colliding, based on their positions and radii.
     * @param obj1
     * @param obj2
     * @return
     */
    public static boolean isColliding(Circle obj1, Circle obj2) {
        double distance = getDistanceBetweenObjects(obj1, obj2);
        double distanceToCollision = obj1.getRadius() + obj2.getRadius();
        if (distance < distanceToCollision)
            return true;
        else
            return false;
    }

    public void draw(Canvas canvas, double x , double y) {
        canvas.drawCircle((float)(positionX-x+canvas.getWidth()/2), (float) (positionY-y+canvas.getHeight()/2), (float) radius, paint);
    }

    public double getRadius() {
        return radius;
    }
}