package primitives;

public class Material {
    public Double3 kD=Double3.ZERO,
    kS=Double3.ZERO,
    /**coefficient of transparency*/
    kT=Double3.ZERO,
    /**coefficient of reflection*/
    kR=Double3.ZERO;
    public int nShininess=0;
    /** set the value of the kT parameter
     * @param kT the value to set
     * @return this Material
     * */
    public Material setKt(Double3 kT) {
        this.kT = kT;
        return this;
    }
    /** set the value of the kT parameter
     * @param kT the value to set
     * @return this Material
     * */
    public Material setKt(double kT) {
        this.kT = new Double3(kT);
        return this;
    }
    /** set the value of the kR parameter
     * @param kR the value to set
     * @return this Material
     * */
    public Material setKr(Double3 kR) {
        this.kR = kR;
        return this;
    }
    /** set the value of the kR parameter
     * @param kR the value to set
     * @return this Material
     * */
    public Material setKr(double kR) {
        this.kR = new Double3(kR);
        return this;
    }
    /** set the value of the kD parameter
     * @param kD the value to set
     * @return this Material
     * */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }
    /** set the value of the kD parameter
     * @param kD the value to set
     * @return this Material
     * */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }
    /** set the value of the kS parameter
     * @param kS the value to set
     * @return this Material
     * */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }
    /** set the value of the kS parameter
     * @param kS the value to set
     * @return this Material
     * */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }
    /** set the value of the nShininess parameter
     * @param nShininess the value to set
     * @return this Material
     * */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
