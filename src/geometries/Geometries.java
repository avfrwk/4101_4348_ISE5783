package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Collection;
import java.util.List;
import java.util.LinkedList;

public class Geometries implements Intersectable{
    List<Intersectable> Intersectables;
    /** Constructor to initialize empty Geometries object
     */
    public Geometries(){
        this.Intersectables=new LinkedList<>();
    }
    /** Constructor to initialize Geometries object that contains the Intersectables that provided
     * @param geometries array of Intersectable
     */
    public Geometries(Intersectable... geometries){
        this.Intersectables=new LinkedList<>();
        for (Intersectable i:geometries) {
            this.Intersectables.add(i);
        }
    }
    /** add Intersectables to the Geometries object
     * @param geometries array of Intersectable to add
     */
    public void add(Intersectable... geometries){
        for (Intersectable i:geometries) {
            this.Intersectables.add(i);
        }
    }
    /** get list of intersection between ray and the Intersectables inside the Geometries object
     * @param ray the ray
     * @return list of intersections
     * */
    @Override
    public List<Point> findIntsersections(Ray ray) {
        List<Point> insects=null;
        List<Point> localInsects;
        for (Intersectable i:this.Intersectables) {
            localInsects=i.findIntsersections(ray);
            if(localInsects!=null){
                if(insects!=null)
                    insects.addAll(localInsects);//-------------------------------------
                else insects=new LinkedList<>(localInsects);
            }
        }
        return insects;
    }
}
