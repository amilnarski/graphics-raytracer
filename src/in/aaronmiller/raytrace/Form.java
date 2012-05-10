package in.aaronmiller.raytrace;
public abstract class Form{
	protected double kd;
	protected double wTheta;
	protected double ns;
	protected RGBPixel color;
	
	public abstract Vector normal(Point p);
	public abstract Point intersect(Ray r);
	
	public String toString(){
		return "kd: "+ kd+'\t'+"wTheta: "+wTheta+'\t'+"ns: "+ns;
	}
}