package test;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

import src.Vector;

public class TestCanvas extends JPanel implements ActionListener{

    private static final int HEIGHT = 300;
    private static final int WIDTH = 300;
    private static final int RADIUS = 30;
    private static final Vector ZERO = new Vector(0, 0);

    public ArrayList<Circle> balls;

    Timer timer;
    ArrayList<Color> colors;

    Vector v1 = new Vector(1, 3);
    Vector v2 = new Vector(-5, 2);
    Vector v3 = new Vector(2, 1);

    Circle c = new Circle(RADIUS, 5, v1, Color.RED);
    Circle c2 = new Circle(20);
    Circle c3 = new Circle(20);
    Circle c4 = new Circle(20);
    Circle c5 = new Circle(20);

    Grid g = new Grid(WIDTH, HEIGHT, 10, 10);

    float COOLDOWN_TIME = 10; //in seconds

    private float cooldown = 0;
    

    TestCanvas(){
        balls = new ArrayList<>();
        balls.add(c);
        balls.add(c2);
        balls.add(c3);
        // balls.add(c4);
        // balls.add(c5);
        Color[] color = {Color.BLUE, Color.BLACK, Color.ORANGE, Color.ORANGE , Color.CYAN, Color.LIGHT_GRAY};
        colors = new ArrayList<>(Arrays.asList(color));
        JFrame frame = new JFrame("Moving Circles");
        c2.setV(v2);
        c3.setV(v3);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setSize(WIDTH, HEIGHT);
        
    
        frame.setVisible(true);
        timer = new Timer(30, this);
        timer.start();

        for (Circle c : balls){
            frame.add(c);
            frame.pack();
        }

        frame.add(g);
        frame.pack();

        c2.setLocation(100,100);
        c3.setLocation(200,100);
        // c4.setLocation(250,100);
        // c5.setLocation(200,150);

        

    }

    public void Update(float time){
            cooldown -= time;
    }

    public boolean attack(){
        if(cooldown <= 0){
            cooldown = COOLDOWN_TIME; 
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Circle c : balls){
            c.move(1, WIDTH, HEIGHT, balls);
        }
        for (Circle c : balls){
            for (Circle b : balls){
                if (Circle.collision(c, b)){
                    
                }         
            }
        }

        // System.out.println(Circle.distance(c,c2));

        repaint();
    }

    public void changeColor(Circle c){
        

    }

    public static void main(String[] args) {
        new TestCanvas();
 
 
     }

        
}



