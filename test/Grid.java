package test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Grid extends JPanel{

    private int width;
    private int height;
    private int xInterval;
    private int yInterval;
    private int rows;
    private int cols;
    private Color color;

    Grid(int w, int h, int row, int col, Color color){
        this.width = w;
        this.height = h;
        this.rows = row;
        this.cols = col;
        this.color = color;
        this.xInterval = w / col;
        this.yInterval = h / row;

    }


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setPaint(color);
        for (int i = 0; i <= cols; i++){
            g2D.setStroke(new BasicStroke(2));
            g2D.drawLine(i * xInterval, 0, i * xInterval, height);
        }
        for (int i = 0; i <= rows; i++){
            g2D.drawLine(0, i * yInterval, width,  i * yInterval);
        }
        
    
    }

    public Circle getLargestBall(ArrayList<Circle> balls){
        Circle largestD = null;
        for (Circle c : balls){
            if (largestD == null){
                largestD = c;
            } else if (c.radius > largestD.radius){
                largestD = c;
            }
            
        }
        return largestD;
    }

    public void placeBalls(ArrayList<Circle> balls, JFrame frame){

        int largestR = getLargestBall(balls).radius;
        System.out.println(largestR);

        for (Circle c : balls){
            System.out.println(c.getName());
            frame.add(c);
            frame.pack();
            
        }

        for (int i = 1; i <= balls.size(); i++){
            Circle c = balls.get(i - 1);
            c.setLocation(i*largestR+100, i*largestR+100);
            
            
            
            System.out.println(" placed");     
        }

        
    }
    
    
}
