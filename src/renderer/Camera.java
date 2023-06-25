package renderer;

import primitives.*;

import java.util.LinkedList;
import java.util.MissingResourceException;
import java.util.stream.*;
public class Camera {
    private Point location;
    private Vector vTo, vUp, vRight;
    private double vpDistance, vpHeight, vpWidth;
    private Point pc;
    private ImageWriter imageWriter;
    private VideoWriter videoWriter;
    private RayTracerBase rayTracer;
    private PixelManager pixelManager;
    private boolean antiAliasingOn =false;
    private int antiAliasingRaysAmountSq=-1;
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
    /** construct Camera from location and target
     * @param location the location of the Camera
     * @param target the forward direction of the Camera
     * @throws IllegalArgumentException when {@param location} and {@param target} are at the same point*/
    public Camera(Point location,Point target){
        this.location=location;
        this.vTo=target.subtract(location).normalize();
        if(vTo.equals(Vector.vectorY)){
            this.vUp=Vector.vectorZ;
        }else{
            this.vUp= this.vTo.crossProduct(
                    this.vTo.crossProduct(Vector.vectorY)
            ).normalize();
        }
        this.vRight= vTo.crossProduct(vUp).normalize();
        this.vpDistance =this.vpHeight =this.vpWidth =-1;
        this.pc =null;
        this.imageWriter=null;
        this.rayTracer=null;
    }
    /** rotating the camera
     * @param angle the angles of the rotation
     * @return this camera*/
    public Camera rotateCamera(double angle){
        this.vUp=Util.rotateAroundVector(this.vTo,this.vUp,angle).normalize();
        this.vRight= vTo.crossProduct(vUp).normalize();
        this.pc =this.location.add(this.vTo.scale(this.vpDistance));
        return this;
    }
    /** rotating the camera around vUp
     * @param angle the angles of the rotation
     * @return this camera*/
    public Camera rotateCameraAroundVup(double angle){
        this.vTo=Util.rotateAroundVector(this.vUp,this.vTo,angle).normalize();
        this.vRight= vTo.crossProduct(vUp).normalize();
        this.pc =this.location.add(this.vTo.scale(this.vpDistance));
        return this;
    }
    /** rotating the camera around vTo
     * @param angle the angles of the rotation
     * @return this camera*/
    public Camera rotateCameraArundVright(double angle){
        this.vUp=Util.rotateAroundVector(this.vRight,this.vUp,angle).normalize();
        this.vTo= vUp.crossProduct(vRight).normalize();
        this.pc =this.location.add(this.vTo.scale(this.vpDistance));
        return this;
    }
    /** rotating the camera around vUp Vto
     * @param angle the angles of the rotation
     * @return this camera*/
    public Camera rotateCameraAroundVto(double angle){
        return this.rotateCamera(angle);
    }
     /** rotate the camera around target point
     * @param target the target of the camera
     * @param angle the angle to rotate
     * @return this camera*/
    public Camera rotateCameraAroundPointVup(Point target,double angle){
        this.location=Util.rotatePointAroundVector(vUp,target,location,angle);
        this.vTo=Util.rotateAroundVector(vUp,vTo,angle).normalize();
        this.vRight= vTo.crossProduct(vUp).normalize();
        this.pc =this.location.add(this.vTo.scale(this.vpDistance));
        return this;
    }
    /** rotate the camera around target point
     * @param target the target of the camera
     * @param angle the angle to rotate
     * @return this camera*/
    public Camera rotateCameraAroundPointVright(Point target,double angle){
        this.location=Util.rotatePointAroundVector(vRight,target,location,angle);
        this.vTo=Util.rotateAroundVector(vRight,vTo,angle);
        this.vUp= vRight.crossProduct(vTo).normalize();
        this.pc =this.location.add(this.vTo.scale(this.vpDistance));
        return this;
    }
    /** rotate the camera around target point
     * @param target the target of the camera
     * @param rotator the rotator vector of the camera
     * @param angle the angle to rotate
     * @return this camera*/
    public Camera rotateCameraAroundPointVector(Point target,Vector rotator,double angle){
        this.location=Util.rotatePointAroundVector(rotator,target,location,angle);
        this.vTo=Util.rotateAroundVector(rotator,vTo,angle);
        this.vUp=Util.rotateAroundVector(rotator,vUp,angle);
        this.vRight= vTo.crossProduct(vUp).normalize();
        this.pc =this.location.add(this.vTo.scale(this.vpDistance));
        return this;
    }
    /** moving the camera forward
     * @param distance the distance to move
     * @return this camera*/
    public Camera moveForward(double distance){
        this.location=this.location.add(this.vTo.scale(distance));
        this.pc =this.location.add(this.vTo.scale(this.vpDistance));
        return this;
    }
    /** moving the camera right
     * @param distance the distance to move
     * @return this camera*/
    public Camera moveRight(double distance){
        this.location=this.location.add(this.vRight.scale(distance));
        this.pc =this.location.add(this.vTo.scale(this.vpDistance));
        return this;
    }
    /** moving the camera up
     * @param distance the distance to move
     * @return this camera*/
    public Camera moveUp(double distance){
        this.location=this.location.add(this.vUp.scale(distance));
        this.pc =this.location.add(this.vTo.scale(this.vpDistance));
        return this;
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
    /**set the the camera to a new location, the given point-vector*distance, and the vto will be v
     * @param point the guide point
     * @param v the guide vector
     * @param distance the distance to multiply v
     * @return the caller Camera*/
    public Camera setLocation(Point point,Vector v,double distance){
        this.location=point.add(v.scale(-distance));
        this.vTo=v.normalize();
        try{
            this.vRight=vTo.crossProduct(Vector.vectorZ).normalize();
        }catch (Exception e){
            this.vRight=vTo.crossProduct(Vector.vectorX).normalize();
        }
        this.vUp= vRight.crossProduct(vTo).normalize();
        this.pc =this.location.add(this.vTo.scale(this.vpDistance));
        return this;
    }
    /**set the imageWriter of the camera
     * @param imageWriter the imageWriter of the camera
     * @return the caller Camera*/
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }
    /**set the VideoWriter of the camera
     * @param videoWriter the imageWriter of the camera
     * @return the caller Camera*/
    public Camera setVideoWriter(VideoWriter videoWriter) {
        this.videoWriter = videoWriter;
        return this;
    }
    public boolean isAntiAliasingOn(){
        return this.antiAliasingOn;
    }
    /**turn on the anti aliasing and set the amount of rays
     * @param antiAliasingRaysAmountSq the square root of amount of rays
     * @throws IllegalArgumentException when amount is not 2^k+1 number
     * @return this camera*/
    public Camera turnOnAntiAliasing(int antiAliasingRaysAmountSq){
        if((Math.log(antiAliasingRaysAmountSq-1)/Math.log(2))%1!=0){
            throw new IllegalArgumentException("the amount is not 2^k+1 number");
        }
        this.antiAliasingOn =true;
        this.antiAliasingRaysAmountSq=antiAliasingRaysAmountSq;
        return this;
    }
    public Camera turnOffAntiAliasing(){
        this.antiAliasingOn =false;
        antiAliasingRaysAmountSq=-1;
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

        pixelManager = new PixelManager(nY, nX, 0.1);
        int threadsCount =Runtime.getRuntime().availableProcessors();

        if(threadsCount==0){
            for(int i=0;i<nX;++i){
                for(int j=0;j<nY;++j){
                    Color color;
                    if(this.antiAliasingOn){
                        RayBeam rayBeam=constructRays(nX, nY, i, j);
                        color = castRays(rayBeam);
                    }else{
                        Ray ray=constructRay(nX, nY, i, j);
                        color = castRay(ray);
                    }
                    this.imageWriter.writePixel(i,j,color);
                }
            }
        }else {
            var threads = new LinkedList<Thread>(); // list of threads
            while (threadsCount-- > 0) // add appropriate number of threads
                threads.add(new Thread(() -> { // add a thread with its code
                    PixelManager.Pixel pixel; // current pixel(row,col)
                    // allocate pixel(row,col) in loop until there are no more pixels
                    while ((pixel = pixelManager.nextPixel()) != null){
                        Color color;
                        if(this.antiAliasingOn){
                            RayBeam rayBeam=constructRays(nX, nY, pixel.col(), pixel.row());
                            color = castRays(rayBeam);
                        }else{
                            Ray ray=constructRay(nX, nY, pixel.col(), pixel.row());
                            color = castRay(ray);
                        }
                        this.imageWriter.writePixel(pixel.col(),pixel.row(),color);
                    }
                }));
            // start all the threads
            for (var thread : threads) thread.start();
            // wait until all the threads have finished
            try { for (var thread : threads) thread.join(); } catch (InterruptedException ignore) {}
        }
        return this;
    }

    /** construct {@link primitives.Point} from the camera to center of specific pixel
     * @param nX amount of pixels on x-axis of the view plane
     * @param nY amount of pixels on y-axis of the view plane
     * @param j the index of the pixel on x-axis that the ray will construct into
     * @param i the index of the pixel on y-axis that the ray will construct into
     * @return the {@link primitives.Point} that constructed*/
    public Point constructPixelPoint(int nX, int nY, int j, int i){
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
        return Pij;
    }
    /** construct {@link primitives.Ray} from the camera to specific pixel
     * @param nX amount of pixels on x-axis of the view plane
     * @param nY amount of pixels on y-axis of the view plane
     * @param j the index of the pixel on x-axis that the ray will construct into
     * @param i the index of the pixel on y-axis that the ray will construct into
     * @return the {@link primitives.Ray} that constructed*/
    public Ray constructRay(int nX, int nY, int j, int i){
        Point Pij=constructPixelPoint(nX, nY, j, i);
        return new Ray(this.location,Pij.subtract(this.location));
    }
    /** construct {@link primitives.RayBeam} from the camera to specific pixel
     * @param nX amount of pixels on x-axis of the view plane
     * @param nY amount of pixels on y-axis of the view plane
     * @param j the index of the pixel on x-axis that the ray will construct into
     * @param i the index of the pixel on y-axis that the ray will construct into
     * @return the {@link primitives.RayBeam} that constructed*/
    public RayBeam constructRays(int nX, int nY, int j, int i){
        Point Pij=constructPixelPoint(nX, nY, j, i);
        Ray ray=new Ray(this.location,Pij.subtract(this.location));
        RayBeam raybeam=new RayBeam(ray,this.antiAliasingRaysAmountSq)
                .generateRays(Pij
                        ,this.vpWidth/this.imageWriter.getNx()
                        ,this.vpHeight/this.imageWriter.getNy());
        return raybeam;
    }
    private Color castRay(Ray ray) {
        Color color=this.rayTracer.traceRay(ray);
        pixelManager.pixelDone();
        return color;
    }
    private Color castRays(RayBeam rayBeam){
        Color color=Color.BLACK;
        Ray[][] rays=rayBeam.getRays();
        for(Ray[] i:rays){
            for(Ray j:i){
                color=color.add(this.rayTracer.traceRay(j));
            }
        }
        color=color.reduce(this.antiAliasingRaysAmountSq*this.antiAliasingRaysAmountSq);
        pixelManager.pixelDone();
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

    /**Function writeToVideo produces unoptimized mp4 file of the frames according to
     * previous written frames*/
    public void writeToVideo(){
        this.videoWriter.writeVideo();
    }
    /**clear the frame list ot the VideoWriter*/
    public Camera clearVideo(){
        this.videoWriter.clear();
        return this;
    }
    /**
     * Function writeToImage produces unoptimized png file of the image according to
     * pixel color matrix in the directory of the project*/
    public void writeToImage(){
        this.imageWriter.writeToImage();
    }
    /**write to frame of videoWriter*/
    public Camera writeToFrame(){
        this.imageWriter.writeToFrame(this.videoWriter);
        return this;
    }


}
