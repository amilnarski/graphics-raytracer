package in.aaronmiller.raytrace;

public class Sphere extends Form {
	protected Point center;
	protected double radius;

	public Sphere(double diffuse, double x, double y, double z, double r,
			RGBPixel color, double wTheta, double ns) {
		this.kd = diffuse;
		this.center = new Point(x, y, z, 1);
		this.radius = r;
		this.color = color;
		super.wTheta = wTheta;
		super.ns = ns;
	}

	public String toString() {
		String s = "Sphere: ";
		s += center.toString();
		s += '\t';
		s += "Radius: " + radius;
		s += '\t';
		s += color;
		s += '\t';
		s+=super.toString();
		return s;
	}

	@Override
	public Vector normal(Point p) {
		Vector normal = new Vector(p.x - center.x, p.y - center.y, p.z
				- center.z);
		normal.normalize();
		return normal;
	}

	@Override
	public Point intersect(Ray r) {
		Point intersect = null;
		// s=(-C +/-srqt(C2 Ð4C))/2
		Point p0 = r.p0;
		Point pcp0 = new Point((center.x - p0.x), (center.y - p0.y),
				(center.z - p0.z), 1);
		double c0 = Math.pow(pcp0.x, 2) + Math.pow(pcp0.y, 2)
				+ Math.pow(pcp0.z, 2) - Math.pow(this.radius, 2);
		Point c1Help = r.equation(pcp0.x + pcp0.y + pcp0.z);
		double c1 = (-2 * c1Help.x) + (-2 * c1Help.y) + (-2 * c1Help.z);

		double s1 = (-c1 + Math.sqrt(Math.pow(c1, 2) - 4 * c0)) / 2;
		double s2 = (-c1 - Math.sqrt(Math.pow(c1, 2) - 4 * c0)) / 2;

		double s = -1;

		if (s1 < 0) {
			s = s2;
		} else if (s2 < 0) {
			s = s1;
		} else if (s1 < s2) {
			s = s1;
		} else {
			s = s2;
		}

		if (s >= 0) {
			Point rd = r.equation(s);
			intersect = new Point(p0.x + rd.x, p0.y + rd.y, p0.z + rd.z, 1);
		}
		return intersect;
	}
}