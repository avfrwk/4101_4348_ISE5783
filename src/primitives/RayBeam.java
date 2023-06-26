package primitives;

public class RayBeam {
    /**the rays of the RayBeam*/
    private Ray[][] rays=null;
    private final Ray centralRay;
    private final static Vector randomVector=new Vector(1,0,0);
    private final static Vector randomVector2=new Vector(0,0,1);
    int amountSq;

    public int getAmountSq() {
        return amountSq;
    }

    /** Constructor to initialize RayBeam base on existing Ray
     * @param ray the ray
     * @param amountSq the squared root amount of rays
     * @throws IllegalArgumentException when amountSq is not positive*/
    public RayBeam(Ray ray,int amountSq,Point center, double width,double height){
        this.centralRay=ray;
        if(amountSq<=0)
            throw new IllegalArgumentException("amountSq is not positive");
        this.amountSq=amountSq;
        this.generateRays(center,width,height);
    }
    public Ray[][] getRays() {
        return this.rays;
    }

    /** generate the rays*/
    private void generateRays(Point center, double width,double height){
        //create orthogonal vector to represent "up"
        Vector up;
        double checkIfParallel=this.centralRay.getDir().dotProduct(randomVector);
        if(checkIfParallel==1||checkIfParallel==-1){
            up=this.centralRay.getDir().crossProduct(randomVector2).normalize().scale(height/amountSq);
        }else{
            up=this.centralRay.getDir().crossProduct(randomVector).normalize().scale(height/amountSq);
        }
        //create orthogonal vector to represent "right"
        Vector right= up.crossProduct(this.centralRay.getDir()).normalize().scale(width/amountSq);

        Point pi=center.add(right.scale(-amountSq/2))
                .add(up.scale(-amountSq/2));

        this.rays=new Ray[this.amountSq][this.amountSq];
        Point p0=this.centralRay.getP0();

        //create the rays
        for (int i=0;i<this.amountSq;++i,pi=pi.add(up)){
            Point pj=pi;
            for (int j=0;j<this.amountSq;++j,pj=pj.add(right)){
                this.rays[i][j]=new Ray(p0,pj.subtract(p0));
            }
        }
    }
    public static void main(String args[]){
        RayBeam rayBeam=new RayBeam(new Ray(Point.ZERO,new Vector(0,0,1)),1,
                new Point(0,0,2),7,7);
        Ray[][] rays=rayBeam.getRays();
        for(Ray[] i:rays){
            for(Ray j:i){
                System.out.println(j);
            }
        }
    }
}

