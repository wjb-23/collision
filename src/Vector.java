package src;

public class Vector {
    private double x;
    private double y;
  
    public Vector(double X, double Y){
        this.x = X;
        this.y = Y;
    }
    
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double mag(){
        return Math.sqrt(x*x + y*y);
    }

    public Vector norm(){
        return new Vector(x/this.mag(), y/this.mag());
    }

    public Vector mult(double scalar){
        return new Vector(x*scalar, y*scalar);
    }

    public Vector mult(Vector v){
        return new Vector(x * v.getX(), y* v.getY());
    }


    public Vector add(Vector v){
        return new Vector(x + v.getX(), y + v.getY());
    }

    public Vector sub(Vector v){
        return new Vector(x - v.getX(), y - v.getY());
    }

    public static double dot(Vector v1, Vector v2){
        return (v1.getX() * v2.getX()) + (v1.getY() * v2.getY());
    }


    

}
