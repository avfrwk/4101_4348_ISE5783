package primitives;

import static java.lang.Double.parseDouble;

/**
 * Util class is used for some internal utilities, e.g. controlling accuracy
 * 
 * @author Dan
 */
public abstract class Util {
	// It is binary, equivalent to ~1/1,000,000,000,000 in decimal (12 digits)
	private static final int ACCURACY = -40;

	/**
	 * Empty private constructor to hide the public one
	 */
	private Util() {}

	// double store format (bit level):
	//    seee eeee eeee (1.)mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	// 1 bit sign, 11 bits exponent, 53 bits (52 stored) normalized mantissa
	// the number is m+2^e where 1<=m<2
	// NB: exponent is stored "normalized" (i.e. always positive by adding 1023)
	private static int getExp(double num) {
		// 1. doubleToRawLongBits: "convert" the stored number to set of bits
		// 2. Shift all 52 bits to the right (removing mantissa)
		// 3. Zero the sign of number bit by mask 0x7FF
		// 4. "De-normalize" the exponent by subtracting 1023
		return (int) ((Double.doubleToRawLongBits(num) >> 52) & 0x7FFL) - 1023;
	}

	/**
	 * Checks whether the number is [almost] zero
	 * 
	 * @param number the number to check
	 * @return true if the number is zero or almost zero, false otherwise
	 */
	public static boolean isZero(double number) {
		return getExp(number) < ACCURACY;
	}

	/**
	 * Aligns the number to zero if it is almost zero
	 * 
	 * @param number the number to align
	 * @return 0.0 if the number is very close to zero, the number itself otherwise
	 */
	public static double alignZero(double number) {
		return getExp(number) < ACCURACY ? 0.0 : number;
	}
	/**
	 * Aligns the numberes of Double 3 to zero if it is almost zero
	 *
	 * @param double3 the number to align
	 * @return 0.0 if the number is very close to zero, the number itself otherwise
	 */
	public static Double3 alignZero(Double3 double3) {
		return new Double3(alignZero(double3.d1),alignZero(double3.d2),alignZero(double3.d3));
	}
	/**
	 * Check whether two numbers have the same sign
	 * 
	 * @param n1 1st number
	 * @param n2 2nd number
	 * @return true if the numbers have the same sign
	 */
	public static boolean checkSign(double n1, double n2) {
		return (n1 < 0 && n2 < 0) || (n1 > 0 && n2 > 0);
	}

	/**
	 * Provide a real random number in range between min and max
	 * 
	 * @param min value (included)
	 * @param max value (excluded)
	 * @return the random value
	 */
	public static double random(double min, double max) {
		return Math.random() * (max - min) + min;
	}
	/** initializing Double3 based on String of 3 double, separated by spaces
	 * @param str String of 3 double, separated by spaces
	 * @return the Double3 value represented by the string argument*/
	public static Double3 parseDouble3(String str){
		String[] ls=str.split(" ");
		return new Double3(
				parseDouble(ls[0]),
				parseDouble(ls[1]),
				parseDouble(ls[2]));
	}
	/**rotates vector around vector
	 * @param v the rotator vector
	 * @param vectorToRotate the rotated vector
	 * @param angle the angle
	 *@return the vector, rotated*/
	public static Vector rotateAroundVector(Vector v, Vector vectorToRotate, double angle){
		double radAngle=angle*Math.PI/180;
		double cos=Math.cos(radAngle);
		double sin=Math.sin(radAngle);
		double vvr=v.dotProduct(vectorToRotate);
		Vector vectorToRotateRet;

		if(Util.alignZero(cos)!=0){
			vectorToRotateRet=vectorToRotate.scale(cos);
			if(Util.alignZero(sin)!=0){
				try{
					vectorToRotateRet=vectorToRotateRet.add(v.crossProduct(vectorToRotate).scale(sin));
				}catch(Exception e){}
			}
			if(Util.alignZero(vvr*(1-cos))!=0){
				vectorToRotateRet=vectorToRotateRet.add(v.scale(vvr*(1-cos)));
			}
		}
		else{
			vectorToRotateRet=v.crossProduct(vectorToRotate).scale(sin);
			if(Util.alignZero(cos-1)!=0&&alignZero(vvr)!=0){
				vectorToRotateRet=vectorToRotateRet.add(v.scale(vvr*(1-cos)));
			}
		}
		return new Vector(alignZero(vectorToRotateRet.xyz));
	}
	/**rotates point around point in plane that orthogonal to vector
	 * @param v the rotator vector
	 * @param p0 the point to rotate around
	 * @param point the rotated point
	 * @param angle the angle
	 *@return the new rotated point*/
	public static Point rotatePointAroundVector(Vector v, Point p0, Point point, double angle){
		return new Point(alignZero(p0.add(rotateAroundVector(v,point.subtract(p0),angle)).xyz));
	}
	public static Point rotateAroundX(Point point,Point p0,double angle){
		double x=point.getX()-p0.getX();
		double y=point.getY()-p0.getY();
		double z=point.getZ()-p0.getZ();
		double radAngle=angle*Math.PI/180;
		double cos=Math.cos(radAngle);
		double sin=Math.sin(radAngle);
		return new Point(
				p0.getX()+x,
				p0.getY()+y*cos-z*sin,
				p0.getZ()+y*sin+z*cos
		);
	}
	public static Point rotateAroundY(Point point,Point p0,double angle){
		double x=point.getX()-p0.getX();
		double y=point.getY()-p0.getY();
		double z=point.getZ()-p0.getZ();
		double radAngle=angle*Math.PI/180;
		double cos=Math.cos(radAngle);
		double sin=Math.sin(radAngle);
		return new Point(
				p0.getX()+x*cos+z*sin,
				p0.getY()+y,
				p0.getZ()+-x*sin+z*cos
		);
	}
	public static Point rotateAroundZ(Point point,Point p0,double angle){
		double x=point.getX()-p0.getX();
		double y=point.getY()-p0.getY();
		double z=point.getZ()-p0.getZ();
		double radAngle=angle*Math.PI/180;
		double cos=Math.cos(radAngle);
		double sin=Math.sin(radAngle);
		return new Point(
				p0.getX()+x*cos-y*sin,
				p0.getY()+x*sin+y*cos,
				p0.getZ()+z
		);
	}
}
