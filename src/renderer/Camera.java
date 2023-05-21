package renderer;

import primitives.*;

import java.util.MissingResourceException;

public class Camera {
    private Point location;
    private Vector vTo, vUp, vRight;
    private double vpDistance, vpHeight, vpWidth;
    private Point pc;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;
    /** construct Camera from location, and forward and up directions
     * @param location the location of the Camera
     * @param vTo the forward direction of the Camera
     * @param vUp the up direction of the
     * @throws IllegalArgumentException when {@param Vto} and {@param Vup} are not orthogonal*/
    public Camera(Point location, Vector vTo, Vector vUp){
        this.location=location;
        if(location==null|| vTo ==null|| vUp ==null){
            throw new IllegalArgumentException("one or more of the arguments are null");
        }
        if(Util.isZero(vTo.dotProduct(vUp))){
            this.vTo = vTo.normalize();
            this.vUp = vUp.normalize();
            this.vRight = vTo.crossProduct(vUp).normalize();
            this.vpDistance =this.vpHeight =this.vpWidth =-1;
            this.pc =null;
            this.imageWriter=null;
            this.rayTracer=null;
        }else{
            throw new IllegalArgumentException("Vto and Vup are not orthogonal");
        }
    }

     /**set the size of the view plane
     * @param width the width of the view plane
     * @param height the height of the view plane
     * @return the caller Camera*/
    public Camera setVPSize(double width, double height){
        if(width<=0||height<=0){
            throw new IllegalArgumentException("arguments needed be above then zero");
        }
        this.vpWidth =width;
        this.vpHeight =height;
        if(this.vpDistance !=-1){
            this.pc =this.location.add(this.vTo.scale(this.vpDistance));
        }
        return this;
    }

    /**set the distance from the camera to the view plane
     * @param distance the distance from the camera to the view plane
     * @return the caller Camera*/
    public Camera setVpDistance(double distance){
        if(distance<=0){
            throw  new IllegalArgumentException("distance needed be above then zero");
        }
        this.vpDistance =distance;
        if(this.vpWidth !=-1){
            this.pc =this.location.add(this.vTo.scale(this.vpDistance));
        }
        return this;
    }
    /**set the imageWriter of the camera
     * @param imageWriter the imageWriter of the camera
     * @return the caller Camera*/
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }
    /**set the rayTracerBase of the camera
     * @param rayTracer the ray tracer of the camera
     * @return the caller Camera*/
    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }
    /**render the Image*/
    public Camera renderImage(){
        if(this.pc ==null){
            throw new MissingResourceException("Camera's VPDistance or VPHeight or VPWidth are missing","Point","Pc");
        }if(this.imageWriter==null){
            throw new MissingResourceException("Camera's imageWriter is missing","ImageWriter","imageWriter");
        }if(this.rayTracer==null){
            throw new MissingResourceException("Camera's rayTracer is missing","RayTracerBase","rayTracer");
        }
        int nX=this.imageWriter.getNx();
        int nY=this.imageWriter.getNy();

        for(int i=0;i<nX;++i){
            for(int j=0;j<nY;++j){
                Ray ray = constructRay(nX, nY, i, j);
                Color color = castRay(ray);
                this.imageWriter.writePixel(i,j,color);
            }
        }
        return this;
    }

    private Color castRay(Ray ray) {
        Color color = this.rayTracer.traceRay(ray);
        return color;
    }

    /**draw a grid on the image
     * @param interval the interval of the grid
     * @param color the color of the grid*/
    public void printGrid(int interval,Color color){
        if(this.imageWriter==null){
            throw new MissingResourceException("Camera's imageWriter is missing","ImageWriter","imageWriter");
        }
        int pWidth=this.imageWriter.getNx(),pHeight=this.imageWriter.getNy();
        for(int i=0;i<pWidth;i++)
            for(int j=0;j<pHeight;j+=interval){
                this.imageWriter.writePixel(i,j,color);
            }
        for(int i=0;i<pHeight;i++)
            for(int j=0;j<pWidth;j+=interval){
                this.imageWriter.writePixel(j,i,color);
            }
    }
    /**
     * Function writeToImage produces unoptimized png file of the image according to
     * pixel color matrix in the directory of the project*/
    public void writeToImage(){
        this.imageWriter.writeToImage();
    }

    /** construct {@link primitives.Ray} from the camera to specific pixel
     * @param nX amount of pixels on x-axis of the view plane
     * @param nY amount of pixels on y-axis of the view plane
     * @param j the index of the pixel on x-axis that the ray will construct into
     * @param i the index of the pixel on y-axis that the ray will construct into
     * @return the {@link primitives.Ray} that constructed*/
    public Ray constructRay(int nX, int nY, int j, int i){
        double Rx= this.vpWidth /nX;
        double Ry= this.vpHeight /nY;
        double Yi=-(i-((double)nY-1)/2)*Ry;
        double Xj=(j-((double)nX-1)/2)*Rx;
        Point Pij=this.pc;
        if(!Util.isZero(Xj)) {
            Pij=Pij.add(this.vRight.scale(Xj));
        }if(!Util.isZero(Yi)) {
            Pij=Pij.add(this.vUp.scale(Yi));
        }
        return new Ray(this.location,Pij.subtract(this.location));
    }

}
