package in.aaronmiller.raytrace;
public class Ray extends Vector{
	Point p0;
	
	public Ray(Point one, Point two){
		super(one.x - two.x,one.y-two.y,one.z-two.z);
		super.normalize();
		p0 = one;
	}
	public Ray(Vector vec, Point pt){
		super((vec.getValue())[0],(vec.getValue())[1],(vec.getValue())[2]);
		super.normalize();
		p0 = pt;
		
	}
	public Ray(){
		super(1,1,1);
		super.normalize();
		p0 = new Point (0,0,0,1);
	}
	
	public Point equation(double s){
		double[] sup = super.getValue();
		Point p = new Point((p0.x + s*sup[0]) , (p0.y + s*sup[1]), (p0.z + s*sup[2]), p0.h);
		return p;
	}
}