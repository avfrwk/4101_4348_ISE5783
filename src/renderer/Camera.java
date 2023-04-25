package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

public class Camera {
    private Point location;
    private Vector Vto,Vup,Vright;
    private double VPDistance,VPHeight,VPWidth;
    private Point Pc;

    /** construct Camera from location, and forward and up directions
     * @param location the location of the Camera
     * @param Vto the forward direction of the Camera
     * @param Vup the up direction of the
     * @throws IllegalArgumentException when {@param Vto} and {@param Vup} are not orthogonal*/
    public Camera(Point location,Vector Vto,Vector Vup){
        this.location=location;
        if(Util.isZero(Vto.dotProduct(Vup))){
            this.Vto=Vto.normalize();
            this.Vup=Vup.normalize();
            this.Vright=Vto.crossProduct(Vup).normalize();
            this.VPDistance=this.VPHeight=this.VPWidth=-1;
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
