public class RayTracer{
	RGBPixel ambient;
	Light[] lights;	
	int maxDepth = 5;
	public RayTracer(){
		
	}
	
	public static void main(String [] args){
		String filename;
		int argsLength = args.length;
		if (argsLength == 1){
			filename = args[0].trim();
		} else {
			//simple exit if no filename is given
			System.out.println("Please call this with the format 'java RayTracer filename'.");
			System.exit(-1);
		}
		
		RGBPixel[][] img = new RGBPixel[1024][768];
		
		for (int scanline = 0; scanline<RGBPixel.length; scanline++){
			for (int pixel = 0; pixel < RGBPixel[0].length; pixel++){
				//determine ray from COP --> pixel
				Ray ray = new Ray(RGBPixel[scanline][pixel]);
				RGBPixel[scanline][pixel] = trace(ray, 1);
			}
		}
		
		RedWritePPM.write (img, filename);
		
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
			if(/*dot product of the normal & direction to light is positive*/) {
				//add the diffuse and specular terms to the color
			}
		}// end for
		if (depth < maxDepth){
			if (/*object is reflective*/){
				rRay = //ray in reflection direction from point
				rColor = trace(rRay, ++depth);
				//scale rColor by specular coefficient and add to color
			}
			
			if (/*object is transparent*/){
				//do stuff, but this won't be the case so it will be ignored
			}
			
		} else {
			return color;			
		}
	}
}