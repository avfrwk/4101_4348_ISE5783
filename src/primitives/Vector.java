package primitives;


/** This class represent a vector*/
public class Vector extends Point{
    public static Vector vectorX=new Vector(1,0,0);
    public static Vector vectorY=new Vector(0,1,0);
    public static Vector vectorZ=new Vector(0,0,1);
    /** Constructor to initialize Vector based on Double3 object
     * @param b the Double3 object that containing the Vector coordinates
     * @throws IllegalArgumentException when a zero vector is inserted*/
    Vector(Double3 b){
        super(b);
        if(this.xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("cannot assign 0 to vector");
    }
    /** Constructor to initialize Vector based on three number values
     * @param x first coordinate value
     * @param y second coordinate value
     * @param z third coordinate value
     * @throws IllegalArgumentException when a zero vector is inserted*/
    public Vector(double x,double y,double z){
        super(x,y,z);
        if(this.xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("cannot assign 0 to vector");
    }
    /** Constructor to initialize Vector based on String of 3 double, separated by spaces
     * @param str String of 3 double, separated by spaces*/
    public Vector (String str){
        super(str);
        if(this.xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("cannot assign 0 to vector");
    }
    /** add two vectors
     * @param b the another vector
     * @return  new vector that is the result adding*/
    @Override
    public Vector add(Vector b){
        return new Vector(this.xyz.add(b.xyz));
    }
    /** scaling the vector by scalar
     * @param r the scalar
     * @return  new vector that is the scaled vector*/
    public Vector scale(double r){
        return new Vector(this.xyz.scale(r));
    }
    /** doing dotProduct for two vectors
     * @param b the another vector
     * @return the result of the dotProduct*/
    public double dotProduct(Vector b){
        return this.xyz.d1 * b.xyz.d1 + this.xyz.d2 * b.xyz.d2 + this.xyz.d3 * b.xyz.d3;
    }
    /** doing crossProduct for two vectors
     * @param b the another vector
     * @return  new vector that is the result of the crossProduct*/
    public Vector crossProduct(Vector b){
        return new Vector(this.xyz.d2*b.xyz.d3-this.xyz.d3*b.xyz.d2,
                this.xyz.d3*b.xyz.d1-this.xyz.d1*b.xyz.d3,
                this.xyz.d1*b.xyz.d2-this.xyz.d2*b.xyz.d1
        );
    }
    /** length of the vector, squared
     * @return  the length of the vector, squared*/
    public double lengthSquared(){
        return this.dotProduct(this);
    }
    /** length of the vector
     * @return  the length of the vector*/
    public double length(){
        return Math.sqrt(this.lengthSquared());
    }
    /** normalizing the vector
     * @return  new normalized vector*/
    public Vector normalize(){
        double len=this.length();
        return new Vector(this.xyz.d1/len,this.xyz.d2/len,this.xyz.d3/len);
    }

    @Override
    public String toString() {
        return "Vector{" +
                "xyz=" + xyz +
                '}';
    }

}

