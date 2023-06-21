package geometries;

import static primitives.Util.isZero;

import java.util.LinkedList;
import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/** Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 * @author Dan */
public class Polygon extends Geometry {
   /** List of polygon's vertices */
   protected final List<Point> vertices;
   /** Associated plane in which the polygon lays */
   protected final Plane       plane;
   private final int           size;

   /** Polygon constructor based on vertices list. The list must be ordered by edge
    * path. The polygon must be convex.
    * @param  vertices                 list of vertices according to their order by
    *                                  edge path
    * @throws IllegalArgumentException in any case of illegal combination of
    *                                  vertices:
    *                                  <ul>
    *                                  <li>Less than 3 vertices</li>
    *                                  <li>Consequent vertices are in the same
    *                                  point
    *                                  <li>The vertices are not in the same
    *                                  plane</li>
    *                                  <li>The order of vertices is not according
    *                                  to edge path</li>
    *                                  <li>Three consequent vertices lay in the
    *                                  same line (180&#176; angle between two
    *                                  consequent edges)
    *                                  <li>The polygon is concave (not convex)</li>
    *                                  </ul>
    */
   public Polygon(Point... vertices) {
      if (vertices.length < 3)
         throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
      this.vertices = List.of(vertices);
      size          = vertices.length;

      // Generate the plane according to the first three vertices and associate the
      // polygon with this plane.
      // The plane holds the invariant normal (orthogonal unit) vector to the polygon
      plane         = new Plane(vertices[0], vertices[1], vertices[2]);
      if (size == 3) return; // no need for more tests for a Triangle

      Vector  n        = plane.getNormal();
      // Subtracting any subsequent points will throw an IllegalArgumentException
      // because of Zero Vector if they are in the same point
      Vector  edge1    = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
      Vector  edge2    = vertices[0].subtract(vertices[vertices.length - 1]);

      // Cross Product of any subsequent edges will throw an IllegalArgumentException
      // because of Zero Vector if they connect three vertices that lay in the same
      // line.
      // Generate the direction of the polygon according to the angle between last and
      // first edge being less than 180 deg. It is hold by the sign of its dot product
      // with
      // the normal. If all the rest consequent edges will generate the same sign -
      // the
      // polygon is convex ("kamur" in Hebrew).
      boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
      for (var i = 1; i < vertices.length; ++i) {
         // Test that the point is in the same plane as calculated originally
         if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
            throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
         // Test the consequent edges have
         edge1 = edge2;
         edge2 = vertices[i].subtract(vertices[i - 1]);
         if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
            throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
      }
   }
   @Override
   public Vector getNormal(Point point) { return plane.getNormal(); }

   /** get list of intersection between ray and Polygon
    * @param ray the ray
    * @param maxDistance the maximum allowed distance to return the geopoint
    * @return list of intersections
    * */
   @Override
   protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance){
      List<GeoPoint> planeInsect=this.plane.findGeoIntersections(ray,maxDistance);
      if(planeInsect!=null){
         Point p0=planeInsect.get(0).point;
         Vector v0=this.vertices.get(0).subtract(p0);
         Vector v1=this.vertices.get(1).subtract(p0);
         Vector n=this.plane.getNormal();

         boolean tt;
         try {//be carefull here
            tt = (v0.crossProduct(v1).dotProduct(n) > 0);
         }catch(Exception e){
            return null;
         }
         for(int i=1;i<size-1;++i){
            Point pp0=this.vertices.get(i),pp1=this.vertices.get(i+1);
            if(pp0.equals(p0)||pp1.equals(p0))
               return  null;
            v0=pp0.subtract(p0);
            v1=pp1.subtract(p0);
            try{
               if((v0.crossProduct(v1).dotProduct(n)>0)!=tt){
                  return null;
               }
            }catch(Exception e){
               return null;
            }


         }
         v0=this.vertices.get(size-1).subtract(p0);
         v1=this.vertices.get(0).subtract(p0);
         if((v0.crossProduct(v1).dotProduct(n)>0)==tt){
            return List.of(new GeoPoint(this,p0));
         }
      }
      return null;
   }

   @Override
   public List<Double> minMaxPoints(){
      List<Double> a=new LinkedList<>();
      Double []b=new Double[6];
      for(Point i:vertices){
         if(b[0]==null)
            b[0]=i.getX();
         if(i.getX()>b[0])
            b[0]=i.getX();
      }
      for(Point i:vertices){
         if(b[1]==null)
            b[1]=i.getY();
         if(i.getY()>b[1])
            b[1]=i.getY();
      }
      for(Point i:vertices){
         if(b[2]==null)
            b[2]=i.getZ();
         if(i.getZ()>b[2])
            b[2]=i.getZ();
      }
      for(Point i:vertices){
         if(b[3]==null)
            b[3]=i.getX();
         if(i.getX()<b[3])
            b[3]=i.getX();
      }
      for(Point i:vertices){
         if(b[4]==null)
            b[4]=i.getY();
         if(i.getY()<b[4])
            b[4]=i.getY();
      }
      for(Point i:vertices){
         if(b[5]==null)
            b[5]=i.getZ();
         if(i.getZ()<b[5])
            b[5]=i.getZ();
      }
      for(int i=0;i<6;i++){
         a.add(b[i]);
      }
      return a;
   }

}
