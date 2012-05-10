package in.aaronmiller.raytrace;

import java.util.ArrayList;

public class Polygon extends Form {
	protected Vector normal;
	protected ArrayList<Point> vertices;

	public Polygon(double diffuse, RGBPixel color, double wTheta, double ns) {
		this.kd = diffuse;
		this.color = color;
		super.wTheta = wTheta;
		super.ns = ns;
	}

	public void addVertex(Point v) {
		if (vertices == null)
			vertices = new ArrayList<Point>();
		vertices.add(v);
		// calculate the normal when we have 3 vertices
		if (vertices.size() == 3) {
			Point one = vertices.get(0);
			Point two = vertices.get(1);
			Point three = vertices.get(2);
			Vector v1 = new Vector(one.x - two.x, one.y - two.y, one.z - two.z);
			Vector v2 = new Vector(three.x - two.x, three.y - two.y, three.z
					- two.z);
			normal = v1.crossProduct(v2);
		}
	}

	public String toString() {
		String s = "Poly: ";
		for (int i = 0; i < vertices.size(); i++) {
			s += vertices.get(i).toString();
			s += '\t';
		}
		s += color.toString();
		s += '\t';
		s+=super.toString();
		return s;
	}

	@Override
	public Vector normal(Point p) {
		return normal;
	}

	@Override
	public Point intersect(Ray r) {
		Point intersect = null;
		double[] normal = this.normal.getValue();
		Point planep = vertices.get(0);
		double D = -1
				* ((normal[0] * planep.x) + (normal[1] * planep.y) + (normal[2] * planep.z));
		double ndotp0 = normal[0] * r.p0.x + normal[1] * r.p0.y + normal[2]
				* r.p0.z;
		double ndotr = this.normal.dotProduct(r);

		double s = (-1 * (ndotp0 + D)) / (ndotr);

		intersect = new Point(r.p0.x + r.equation(s).x, r.p0.y
				+ r.equation(s).y, r.p0.z + r.equation(s).z, 1);

		Vector N;
		if (ndotr > 0) {
			N = new Vector(-1 * normal[0], -1 * normal[1], -1 * normal[2]);
		} else {
			N = this.normal;
		}

		// get coefficient to ignore?

		// just ignore the C coefficent based on a very scientific analysis of
		// my scene
		normal = N.getValue();
		Matrix Torigin = new Matrix(new double[][] {
				{ 1, 0, 0, (-1 * normal[0]) }, { 0, 1, 0, (-1 * normal[1]) },
				{ 0, 0, 1, 0 }, { 0, 0, 0, 1 } });

		ArrayList<Point> Tpoints = new ArrayList<Point>();

		//Point Tintersect = Torigin.transform(intersect);
		for (int i = 0; i < vertices.size(); i++){
			Tpoints.add(Torigin.transform(vertices.get(i)));
		}
		
		int numCross = 0;
		int signHold1;
		int signHold2;
		
		if (Tpoints.get(0).y > 0){
			signHold1 = 1;
		} else {
			signHold1 = -1;
		}
		for (int i = 0; i<Tpoints.size(); i++){
			//(u_a, v_a)
			Point a  = vertices.get(i);
			Point b = vertices.get((i+1)%Tpoints.size());
			
			if (b.y > 0){
				signHold2 = 1;
			} else {
				signHold2 = -1;
			}
			
			if (signHold1 != signHold2){
				if (a.x > 0 && b.x > 0){
					numCross ++;
				} else if (a.x > 0 || b.x > 0){
					if(crossPosUAxis(a.x, a.y, b.x, b.y)){
						numCross ++;
					}
				}
			}
			signHold1 = signHold2;
		}

		if (numCross % 2 != 0){
			intersect = null;
		}
		return intersect;
	}
	
	private boolean crossPosUAxis(double ua, double va, double ub, double vb) {
		if ((ub +(ua-ub)*(vb /(vb-va))) > 0 ){
			return true;
		} else {
			return false;
		}
	}
}
