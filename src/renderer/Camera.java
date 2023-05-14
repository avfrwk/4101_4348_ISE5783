package renderer;

import primitives.*;

import java.util.MissingResourceException;

public class Camera {
    private Point location;
    private Vector Vto,Vup,Vright;
    private double VPDistance,VPHeight,VPWidth;
    private Point Pc;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;
    /** construct Camera from location, and forward and up directions
     * @param location the location of the Camera
     * @param Vto the forward direction of the Camera
     * @param Vup the up direction of the
     * @throws IllegalArgumentException when {@param Vto} and {@param Vup} are not orthogonal*/
    public Camera(Point location,Vector Vto,Vector Vup){
        this.location=location;
        if(location==null||Vto==null||Vup==null){
            throw new IllegalArgumentException("one or more of the arguments are null");
        }
        if(Util.isZero(Vto.dotProduct(Vup))){
            this.Vto=Vto.normalize();
            this.Vup=Vup.normalize();
            this.Vright=Vto.crossProduct(Vup).normalize();
            this.VPDistance=this.VPHeight=this.VPWidth=-1;
            this.Pc=null;
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
        this.VPWidth=width;
        this.VPHeight=height;
        if(this.VPDistance!=-1){
            this.Pc=this.location.add(this.Vto.scale(this.VPDistance));
        }
        return this;
    }

    /**set the distance from the camera to the view plane
     * @param distance the distance from the camera to the view plane
     * @return the caller Camera*/
    public Camera setVPDistance(double distance){
        if(distance<=0){
            throw  new IllegalArgumentException("distance needed be above then zero");
        }
        this.VPDistance=distance;
        if(this.VPWidth!=-1){
            this.Pc=this.location.add(this.Vto.scale(this.VPDistance));
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
        if(this.Pc==null){
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
        int Pwidth=this.imageWriter.getNx(),Pheight=this.imageWriter.getNy();
        for(int i=0;i<Pwidth;i++)
            for(int j=0;j<Pheight;j+=interval){
                this.imageWriter.writePixel(i,j,color);
            }
        for(int i=0;i<Pheight;i++)
            for(int j=0;j<Pwidth;j+=interval){
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
     * @param Nx amount of pixels on x-axis of the view plane
     * @param Ny amount of pixels on y-axis of the view plane
     * @param j the index of the pixel on x-axis that the ray will construct into
     * @param i the index of the pixel on y-axis that the ray will construct into
     * @return the {@link primitives.Ray} that constructed*/
    public Ray constructRay(int Nx, int Ny, int j, int i){
        double Rx= this.VPWidth/Nx;
        double Ry= this.VPHeight/Ny;
        double Yi=-(i-((double)Ny-1)/2)*Ry;
        double Xj=(j-((double)Nx-1)/2)*Rx;
        Point Pij=this.Pc;
        if(!Util.isZero(Xj)) {
            Pij=Pij.add(this.Vright.scale(Xj));
        }if(!Util.isZero(Yi)) {
            Pij=Pij.add(this.Vup.scale(Yi));
        }
        return new Ray(this.location,Pij.subtract(this.location));
    }

}
