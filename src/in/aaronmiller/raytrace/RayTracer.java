package in.aaronmiller.raytrace;

import java.util.*;
import java.util.regex.*;
import java.io.*;

public class RayTracer {
	RGBPixel ambient;
	ArrayList<Light> lights;
	int maxDepth;
	ArrayList<Form> objects;
	Pattern light;
	Pattern polygon;
	Pattern sphere;
	static Point cop;

	public RayTracer() {
		this.ambient = new RGBPixel(0, 0, 0);
		this.lights = new ArrayList<Light>();
		this.maxDepth = 5;
		this.objects = new ArrayList<Form>();
		this.light = Pattern.compile("L\\wV\\((\\d+),(\\d+),(\\d)+\\)\\wI\\d+\\.\\d+");
		this.polygon = Pattern
				.compile("P\\wV.(\\d+),(\\d+),(\\d)+.\\wV\\.(\\d+),(\\d+),(\\d)+.\\wV.(\\d+),(\\d+),(\\d)+.\\wColor.(\\d+),(\\d+),(\\d)+\\wD\\d+\\.\\d+");
		this.sphere = Pattern.compile("");
		this.cop = new Point(512,384,768,1);
	}

	public static void main(String[] args) {
		String filename;
		int argsLength = args.length;
		if (argsLength == 1) {
			filename = args[0].trim();
		} else {
			// simply exit if no filename is given
			System.out
					.println("Please call this with the format 'java RayTracer filename'.");
			System.exit(-1);
		}

		// set up the image
		RGBPixel[][] img = new RGBPixel[1024][768];
		RayTracer rt = new RayTracer();
		rt.readSceneFile();
		for (int scanline = 0; scanline < img.length; scanline++) {
			for (int pixel = 0; pixel < img[0].length; pixel++) {
				// determine ray from COP --> pixel
				Ray ray = new Ray(cop, new Point(pixel, scanline, 0, 1) );
				img[scanline][pixel] = rt.trace(ray, 1);
			}
		}

		// write the produced image as a PPM
		rt.writePPM(img);

	}

	private RGBPixel trace(Ray ray, int depth) {
		// determine closest intersection of ray with an object
		Double closest = Double.POSITIVE_INFINITY;
		int closestIndex = 0;
		Point[] intersections = new Point[objects.size()];
		for (int i = 0; i < this.objects.size(); i++){
			intersections[i] = objects.get(i).intersect(ray);
			Vector d = new Vector (intersections[i].x - cop.x, intersections[i].y - cop.y, intersections[i].z - cop.z);
			if (d.magnitude() < closest) {
				closest = d.magnitude();
				closestIndex = i;
			}
		}
		
		Point nearInt = intersections[closestIndex];
		
		if (nearInt != null) {
			// compute normal at intersection
			Form closestHit = objects.get(closestIndex);
			Point intersectionPt = nearInt;
			Vector normal = objects.get(closestIndex).normal(nearInt);

			return shade(closestHit, ray, intersectionPt, normal, depth);
		} else {
			return new RGBPixel(0,0,0);
		}
	}

	private RGBPixel shade (Form object, Ray ray, Point point, Vector normal, int depth){
		RGBPixel color;
		Ray rRay;
		Ray sRay;
		Ray tRay;
		RGBPixel rColor;
		RGBPixel tColor;
		
		color = ambient;
		for (int light = 0; light < lights.size(); light++){
			Light l = lights.get(light);
			sRay = new Ray (point, l);//pass in the light
			if( normal.dotProduct(sRay) > 0/*dot product of the normal & direction to light is positive*/) {
				//add the diffuse and specular terms to the color
				
			}
		}// end for
		if (depth < maxDepth){
			if (false/*object is reflective*/){
				rRay = new Ray()//ray in reflection direction from point
				rColor = trace(rRay, ++depth);
				//scale rColor by specular coefficient and add to color
			}
			
			if (false/*object is transparent*/){
				//do stuff, but this won't be the case so it will be ignored
			}
			
		} else {
			return color;			
		}
	}

	private void readSceneFile() {
		try {
			File sceneF = new File("scene.gdf");
			Scanner scan = new Scanner(sceneF);
			while (scan.hasNextLine()) {
				String line = scan.nextLine().trim();
				System.out.println(line);
				String[] tokens = line.split(" ");
				if (tokens[0].equals("L")){
					Light l = new Light(Double.parseDouble(tokens[4]),Double.parseDouble(tokens[1]),Double.parseDouble(tokens[2]),Double.parseDouble(tokens[3]));
					System.out.println(l);
					lights.add(l);
				} else if (tokens[0].equals("S")){
					RGBPixel sColor = new RGBPixel(Integer.parseInt(tokens[5]),Integer.parseInt(tokens[6]),Integer.parseInt(tokens[7]));
					Sphere s = new Sphere(Double.parseDouble(tokens[8]),Double.parseDouble(tokens[2]),Double.parseDouble(tokens[3]),Double.parseDouble(tokens[4]),Double.parseDouble(tokens[1]), sColor, Double.parseDouble(tokens[9]), Double.parseDouble(tokens[10]));
					System.out.println(s);
				} else if (tokens[0].equals("P")){
				int maxIndex = tokens.length-3;
				double wTheta = Double.parseDouble(tokens[tokens.length-2]);
				double ns = Double.parseDouble(tokens[tokens.length-1]);
				double d = Double.parseDouble(tokens[maxIndex]);
				RGBPixel color = new RGBPixel (Integer.parseInt(tokens[maxIndex-3]), Integer.parseInt(tokens[maxIndex-2]), Integer.parseInt(tokens[maxIndex-1]));
				Polygon p = new Polygon(d, color, wTheta, ns);
				for (int i = 1; i<maxIndex -3; i+=3){
					Point pp = new Point(Double.parseDouble(tokens[i]),Double.parseDouble(tokens[i+1]),Double.parseDouble(tokens[i+2]),1);
					p.addVertex(pp);
				}
				System.out.println(p);
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-400);
		}

	}
	
	private void writePPM(RGBPixel[][] imageArray) {
		try {
			ReadWritePPM.writeImage(imageArray, "image.ppm");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-500);
		}
	}
}