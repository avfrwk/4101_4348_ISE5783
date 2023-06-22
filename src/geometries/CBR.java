package geometries;

import primitives.Point;
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
        if(points!=null){
            for(Double j : points){
                points1[k++]=j;
            }
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

    public List<Double> getPoints() {
        return points;
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
        //check first if the ray have intersection with the bounds
        Polygon[] polygons=new Polygon[6];
        int i=0;
        Double []a=new Double[6];
        for(Double j:points){
            a[i++]=j;
        }
        polygons[0]=new Polygon(new Point(a[0],a[1],a[2]),new Point(a[0],a[4],a[2]),new Point(a[0],a[4],a[5]),new Point(a[0],a[1],a[5]));
        polygons[1]=new Polygon(new Point(a[0],a[1],a[2]),new Point(a[0],a[4],a[2]),new Point(a[3],a[4],a[2]),new Point(a[3],a[1],a[2]));
        polygons[2]=new Polygon(new Point(a[0],a[1],a[2]),new Point(a[3],a[1],a[2]),new Point(a[3],a[1],a[5]),new Point(a[0],a[1],a[5]));
        polygons[3]=new Polygon(new Point(a[3],a[4],a[5]),new Point(a[3],a[1],a[5]),new Point(a[3],a[1],a[2]),new Point(a[3],a[4],a[2]));
        polygons[4]=new Polygon(new Point(a[3],a[4],a[5]),new Point(a[0],a[4],a[5]),new Point(a[0],a[4],a[2]),new Point(a[3],a[4],a[2]));
        polygons[5]=new Polygon(new Point(a[3],a[4],a[5]),new Point(a[0],a[4],a[5]),new Point(a[0],a[1],a[5]),new Point(a[3],a[1],a[5]));
        for(int j=0;j<6;j++){
            if(polygons[j].findGeoIntersectionsHelper(ray,maxDistance)!=null)
                break;
            if(j==5)
                return null;
        }

        //super?
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

    /**return the distance between two bounds by pow
     * @return Double
     */
    Double distanceBetweenBounds(CBR cbr){
        Double[] a=new Double[6];
        int j=0;
        for(Double i:points){
            a[j++]=i;
        }
        j=0;
        for(Double i:points){
            a[j]=Math.abs(a[j]-i);
            j++;
        }
        double result=0;
        for(int k=0;k<3;k++){
            if(a[k]>a[k+3])
                a[k]=a[k+3];
            result+=a[k]*a[k];
        }
        return result;
    }

    /**return the size of the boundary by pow
     * @return Double
     */
    Double size(){
        Double[] a=new Double[6];
        int j=0;
        for(Double i:points){
            a[j++]=i;
        }
        double result=0;
        for(int k=0;k<3;k++){
            result+=(a[k]-a[k+3])*(a[k]-a[k+3]);
        }
        return result;
    }

    /**parts the bounds automatically
     */
    public void partToBounds(){
        int num=geometries.size();
        if(num==1)//check if there is only one
            return;
        Double maxDistance=size()/num/num;
        CBR[] cbr=new CBR[num];
        boolean[] isUsing=new boolean[num];
        int j=0;
        for(Intersectable i:geometries){
            isUsing[j]=true;
            cbr[j++]=new CBR(i);
        }
        for(int i=0;i<num-1;i++){
            if(cbr[i].minMaxPoints()!=null&&isUsing[i]==true) {
                for(int k=i+1;k<num;k++){
                    if(cbr[k].minMaxPoints()!=null&&isUsing[k]==true&&cbr[i].distanceBetweenBounds(cbr[k])<maxDistance){
                        for(Intersectable temp: cbr[k].geometries){
                            cbr[i].add(temp);
                        }
                        isUsing[k]=false;
                    }
                }

            }
        }
        j=0;
        for(int i=0;i<num;i++){
            if(isUsing[i])
                ++j;
        }
        if(j<2)//Prevents an infinite loop
            return;
        List<Intersectable> result=new LinkedList<>();
        for(int i=0;i<num;i++){
            if(isUsing[i]) {
                cbr[i].partToBounds();
                result.add(cbr[i]);
            }
        }
        geometries=result;
    }
}
