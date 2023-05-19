package test;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

import src.Vector;

public class TestCanvas extends JPanel implements ActionListener{

    private static final int HEIGHT = 600;
    private static final int WIDTH = 600;
    private static final int RADIUS = 30;

    public int t = 0;
    public int dT = 1;

    public ArrayList<Circle> balls;
    public ArrayList<Color> colors;

    public JFrame frame;
    public Timer timer;
    

    Vector v1 = new Vector(1, 3);
    Vector v2 = new Vector(-5, 2);
    Vector v3 = new Vector(-5, -2);
    Vector v4 = new Vector(-2, -3);

    Circle c = new Circle(RADIUS, 10, new Vector(2,0), Color.RED, "c");
    Circle c2 = new Circle(25, "c2", Color.MAGENTA);
    Circle c3 = new Circle(22, "c3", Color.CYAN);
    Circle c4 = new Circle(20);
    Circle c5 = new Circle(20);
    Circle c6 = new Circle(25, "c2", Color.MAGENTA);
    Circle c7 = new Circle(42, "c5", Color.CYAN);
    Circle c8 = new Circle(20);
    Circle c9 = new Circle(20);

    public Grid g;    

    TestCanvas(){
        balls = new ArrayList<>();
        balls.add(c);
        balls.add(c2);
        // balls.add(c3);
        // balls.add(c4);
        // balls.add(c5);
        // balls.add(c6);
        // balls.add(c7);
        // balls.add(c8);
        // balls.add(c5);
        Color[] color = {Color.BLUE, Color.BLACK, Color.ORANGE, Color.ORANGE , Color.CYAN, Color.LIGHT_GRAY};
        colors = new ArrayList<>(Arrays.asList(color));
        frame = new JFrame("Elastic Collision");
        g = new Grid(WIDTH - 200, HEIGHT - 200, 10, 10, Color.BLACK);
        // c2.setV(v2);
        // c3.setV(v3);
        // c4.setV(v4);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setSize(WIDTH, HEIGHT);
    
        frame.setVisible(true);
        timer = new Timer(40, this);
        timer.start();


        
        g.placeBalls(balls, frame);

        frame.add(g);
        frame.pack();


    }

    // @Override
    public void actionPerformed(ActionEvent e) {
        for (Circle c : balls){
            c.move(1, WIDTH, HEIGHT);
        }
        for (Circle c : balls){
            for (Circle b : balls){
                if (c != b)
                    if (Circle.collision(c, b)){
                        System.out.println();
                        // Circle.solveCollision1(1, c, b);
                        Circle.solveSpringCollision(1, c, b);
                    }
            }  
            
        }
        repaint();

    }

    public void generateBalls(int n, ArrayList<Color> color){
        for (int i = 0; i <= n; i++){
            balls.add(new Circle(RADIUS, "circle" + n, color.get(i-1))); 
        }
    }

    public static void main(String[] args) {
        new TestCanvas();
        
     }

        
}



