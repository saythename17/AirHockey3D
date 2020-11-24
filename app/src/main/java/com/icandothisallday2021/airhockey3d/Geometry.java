package com.icandothisallday2021.airhockey3d;

public class Geometry {
    //⭐️
    public static class Point{
        public final float x, y, z;
        public Point(float x, float y, float z){
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Point translateY(float distance){
            return new Point(x,y + distance, z);
        }
    }

    //⭐️
    public static class Circle{
        public final Point center;
        public final float radius;

        public Circle(Point center, float radius){
            this.center = center;
            this.radius = radius;
        }

        public Circle scale(float scale){
            return new Circle(center, radius * scale);
        }
    }

    //⭐️
    public static class Cylinder{
        public final Point center;
        public final float radius;
        public final float height;

        public Cylinder(Point center, float radius, float height) {
            this.center = center;
            this.radius = radius;
            this.height = height;
        }
    }


}

/*You’ll probably have noticed that we’ve defined our geometry classes as immutable;
  whenever we make a change, we return a new object.
  This helps to make the code easier to work with and understand,
  but when you need top performance,
  you might want to stick with simple floating-point arrays and mutate them with static functions.*/
