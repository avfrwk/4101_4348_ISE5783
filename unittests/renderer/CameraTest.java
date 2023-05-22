package renderer;

import static java.awt.Color.BLUE;
import static java.awt.Color.WHITE;
import static org.junit.jupiter.api.Assertions.*;

import geometries.Geometry;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import org.junit.jupiter.api.Test;

import primitives.*;
import scene.Scene;

/**
 * Testing Camera Class
 * 
 * @author Dan
 *
 */
class CameraTest {
	private final Scene scene1 = new Scene("Test scene");
	private final Scene scene2 = new Scene("Test scene")
			.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

	private final Camera camera1 = new Camera(new Point(0, 0, 1000),
			new Vector(0, 0, -1), new Vector(0, 1, 0))
			.setVPSize(150, 150).setVpDistance(1000);
	private final Camera camera2 = new Camera(new Point(0, 0, 750),
			new Vector(0, 0, -1), new Vector(0, 1, 0))
			.setVPSize(200, 200).setVpDistance(1000);

	private static final int SHININESS = 301;
	private static final double KD = 0.5;
	private static final Double3 KD3 = new Double3(0.2, 0.6, 0.4);

	private static final double KS = 0.5;
	private static final Double3 KS3 = new Double3(0.2, 0.4, 0.3);

	private final Material material = new Material().setKd(KD3).setKs(KS3).setShininess(SHININESS);
	private final Color trianglesLightColor = new Color(800, 500, 250);
	private final Color trianglesLightColor2 = new Color(400, 20, 0);
	private final Color trianglesLightColor3 = new Color(600, 60, 240);

	private final Color sphereLightColor = new Color(800, 500, 0);
	private final Color sphereLightColor2 = new Color(100, 200, 300);
	private final Color sphereLightColor3 = new Color(300, 50, 500);

	private final Color sphereColor = new Color(BLUE).reduce(2);

	private final Point sphereCenter = new Point(0, 0, -50);
	private static final double SPHERE_RADIUS = 50d;

	// The triangles' vertices:
	private final Point[] vertices =
			{
					// the shared left-bottom:
					new Point(-110, -110, -150),
					// the shared right-top:
					new Point(95, 100, -150),
					// the right-bottom
					new Point(110, -110, -150),
					// the left-top
					new Point(-75, 78, 100)
			};
	private final Point sphereLightPosition = new Point(-50, -50, 25);
	private final Point sphereLightPosition2 = new Point(10, -50, 0);
	private final Point trianglesLightPosition = new Point(30, 10, -100);
	private final Point trianglesLightPosition2 = new Point(80, 100, -90);
	private final Vector trianglesLightDirection = new Vector(-2, -2, -2);
	private final Vector trianglesLightDirection2 = new Vector(-2, -2, 0);


	private final Geometry sphere = new Sphere(SPHERE_RADIUS, sphereCenter)
			.setEmission(sphereColor).setMaterial(new Material().setKd(KD).setKs(KS).setShininess(SHININESS));
	private final Geometry triangle1 = new Triangle(vertices[0], vertices[1], vertices[2])
			.setMaterial(material);
	private final Geometry triangle2 = new Triangle(vertices[0], vertices[1], vertices[3])
			.setMaterial(material);
	static final Point ZERO_POINT = new Point(0, 0, 0);
	/**
	 * Test method for
	 * {@link Camera#Camera(Point,Vector,Vector)}.
	 */
	@Test
	void testConstructor() {
		// ============ Equivalence Partitions Tests ==============
		assertDoesNotThrow(()->new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0)),
				"Camera's constructor throws for legal arguments");
		// =============== Boundary Values Tests ==================
		assertThrows(IllegalArgumentException.class,()->new Camera(ZERO_POINT, new Vector(1, 1, 1), new Vector(1, 0, 0)),
				"Camera's constructor does not throws for orthogonal vectors");
	}

	/**
	 * Test method for
	 * {@link Camera#constructRay(int, int, int, int)}.
	 */
	@Test
	void testConstructRay() {
		Camera camera = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0)).setVpDistance(10);
		String badRay = "Bad ray";

		// ============ Equivalence Partitions Tests ==============
		// EP01: 4X4 Inside (1,1)
		assertEquals(new Ray(ZERO_POINT, new Vector(1, -1, -10)),
				camera.setVPSize(8, 8).constructRay(4, 4, 1, 1), badRay);

		// =============== Boundary Values Tests ==================
		// BV01: 3X3 Center (1,1)
		assertEquals(new Ray(ZERO_POINT, new Vector(0, 0, -10)),
				camera.setVPSize(6, 6).constructRay(3, 3, 1, 1), badRay);

		// BV02: 3X3 Center of Upper Side (0,1)
		assertEquals(new Ray(ZERO_POINT, new Vector(0, -2, -10)),
				camera.setVPSize(6, 6).constructRay(3, 3, 1, 0), badRay);

		// BV03: 3X3 Center of Left Side (1,0)
		assertEquals(new Ray(ZERO_POINT, new Vector(2, 0, -10)),
				camera.setVPSize(6, 6).constructRay(3, 3, 0, 1), badRay);

		// BV04: 3X3 Corner (0,0)
		assertEquals(new Ray(ZERO_POINT, new Vector(2, -2, -10)),
				camera.setVPSize(6, 6).constructRay(3, 3, 0, 0), badRay);

		// BV05: 4X4 Corner (0,0)
		assertEquals(new Ray(ZERO_POINT, new Vector(3, -3, -10)),
				camera.setVPSize(8, 8).constructRay(4, 4, 0, 0), badRay);

		// BV06: 4X4 Side (0,1)
		assertEquals(new Ray(ZERO_POINT, new Vector(1, -3, -10)),
				camera.setVPSize(8, 8).constructRay(4, 4, 1, 0), badRay);
	}

	/**test to check the camera spinning*/
	@Test
	void testSpinning(){
		scene1.geometries.add(sphere);
		scene1.lights.add(new DirectionalLight(sphereLightColor, new Vector(1, 1, -0.5)));
		Camera camera = new Camera(new Point(0, 0, 1000),((Sphere)sphere).getCenter())
				.setVpDistance(1000).setVPSize(150, 150).setRayTracer(new RayTracerBasic(scene1))
				.setVideoWriter(new VideoWriter("spinningSphere"));
		for(int i=0;i<36;++i){
			camera.rotateCameraAroundPointVup(((Sphere) sphere).getCenter(),10)
			.setImageWriter(new ImageWriter("SphereSpin", 500, 500)) //
					.renderImage() //
					.writeToFrame();
		}
		camera.writeToVideo();
	}
	/**test to check the camera movements*/
	@Test
	void testMoving(){
		scene1.geometries.add(sphere);
		scene1.lights.add(new DirectionalLight(sphereLightColor, new Vector(1, 1, -0.5)));
		Camera camera = new Camera(new Point(0, 0, 1000),((Sphere)sphere).getCenter())
				.setVpDistance(1000).setVPSize(150, 150).setRayTracer(new RayTracerBasic(scene1))
				.setVideoWriter(new VideoWriter("moving sphere").setFps(25));
		for(int i=0;i<10;++i){
			camera.moveRight(5)
					.setImageWriter(new ImageWriter("SphereMoving", 500, 500)) //
					.renderImage() //
					.writeToFrame();
		}
		for(int i=0;i<20;++i){
			camera.moveRight(-5)
					.setImageWriter(new ImageWriter("SphereMoving", 500, 500)) //
					.renderImage() //
					.writeToFrame();
		}
		for(int i=0;i<10;++i){
			camera.moveRight(5)
					.setImageWriter(new ImageWriter("SphereMoving", 500, 500)) //
					.renderImage() //
					.writeToFrame();
		}

		for(int i=0;i<10;++i){
			camera.moveUp(5)
					.setImageWriter(new ImageWriter("SphereMoving", 500, 500)) //
					.renderImage() //
					.writeToFrame();
		}
		for(int i=0;i<20;++i){
			camera.moveUp(-5)
					.setImageWriter(new ImageWriter("SphereMoving", 500, 500)) //
					.renderImage() //
					.writeToFrame();
		}
		for(int i=0;i<10;++i){
			camera.moveUp(5)
					.setImageWriter(new ImageWriter("SphereMoving", 500, 500)) //
					.renderImage() //
					.writeToFrame();
		}

		for(int i=0;i<50;++i){
			camera.moveForward(5)
					.setImageWriter(new ImageWriter("SphereMoving", 500, 500)) //
					.renderImage() //
					.writeToFrame();
		}
		for(int i=0;i<50;++i){
			camera.moveForward(-5)
					.setImageWriter(new ImageWriter("SphereMoving", 500, 500)) //
					.renderImage() //
					.writeToFrame();
		}
		camera.writeToVideo();
	}
	/**test to check the camera Rotating*/
	@Test
	void testRotating(){
		scene1.geometries.add(sphere);
		scene1.lights.add(new DirectionalLight(sphereLightColor, new Vector(1, 1, -0.5)));
		Camera camera = new Camera(new Point(0, 0, 1000),((Sphere)sphere).getCenter())
				.setVpDistance(1000).setVPSize(150, 150).setRayTracer(new RayTracerBasic(scene1))
				.setVideoWriter(new VideoWriter("Rotating sphere").setFps(25));
		for(int i=0;i<36;++i){
			camera.rotateCamera(10)
					.setImageWriter(new ImageWriter("SphereRotates", 500, 500)) //
					.renderImage() //
					.writeToFrame();
		}
		camera.writeToVideo();
	}
	/**test to check the camera Rotating around */
	@Test
	void testRotatingAround(){
		scene2.geometries.add(triangle1, triangle2);
		scene2.lights.add(new PointLight(trianglesLightColor, trianglesLightPosition)
				.setKl(0.001).setKq(0.0002));

		ImageWriter imageWriter = new ImageWriter("lightTrianglesPoint", 500, 500);
		camera2.setRayTracer(new RayTracerBasic(scene2)) //
		.setVideoWriter(new VideoWriter("rotating triangle around").setFps(25));

		for(int i=0;i<10;++i){
			camera2.rotateCameraAroundVup(0.5)
					.setImageWriter(new ImageWriter("TriangleRotatesAround", 500, 500)) //
					.renderImage() //
					.writeToFrame();
		}

		for(int i=0;i<20;++i){
			camera2.rotateCameraAroundVup(-0.5)
					.setImageWriter(new ImageWriter("TriangleRotatesAround", 500, 500)) //
					.renderImage() //
					.writeToFrame();
		}
		for(int i=0;i<10;++i){
			camera2.rotateCameraAroundVup(0.5)
					.setImageWriter(new ImageWriter("TriangleRotatesAround", 500, 500)) //
					.renderImage() //
					.writeToFrame();
		}
		for(int i=0;i<10;++i){
			camera2.rotateCameraArundVright(0.5)
					.setImageWriter(new ImageWriter("TriangleRotatesAround", 500, 500)) //
					.renderImage() //
					.writeToFrame();
		}
		for(int i=0;i<20;++i){
			camera2.rotateCameraArundVright(-0.5)
					.setImageWriter(new ImageWriter("TriangleRotatesAround", 500, 500)) //
					.renderImage() //
					.writeToFrame();
		}
		for(int i=0;i<10;++i){
			camera2.rotateCameraArundVright(0.5)
					.setImageWriter(new ImageWriter("TriangleRotatesAround", 500, 500)) //
					.renderImage() //
					.writeToFrame();
		}
		camera2.writeToVideo();
	}
}





