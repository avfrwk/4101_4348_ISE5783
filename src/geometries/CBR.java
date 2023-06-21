package geometries;

import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class CBR extends Geometries{
    private List<Double>points;

    /**
     * Constructor to initialize Geometries object that contains the Intersectables that provided
     *
     * @param geometries array of Intersectable
     */
    public CBR(Intersectable... geometries){
        super(geometries);
        points=minMaxPoints();
    }

    @Override
    public List<Double> minMaxPoints(){
        Double[] points1=new Double[6];
        int k=0;
        for(Double j : points){
            points1[k++]=j;
        }
        for (Intersectable i : geometries) {
            if(i.minMaxPoints()==null){
                return null;
            }
            Double[] geo=new Double[6];
            k=0;
            for(Double j : i.minMaxPoints()){
                geo[k++]=j;
            }
            for(int j=0;j<3;j++){
                if(points1[j]==null||points1[j]<geo[j])
                    points1[j]=geo[j];
            }
            for(int j=3;j<6;j++){
                if(points1[j]==null||points1[j]>geo[j])
                    points1[j]=geo[j];
            }
        }
        List<Double> a=new LinkedList<>();
        for(int i=0;i<6;i++){
            a.add(points1[i]);
        }
        return a;
    }

    @Override
    public void add(Intersectable... geometries) {
        for (Intersectable i : geometries) {
            this.geometries.add(i);
        }
        //change the bounds
        Double[] points1=new Double[6];
        int k=0;
        for(Double j : points){
            points1[k++]=j;
        }
        for (Intersectable i : geometries) {
            if(i.minMaxPoints()==null){
                points= null;
                break;
            }
            Double[] geo=new Double[6];
            k=0;
            for(Double j : i.minMaxPoints()){
                geo[k++]=j;
            }
            for(int j=0;j<3;j++){
                if(points1[j]==null||points1[j]<geo[j])
                    points1[j]=geo[j];
            }
            for(int j=3;j<6;j++){
                if(points1[j]==null||points1[j]>geo[j])
                    points1[j]=geo[j];
            }
        }
        List<Double> a=new LinkedList<>();
        for(int i=0;i<6;i++){
            a.add(points1[i]);
        }
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance){
        //if()//to check if the ray in the bound
        List<GeoPoint> insects = null;
        List<GeoPoint> localInsects;
        for (Intersectable geometry : this.geometries) {
            localInsects = geometry.findGeoIntersectionsHelper(ray,maxDistance);
            if (localInsects != null) {
                if (insects != null)
                    insects.addAll(localInsects);//-------------------------------------
                else insects = new LinkedList<>(localInsects);
            }
        }
        return insects;
    }

}
