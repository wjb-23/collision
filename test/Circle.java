package test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import src.Vector;

public class Circle extends JPanel {
    private double m;
    public int radius;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    private Vector v;
    private Vector p;

    private Color c;

    private static final int K = 30;
    private static final int MASS = 10;

    public Circle(int radius, String name) {
        this.radius = radius;
        this.m = MASS;
        this.v = new Vector(0, 0);
        this.p = new Vector(0,0);
        this.c = Color.BLUE;
        this.name = name;
        this.setPreferredSize(new Dimension(2 * radius, 2 * radius));

    }

    public Circle(int radius) {
        this.radius = radius;
        this.m = MASS;
        this.v = new Vector(0, 0);
        this.p = new Vector(0,0);
        this.c = Color.BLUE;
        this.name = "circle";
        this.setPreferredSize(new Dimension(2 * radius, 2 * radius));

    }

    public Circle(int radius, double mass, Vector V, Color color, String name) {
        this.radius = radius;
        this.m = mass;
        this.v = V;
        // this.f = new Vector(0,0);
        this.p = new Vector(m*v.getX(), m*v.getY());
        this.c = color;
        this.name = name;
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

    public void move(double dT, int WallX, int WallY) {

        // bound issues
        // nested for loops for collison handling -> ~1300 objects before frame drops
        // https://www.youtube.com/watch?v=9IULfQH7E90
        // try above then try grid implementation

        int x = getLocation().x;
        int y = getLocation().y;

        // Wall Collision handling

        if ((x + radius)>= WallX - radius || x < 0){
            p.setX(-p.getX());
        }
        if ((y + radius) >= WallY - radius || y < 0 ){
            p.setY(-p.getY());
            
        }

        // Update position

        int xf = (int) (p.getX() * dT/m) + x;
        int yf = (int) (p.getY() * dT/m) + y;
        
        setLocation(xf, yf);
        
    }

    // public void setColor()

    public static boolean collision(Circle c1, Circle c2){

        boolean check = false;
        double distance = distance(c1, c2);
        // System.out.println(distance);

        if (distance < (c1.radius + c2.radius)){
            check = true;
        } else {
            check = false;
        }
            
        return check;
        
    }

    public static double distance(Circle c1, Circle c2){
        Vector r = createDistanceVector(c1, c2);
        return r.mag();

    }

    public static Vector createDistanceVector(Circle c1, Circle c2){

        double x1 = c1.getLocation().getX();
        double y1 = c1.getLocation().getY();

        double x2 = c2.getLocation().getX();
        double y2 = c2.getLocation().getY();
        

        return new Vector(x2 - x1, y2 - y1);

    }

    public static void solveSpringCollision(double dT, Circle c1, Circle c2){

        int x1 = c1.getLocation().x;
        int y1 = c1.getLocation().y;

        int x2 = c2.getLocation().x;
        int y2 = c2.getLocation().y;

        // distance vector and its norm
        Vector r = createDistanceVector(c1, c2);
        Vector rUnit = r.norm();

        if (r.mag() < c1.radius + c2.radius){
        // calculating force after collision
            double f21x = K * (r.mag() - (c1.radius + c2.radius)) * rUnit.getX();
            double f21y = K * (r.mag() - (c1.radius + c2.radius)) * rUnit.getY();


            double f12x = -f21x;
            double f12y = -f21y;

            Vector f12 = new Vector (f12x, f12y);
            Vector f21 = new Vector (f21x, f21y);

            double p1x = c1.p.getX() + (f21.getX() * dT) / c1.m;
            double p1y = c1.p.getY() + (f21.getY() * dT) / c1.m;
            
            double p2x = c2.p.getX() + (f12.getX() * dT) / c2.m;
            double p2y = c2.p.getY() + (f12.getY() * dT) / c2.m;
            

            Vector p1 = new Vector(p1x, p1y);
            Vector p2 = new Vector(p2x, p2y);

            c1.v = p1.mult(1/c1.m);
            c2.v = p2.mult(1/c2.m);

            c1.p = p1;
            c2.p = p2;

        }

        // ball 1

        int x1f = (int) (c1.p.getX() * dT/c1.m) + (int) x1;
        int y1f = (int) (c1.p.getY() * dT/c1.m) + (int) y1;
        c1.setLocation(x1f, y1f);
        
        // ball 2

        int x2f = (int) (c2.p.getX() * dT/c2.m) + (int) x2;
        int y2f = (int) (c2.p.getY() * dT/c2.m) + (int) y2;
        c2.setLocation(x2f, y2f);
        
    }

    public static void solveCollision(double dT, Circle c1, Circle c2){


        int x1 = c1.getLocation().x;
        int y1 = c1.getLocation().y;

        int x2 = c2.getLocation().x;
        int y2 = c2.getLocation().y;

        Vector r21 = createDistanceVector(c1, c2);
        Vector r12 = createDistanceVector(c2, c1);

        // k_o = 2*m_o/m_i + m_o,  o = observer, i = incoming 
        double k1 = 2 * c2.m / (c1.m + c2.m);
        double k2 = 2 * c2.m / (c1.m + c2.m);

        // v_o,f = v_o - u_f, v_o = final velocity of observer
        // u_f,o = dot(v_o - v_i, r_o - r_i) / ((r_o - r_i).mag())^2) * (r_o - r_i), u_f, o = update factor of observer

        Vector updateFactor1 = r12.mult(k1 * Vector.dot(c1.getV().sub(c2.getV()), r12)/(r12.mag() * r12.mag()));
        Vector updateFactor2 = r21.mult(k2 * Vector.dot(c2.getV().sub(c1.getV()), r21)/(r21.mag() * r21.mag()));
        
        Vector v1f = c1.getV().sub(updateFactor1);
        Vector v2f = c2.getV().sub(updateFactor2);

        c1.setV(v1f);
        c2.setV(v2f);

        

        // // ball 1

        // int x1f = (int) (c1.p.getX() * dT/c1.m) + (int) x1;
        // int y1f = (int) (c1.p.getY() * dT/c1.m) + (int) y1;
        // c1.setLocation(x1f, y1f);
        
        // // ball 2

        // int x2f = (int) (c2.p.getX() * dT/c2.m) + (int) x2;
        // int y2f = (int) (c2.p.getY() * dT/c2.m) + (int) y2;
        // c2.setLocation(x2f, y2f);
        
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