package in.aaronmiller.raytrace;
public class Light extends Point{
	
	double intensity;
	
	public Light(double intensity, double x, double y, double z){
		super(x,y,z,1);
		this.intensity = intensity;
	}
	
	public String toString(){
		return "Light: "+super.toString() + " Intensity: "+ intensity;
	}
	
}