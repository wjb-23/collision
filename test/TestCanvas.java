package test;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

import src.Vector;

public class TestCanvas extends JPanel implements ActionListener{

    private static final int HEIGHT = 400;
    private static final int WIDTH = 400;
    private static final int RADIUS = 30;
    public int t = 0;
    public int dT = 1;

    private static final Vector ZERO = new Vector(0, 0);

    public ArrayList<Circle> balls;

    Timer timer;
    ArrayList<Color> colors;

    Vector v1 = new Vector(1, 3);
    Vector v2 = new Vector(-5, 2);
    Vector v3 = new Vector(2, 2);

    Circle c = new Circle(RADIUS, 10, v1, Color.RED, "c");
    Circle c2 = new Circle(20, "c2");
    Circle c3 = new Circle(20, "c3");
    Circle c4 = new Circle(20);
    // Circle c5 = new Circle(20);

    Grid g = new Grid(WIDTH, HEIGHT, 10, 10, Color.BLACK);    

    TestCanvas(){
        // count = 0;
        balls = new ArrayList<>();
        balls.add(c);
        balls.add(c2);
        balls.add(c3);
        balls.add(c4);
        // balls.add(c5);
        Color[] color = {Color.BLUE, Color.BLACK, Color.ORANGE, Color.ORANGE , Color.CYAN, Color.LIGHT_GRAY};
        colors = new ArrayList<>(Arrays.asList(color));
        JFrame frame = new JFrame("Moving Circles");
        c2.setV(v2);
        c3.setV(v3);
        c4.setV(v3);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setSize(WIDTH, HEIGHT);
        
    
        frame.setVisible(true);
        timer = new Timer(40, this);
        timer.start();

        for (Circle c : balls){
            frame.add(c);
            frame.pack();
        }

        frame.add(g);
        frame.pack();

        c2.setLocation(100,100);
        c3.setLocation(200,100);
        c4.setLocation(250,100);
        // c5.setLocation(200,150);


        

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Circle c : balls){
            c.move(1, WIDTH, HEIGHT);
        }
        for (Circle c : balls){
            for (Circle b : balls){
                if (c != b)
                    if (Circle.collision(c, b)){
                        Circle.solveCollision(1, c, b);
                    }
            }  
            
        }

    }

    public void changeColor(Circle c){
        

    }

    public static void main(String[] args) {
        new TestCanvas();
 
 
     }

        
}



