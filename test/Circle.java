package test;

import java.util.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;

import src.Vector;

public class Circle extends JPanel {

    private int x;
    private int y;
    private int m;
    private int radius;

    private Vector v;
    private Vector p;
    private Vector f;

    private Color c;

    private static final int K = 101;
    private static final int MASS = 10;

    public Circle( int radius) {
        this.radius = radius;
        this.m = MASS;
        this.v = new Vector(0, 0);
        this.p = new Vector(0,0);
        this.f = new Vector(0,0);
        this.c = Color.BLUE;
        this.setPreferredSize(new Dimension(2 * radius, 2 * radius));

    }

    public Circle(int radius, int mass, Vector V, Color color) {
        this.radius = radius;
        this.m = mass;
        this.v = V;
        this.x = 0;
        this.y = 0;
        this.f = new Vector(0,0);
        this.p = new Vector(m*v.getX(), m*v.getY());
        this.c = color;
    }

    public void setColor(Color color){
        c = color;
    }
    

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setPaint(c);
        g2D.fillOval(0, 0, 2*radius, 2*radius);
        g2D.setPaint(Color.BLACK);
        g2D.setStroke(new BasicStroke(1));
        g2D.drawOval(0, 0, 2*radius, 2*radius);
        
    
    }

    public void move(int dT, int WallX, int WallY, ArrayList<Circle> balls) {

        // bound issues
        // nested for loops for collison handling -> ~1300 objects before frame drops
        // https://www.youtube.com/watch?v=9IULfQH7E90
        // try above then try grid implementation

        int x = getLocation().x;
        int y = getLocation().y;
        
        if ((x + radius)>= WallX - radius || x < 0){
            p.setX(-p.getX());
        }
        if ((y + radius) >= WallY - radius || y < 0 ){
            p.setY(-p.getY());
            
        }

        // Collision handling


        // Update position

        x = (int) (p.getX() * dT)/m + x;
        y = (int) (p.getY() * dT)/m + y;
        setLocation(x, y);
        

        
    }

    // public void setColor()

    public static boolean collision(Circle c1, Circle c2){

        boolean check = false;
        // System.out.println("ball 2: " + new Point((int)x2 + c2.radius, (int)y2 + c2.radius).toString());
        double distance = distance(c1, c2);
        // System.out.println(distance);

        if (distance < (c1.radius + c2.radius)){
            check = true;
            // System.out.println('t');
        } else {
            check = false;
        }
            
        return check;
        
    }

    public static double distance(Circle c1, Circle c2){

        double x1 = c1.getLocation().getX();
        double y1 = c1.getLocation().getY();

        double x2 = c2.getLocation().getX();
        double y2 = c2.getLocation().getY();

        return Math.sqrt(((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)));

    }

    public static void solveCollision(int dT, Circle c1, Circle c2){

        // location of balls
        double x1 = c1.getLocation().getX();
        double y1 = c1.getLocation().getY();

        double x2 = c2.getLocation().getX();
        double y2 = c2.getLocation().getY();

        // distance vector and its norm
        Vector r = new Vector(x2 - x1, y2 - y1);
        Vector rUnit = new Vector((x2 - x1)/r.mag(), (y2 - y1)/r.mag());

        // calculating force after collision
        double f21x = K * (r.mag() - (c1.radius + c2.radius)) * rUnit.getX();
        double f21y = K * (r.mag() - (c1.radius + c2.radius)) * rUnit.getY();

        double f12x = -f21x;
        double f12y = -f21y;

        Vector f12 = new Vector (f12x, f12y);
        Vector f21 = new Vector (f21x, f21y);

        c1.setF(f21);
        c2.setF(f12);

        double p1x = c1.p.getX() + f21.getX()*dT;
        double p1y = c1.p.getY() + f21.getY()*dT;

        double p2x = c1.p.getX() + f12.getX()*dT;
        double p2y = c1.p.getY() + f12.getY()*dT;

        c1.setV(new Vector(p1x / c1.m, p1y / c1.m));
        c2.setV(new Vector(p2x / c2.m, p2y / c2.m));
    }

    public Vector getF() {
        return f;
    }

    public void setF(Vector f) {
        this.f = f;
    }
   

    public Vector getP() {
        return p;
    }

    public void setP(Vector p) {
        this.p = p;
    }



    public Vector getV() {
        return v;
    }



    public void setV(Vector v) {
        this.v = v;
        this.p = new Vector(v.getX()*m, v.getY()*m);
    }

}