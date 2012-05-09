package in.aaronmiller.raytrace;
import java.util.BufferedReader;
import java.util.FileReader;
import java.util.ArrayList;

public class RayTracer{
	RGBPixel ambient;
	ArrayList <Light> lights;	
	int maxDepth;
	ArrayList <Form> objects;
	Pattern light;
	Pattern polygon;
	Pattern sphere;

	
	public RayTracer(){
		this.ambient = 0;
		this.lights = new ArrayList <Light>();
		this.maxDepth = 5;
		this.objects = new ArrayList <Form>();
		this.light = new Pattern .compile("");
		this.polygon = new Pattern.compile("P\\wV\\((\\d+),(\\d+),(\\d)+\\)\\wV\\((\\d+),(\\d+),(\\d)+\\)\\wV\\((\\d+),(\\d+),(\\d)+\\)\\wColor\\((\\d+),(\\d+),(\\d)+\\)\\wD\\d+\\.\\d+");
		this.sphere = new Pattern .compile("");
	}
	
	public static void main(String [] args){
		String filename;
		int argsLength = args.length;
		if (argsLength == 1){
			filename = args[0].trim();
		} else {
			//simply exit if no filename is given
			System.out.println("Please call this with the format 'java RayTracer filename'.");
			System.exit(-1);
		}
		
		//set up the image
		RGBPixel[][] img = new RGBPixel[1024][768];
		RayTracer rt = new RayTracer();
		for (int scanline = 0; scanline<RGBPixel.length; scanline++){
			for (int pixel = 0; pixel < RGBPixel[0].length; pixel++){
				//determine ray from COP --> pixel
				Ray ray = new Ray(RGBPixel[scanline][pixel]);
				RGBPixel[scanline][pixel] = trace(ray, 1);
			}
		}
		
		//write the produced image as a PPM
		rt.writePPM(img);
		
	}
	
	private RGBPixel trace (Ray ray, int depth){
		//determine closest intersection of ray with an object
		boolean hit = false;
		
		if (hit){
			//compute normal at intersection
			Shape closestHit;
			Point point;
			Vector normal;
			
			return shade(closestHit, ray, point, normal, depth);
		}
	}
	
	private RGBPixel shade (Shape object, Ray ray, Point point, Vector normal, int depth){
		RGBPixel color;
		Ray rRay;
		Ray sRay;
		Ray tRay;
		RGBPixel rColor;
		RGBPixel tColor;
		
		color = ambient;
		for (int light = 0; i < lights.length; i++){
			sRay = new Ray (lights[light]);//pass in the light
			if(false/*dot product of the normal & direction to light is positive*/) {
				//add the diffuse and specular terms to the color
			}
		}// end for
		if (depth < maxDepth){
			if (false/*object is reflective*/){
				rRay = //ray in reflection direction from point
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
	
	private void readSceneFile(){
		BufferedReader sceneReader = new BufferedReader(new FileReader("scene.gdf"));
	}
	
	private void writePPM(RGBPixel[][] imageArray){
		ReadWritePPM.writeImage(imageArray, "image.ppm");
	}
}