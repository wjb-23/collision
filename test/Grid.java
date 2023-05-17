package test;

import java.util.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;

import src.Vector;

public class Grid extends JPanel{

    private int width;
    private int height;
    private int xInterval;
    private int yInterval;
    private int rows;
    private int cols;

    Grid(int w, int h, int row, int col){
        this.width = w;
        this.height = h;
        this.rows = row;
        this.cols = col;
        this.xInterval = w / col;
        this.yInterval = h / row;

    }


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setPaint(Color.black);
        for (int i = 0; i <= cols; i++){
            g2D.drawLine(i * xInterval, 0, i * xInterval, height);
        }
        for (int i = 0; i <= rows; i++){
            g2D.drawLine(0, i * yInterval, width,  i * yInterval);
        }
        
    
    }
    
}
