package in.aaronmiller.raytrace;
public class Point{
	protected double x;
	protected double y;
	protected double z;
	protected double h;
	
	public Point(double x, double y, double z, double h){
		this.x = x;
		this.y = y;
		this.z = z;
		this.h = h;
	}
	
	public String toString (){
		return "("+x+", "+y+", "+z+")";
	}
}