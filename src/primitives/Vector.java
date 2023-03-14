package primitives;

public class Vector extends Point{
    Vector(Double3 b){
        super(b);
        if(this.xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("cannot assign 0 to vector");
    }
    public Vector(double x,double y,double z){
        super(x,y,z);
        if(this.xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("cannot assign 0 to vector");
    }
    public Vector add(Vector b){
        return new Vector(this.xyz.add(b.xyz));
    }
    public Vector scale(double r){
        return new Vector(this.xyz.scale(r));
    }
    public double dotProduct(Vector b){
        return this.xyz.d1 * b.xyz.d1 + this.xyz.d2 * b.xyz.d2 + this.xyz.d3 * b.xyz.d3;
    }
    public Vector crossProduct(Vector b){
        return new Vector(this.xyz.d2*b.xyz.d3-this.xyz.d3*b.xyz.d2,
                this.xyz.d3*b.xyz.d1-this.xyz.d1*b.xyz.d3,
                this.xyz.d1*b.xyz.d2-this.xyz.d2*b.xyz.d1
        );
    }
    public double lengthSquared(){
        return this.dotProduct(this);
    }
    public double length(){
        return Math.sqrt(this.lengthSquared());
    }
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

